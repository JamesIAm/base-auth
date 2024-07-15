package org.nahajski.baseauth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.nahajski.baseauth.entity.UserEntity;
import org.nahajski.baseauth.entity.UserRole;
import org.nahajski.baseauth.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

    private final String frontendUrl;
    private final UserService userService;

    public OAuth2LoginSuccessHandler(UserService userService, @Value("${spring.security.oauth2.client.frontend.url}") String frontendUrl) {
        this.userService = userService;
        this.frontendUrl = frontendUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String name = oAuth2AuthenticationToken.getPrincipal().getName();
        Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
        String email = attributes.get("email").toString();
        String oAuthProvider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        if (StringUtils.isEmpty(email)) {
            throw new UserPrincipalNotFoundException(null);
        }
        Optional<UserEntity> optionalUserEntity = userService.findByEmail(email);
        if (optionalUserEntity.isPresent()) {
            var userEntity = optionalUserEntity.get();
            extracted(oAuthProvider, userEntity.getRole(), attributes, oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), GOOGLE_NAME_ATTRIBUTE_KEY);
            //Create user and add to user security context
            log.info("Name: " + name);
            log.info("Email: " + email);
        } else {
            UserEntity user = new UserEntity();
            user.setRole(UserRole.USER);
            user.setEmail(email);
            user.setName(name);
            userService.saveUser(user);
            extracted(oAuthProvider, user.getRole(), attributes, oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), GOOGLE_NAME_ATTRIBUTE_KEY);

        }

        this.

                setAlwaysUseDefaultTargetUrl(true);
        this.

                setDefaultTargetUrl(frontendUrl);
        super.

                onAuthenticationSuccess(request, response, authentication);

    }

    private static void extracted(String oAuthProvider, UserRole userRole
            , Map<String, Object> attributes, String provider, String nameAttributeKey) {
        if ("google".equals(oAuthProvider)) {
            log.info("Google auth");
            DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userRole.name())), attributes, nameAttributeKey);//Find the relevant name attribute key authentication, principal, nameAttributeKey
            Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority("user")), provider);
            SecurityContextHolder.getContext().setAuthentication(securityAuth);
        } else if ("github".equals(oAuthProvider)) {
            log.info("Github auth");
        }
    }
}
