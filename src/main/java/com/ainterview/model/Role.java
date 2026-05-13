package com.ainterview.model;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    USER("user"),
    ADMIN("admin");

    public final String title;
    @Override
    public String toString() {
        return title;
    }
}
