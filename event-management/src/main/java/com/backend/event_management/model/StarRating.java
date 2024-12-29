package com.backend.event_management.model;

public enum StarRating {
    HALF_STAR(0.5f),
    ONE_STAR(1.0f),
    ONE_AND_HALF_STAR(1.5f),
    TWO_STAR(2.0f),
    TWO_AND_HALF_STAR(2.5f),
    THREE_STAR(3.0f),
    THREE_AND_HALF_STAR(3.5f),
    FOUR_STAR(4.0f),
    FOUR_AND_HALF_STAR(4.5f),
    FIVE_STAR(5.0f);

    private final float value;

    StarRating(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}