package org.nahajski.baseauth.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nahajski.baseauth.security.OIDCProvider;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthIssuerSubject {
    @Enumerated(EnumType.STRING)
    private OIDCProvider iss;
    private String sub;
}
