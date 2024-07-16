package org.nahajski.baseauth.security;

import java.util.Locale;

public enum OIDCProvider {
    GOOGLE("sub"), GITHUB("id");

    private final String NAME_ATTRIBUTE_KEY;

    OIDCProvider(String nameAttributeKey) {
        NAME_ATTRIBUTE_KEY = nameAttributeKey;
    }

    public static OIDCProvider get(String key) {
        return OIDCProvider.valueOf(key.toUpperCase(Locale.ROOT));
    }

    public String getNameAttributeKey() {
        return NAME_ATTRIBUTE_KEY;
    }

    public String toLower() {
        return this.toString().toLowerCase(Locale.ROOT);
    }


}
