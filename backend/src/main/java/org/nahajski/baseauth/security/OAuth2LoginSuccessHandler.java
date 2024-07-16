package org.nahajski.baseauth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.nahajski.baseauth.entity.OauthIssuerSubject;
import org.nahajski.baseauth.entity.UserEntity;
import org.nahajski.baseauth.entity.UserRole;
import org.nahajski.baseauth.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String GOOGLE_NAME_ATTRIBUTE_KEY = "sub";
    public static final UserRole NEW_USER_ROLE = UserRole.USER;

    private final String frontendUrl;
    private final UserService userService;

    public OAuth2LoginSuccessHandler(UserService userService, @Value("${spring.security.oauth2.client.frontend.url}") String frontendUrl) {
        this.userService = userService;
        this.frontendUrl = frontendUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String oAuthProvider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        DefaultOAuth2User principal = (DefaultOAuth2User) oAuth2AuthenticationToken.getPrincipal();
        String subject = principal.getName();
        OauthIssuerSubject oauthIssuerSubject = new OauthIssuerSubject(oAuthProvider, subject);
        Optional<UserEntity> optionalUserEntity = userService.find(oauthIssuerSubject);
        var attributes = principal.getAttributes();
        String email = "";
        String name = "";
        if (attributes != null ) {
            Object nullableEmail = attributes.getOrDefault("email", "");
            if (nullableEmail != null) {
                email = nullableEmail.toString();
            }

            Object nullableName = attributes.getOrDefault("name", "");
            if (nullableName != null) {
                name = nullableName.toString();
            }
        }
        if (optionalUserEntity.isPresent()) {
            var userEntity = optionalUserEntity.get();
            extracted(oAuthProvider, userEntity.getRole(), attributes, GOOGLE_NAME_ATTRIBUTE_KEY);
            log.info("Name: " + name);
            log.info("Email: " + email);
        } else {
            UserEntity user = new UserEntity();
            user.setRole(NEW_USER_ROLE);
            user.setEmail(email);
            user.setName(name);
            user.setOauthId(oauthIssuerSubject);
            userService.saveUser(user);
            extracted(oAuthProvider, user.getRole(), attributes, GOOGLE_NAME_ATTRIBUTE_KEY);

        }

        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(frontendUrl);

        response.addCookie(new LoggedInCookie(true));
        super.onAuthenticationSuccess(request, response, authentication);

    }

    private static void extracted(String oAuthProvider, UserRole userRole, Map<String, Object> attributes, String nameAttributeKey) {
        if ("google".equals(oAuthProvider)) {
            log.info("Google auth");
            String authority = userRole.name();
            DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(authority)), attributes, nameAttributeKey);//Find the relevant name attribute key authentication, principal, nameAttributeKey
            Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(authority)), oAuthProvider);
            SecurityContextHolder.getContext().setAuthentication(securityAuth);
        } else if ("github".equals(oAuthProvider)) {
            log.info("Github auth");
        }
    }
}
