package com.project.booking.Console;

public interface Command {
    String text();

    String description();

    void doCommand();

    default boolean isAllowToUnAuth() {
        return false;
    }

    default boolean isExit() {
        return false;
    }
}
