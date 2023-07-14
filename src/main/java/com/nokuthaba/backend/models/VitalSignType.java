package com.nokuthaba.backend.models;

public enum VitalSignType {
    BLOOD_PRESSURE("Blood Pressure", "mmHg"),
    WEIGHT("Weight", "kg"),
    Height("Height", "cm"),
    BLOOD_GLUCOSE("Blood Glucose", "mg/dL"),
    TEMPERATURE("Temperature", "celcius degrees"),

    PULSE("Pulse", "bpm");

    private  final  String type;
    private  final  String unit;

    VitalSignType(String type, String unit) {
        this.type = type;
        this.unit = unit;
    }

}
