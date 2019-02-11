package com.project.booking.Constants;

public enum Sex {
    MALE("Male", "MR"),
    FEMALE("Female", "MRS"),
    UNKNOWN("Unknown", "NONE");

    private final String name;
    private final String prefix;

    Sex(String name) {
        this.name = name;
        this.prefix = getPrefix();
    }

    Sex(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }
}
