package com.demo.api.exceptions.errors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNKNOWN("E000"),
    REQUIRED("E001"),
    MIN_LEN("E002"),
    MAX_LEN("E003"),
    LENGTH("E004"),
    MIN("E005"),
    MAX("E006"),
    RANGE("E007"),
    EMAIL("E008"),
    DATE("E009"),
    UNIQUE("E010"),
    MUST_EXIST("E011"),
    NOT_NULL("E012"),
    PAST_OR_PRESENT("E013"),
    ;

    String code;

    ErrorCode(String code) {
        this.code = code;
    }
}
