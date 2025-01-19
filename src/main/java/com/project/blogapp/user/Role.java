package com.project.blogapp.user;

public enum Role {
    ADMIN(2),
    USER(2);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
