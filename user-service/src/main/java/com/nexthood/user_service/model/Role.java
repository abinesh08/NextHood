package com.nexthood.user_service.model;

public enum Role {
    RESIDENT,
    VOLUNTEER,
    AUTHORITY;

    public static Role fromString(String value) {
        try {
            return Role.valueOf(value.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid role: " + value);
        }
    }
}
