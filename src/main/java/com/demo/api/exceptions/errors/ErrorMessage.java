package com.demo.api.exceptions.errors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorMessage {
    UNKNOWN("Unknown error"),
    REQUIRED("%name is a required field"),
    MIN_LEN("%name must contain more than %min characters"),
    MAX_LEN("%name must contain less than %max characters"),
    LENGTH("%name length must be between %min-%max characters"),
    MIN("value of %name must be more than %min"),
    MAX("value of %name must be less than %max"),
    RANGE("value of %name must be between %min and %max"),
    EMAIL("%name must be a valid email address"),
    DATE("%name must be a valid date"),
    UNIQUE("%name has already existed"),
    MUST_EXIST("%name does not exist"),
    NOT_NULL("%name can not be null"),
    PAST_OR_PRESENT("%name does not accept a future date"),
    ;

    String template;

    ErrorMessage(String template) {
        this.template = template;
    }
}
