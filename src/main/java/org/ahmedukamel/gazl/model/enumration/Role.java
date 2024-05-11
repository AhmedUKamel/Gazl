package org.ahmedukamel.gazl.model.enumration;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    VISITOR,
    BUSINESS,
    CHARITY,
    GOVERNMENT,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}