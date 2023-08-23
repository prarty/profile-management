package com.intuit.sride.profilemanagement.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VehicleType {
    TWO_WHEELER("TWO_WHEELER"), THREE_WHEELER("THREE_WHEELER"), FOUR_WHEELER("FOUR_WHEELER");

    @JsonValue
    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
