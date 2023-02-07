package com.cinema.cinema.utils;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@Service
public abstract class ValidatorService<T> {

    private final Validator validator;

    public void validateInput(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            Map<String, String> messages = new HashMap<>();
            for (ConstraintViolation<T> violation : violations) {
                messages.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new ArgumentNotValidException("Not valid data provided", messages);
        }
    }

    public abstract T validateExists(long id);

    public abstract void validateNotExists(T t);

}
