package com.demo.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

@Getter
@AllArgsConstructor
public class ErrorsBag {
    private Map<String, Error> errors;

    public static ErrorsBag parseValidationException(List<ObjectError> errors) {
        var errs = new HashMap<String, Error>();
        for (var err: errors) {
            try {
                var fieldName = ((FieldError)err).getField();
                var mssg = Error.deserialize(Objects.requireNonNull(err.getDefaultMessage()));
                errs.put(fieldName, mssg);
            } catch (Exception e) {
            }
        }
        return new ErrorsBag(errs);
    }

    public Map<String, String> getPlainMessages() {
        var mssgs = new HashMap<String, String>();
        errors.forEach((k,v) -> mssgs.put(k, v.getMessage()));
        return mssgs;
    }
}
