package com.nexthood.common_security;

public enum Role {
    RESIDENT,
    VOLUNTEER,
    AUTHORITY;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
