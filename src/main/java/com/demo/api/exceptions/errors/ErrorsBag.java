package com.demo.api.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class ErrorsBag {
    private List<Error> errors;

    public static ErrorsBag parseValidationException(MethodArgumentNotValidException exception) {
        var errs = new ArrayList<Error>();
        for (var err: exception.getBindingResult().getAllErrors()) {
            try {
                errs.add(Error.deserialize(Objects.requireNonNull(err.getDefaultMessage())));
            } catch (Exception e) {
                errs.add(new Error(ErrorCode.UNKNOWN.getCode(), ErrorMessage.UNKNOWN.getTemplate()));
            }
        }
        return new ErrorsBag(errs);
    }
}
