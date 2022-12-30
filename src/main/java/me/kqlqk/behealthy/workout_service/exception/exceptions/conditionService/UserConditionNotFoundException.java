package me.kqlqk.behealthy.workout_service.exception.exceptions.conditionService;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class UserConditionNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    private static final String USER_CONDITION_NOT_FOUND = "UserConditionNotFound";

    public UserConditionNotFoundException(String message) {
        super(USER_CONDITION_NOT_FOUND + " | " + message);
    }
}
