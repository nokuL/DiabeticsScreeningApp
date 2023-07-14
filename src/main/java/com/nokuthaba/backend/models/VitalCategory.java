package com.nokuthaba.backend.models;

public enum VitalCategory {
    NORMAL("Normal"),
    ELEVATED("Elevated"),
    HIGH_BP_STAGE_1("High Stage 1"),
    HIGH_BP_STAGE_2("High Stage 2"),
    HYPERTENSIVE_CRISI("Hypertensive"),
    UNDER_WEIGHT("Under Weight"),
    OVERWEIGHT("Over Weight"),
    OBESE("Obese"),
    LOW("Low"),
    HIGH("HIGH"),
    VERY_HIGH("Very High");
    private  final  String category;

    VitalCategory(String category) {
        this.category = category;
    }
}
