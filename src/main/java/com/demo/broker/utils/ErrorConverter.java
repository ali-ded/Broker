package com.demo.broker.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ErrorConverter {
    public Map<String, String> convertFieldErrorListToMap(List<FieldError> errors) {
        return errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        objectError -> objectError.getDefaultMessage() != null ?
                                objectError.getDefaultMessage() :
                                "the field does not satisfy the validation conditions",
                        (s, a) -> s + ", " + a)
                );
    }
}
