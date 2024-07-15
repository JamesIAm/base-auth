package com.jamesiam.baseauth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

@Component
@Slf4j
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.client.frontend.url")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String name = oAuth2AuthenticationToken.getPrincipal().getName();
        Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
        String email = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String oAuthProvider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        if (StringUtils.isEmpty(email)) {
            throw new UserPrincipalNotFoundException(null);
        }
        if ("google".equals(oAuthProvider)) {
            log.info("Google auth");
            DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("user")), attributes, "sub");//Find the relevant name attribute key authentication, principal, nameAttributeKey
            Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority("user")), oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
            SecurityContextHolder.getContext().setAuthentication(securityAuth);
        } else if ("github".equals(oAuthProvider)) {
            log.info("Github auth");
        }
        //Create user and add to user security context
        log.info("Name: " + name);
        log.info("Email: " + email);

        this.

                setAlwaysUseDefaultTargetUrl(true);
        this.

                setDefaultTargetUrl(frontendUrl);
        super.

                onAuthenticationSuccess(request, response, authentication);

    }
}
