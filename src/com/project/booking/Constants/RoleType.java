package com.project.booking.Constants;

public enum RoleType {
    USER("User"),
    GUEST("Guest");

    private final String name;

    public String getName() {
        return name;
    }

    RoleType(String name) {
        this.name = name;
    }
}
