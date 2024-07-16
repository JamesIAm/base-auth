package org.nahajski.baseauth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.nahajski.baseauth.entity.BaseOAuth2User;
import org.nahajski.baseauth.entity.OAuthIssuerSubject;
import org.nahajski.baseauth.entity.UserEntity;
import org.nahajski.baseauth.entity.UserRole;
import org.nahajski.baseauth.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final UserRole NEW_USER_ROLE = UserRole.USER;

    private final String frontendUrl;
    private final UserService userService;

    public OAuth2LoginSuccessHandler(UserService userService, @Value("${baseauth.frontend.url}") String frontendUrl) {
        this.userService = userService;
        this.frontendUrl = frontendUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OIDCProvider oAuthOIDCProvider = OIDCProvider.get(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
        DefaultOAuth2User principal = (DefaultOAuth2User) oAuth2AuthenticationToken.getPrincipal();
        String subject = principal.getName();
        OAuthIssuerSubject oAuthIssuerSubject = new OAuthIssuerSubject(oAuthOIDCProvider, subject);
        Optional<UserEntity> optionalUserEntity = userService.find(oAuthIssuerSubject);
        var attributes = principal.getAttributes();
        String email = getNullableAttribute(attributes, "email");
        String name = getNullableAttribute(attributes, "name");
        if (optionalUserEntity.isPresent()) {
            var user = optionalUserEntity.get();
            setSecurityContextAuth(user, attributes);
        } else {
            UserEntity user = createAndSaveUser(email, name, oAuthIssuerSubject);
            setSecurityContextAuth(user, attributes);

        }

        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);

        response.addCookie(new LoggedInCookie(true));
        super.onAuthenticationSuccess(request, response, authentication);

    }

    private UserEntity createAndSaveUser(String email, String name, OAuthIssuerSubject oauthIssuerSubject) {
        UserEntity user = new UserEntity();
        user.setRole(NEW_USER_ROLE);
        user.setEmail(email);
        user.setName(name);
        user.setOauthId(oauthIssuerSubject);
        userService.saveUser(user);
        return user;
    }

    private static String getNullableAttribute(Map<String, Object> attributes, String attribute) {
        String email = "";
        if (attributes != null) {
            Object nullableAttribute = attributes.getOrDefault(attribute, "");
            if (nullableAttribute != null) {
                email = nullableAttribute.toString();
            }
        }
        return email;
    }

    private static void setSecurityContextAuth(UserEntity user, Map<String, Object> attributes) {
        OIDCProvider provider = user.getOauthId().getIss();
        log.info("logged in with %s OIDC auth".formatted(provider));
        List<UserRole> authorities = List.of(user.getRole());
        DefaultOAuth2User newUser = new BaseOAuth2User(authorities, attributes, provider.getNameAttributeKey(), user);//Find the relevant name attribute key authentication, principal, nameAttributeKey
        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, authorities, provider.toString());
        SecurityContextHolder.getContext().setAuthentication(securityAuth);
    }
}
