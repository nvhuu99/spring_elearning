package com.demo.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CertificateName {
    ASSOCIATE("Associate"),
    PROFESSIONAL("Professional"),
    MASTER("Master");

    private final String value;

    CertificateName(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static CertificateName fromValue(String value) {
        for (CertificateName c : values()) {
            if (c.value.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
