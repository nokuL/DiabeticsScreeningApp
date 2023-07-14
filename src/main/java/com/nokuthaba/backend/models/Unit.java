package com.nokuthaba.backend.models;

public enum Unit {
    mmHg("mmHg"),
    kg("kg"),
    cm("cm"),
    mgPerDL("mg/DL"),
    celcius_degrees("celcius_degrees"),
    bpm("bpm");
    private final String value;

    Unit(String value) {
        this.value = value;
    }




}
