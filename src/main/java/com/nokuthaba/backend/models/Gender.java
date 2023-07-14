package com.nokuthaba.backend.models;

public enum Gender {
    FEMALE("Female"),
    MALE("Male");

    Gender(String value) {
        this.value = value;
    }

    private final String value;


}
