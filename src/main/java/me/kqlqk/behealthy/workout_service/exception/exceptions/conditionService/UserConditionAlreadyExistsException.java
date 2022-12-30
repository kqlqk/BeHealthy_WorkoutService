package me.kqlqk.behealthy.workout_service.exception.exceptions.conditionService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class UserConditionAlreadyExistsException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    private static final String USER_CONDITION_ALREADY_EXISTS = "UserConditionAlreadyExists";

    public UserConditionAlreadyExistsException(String message) {
        super(USER_CONDITION_ALREADY_EXISTS + " | " + message);
    }
}
