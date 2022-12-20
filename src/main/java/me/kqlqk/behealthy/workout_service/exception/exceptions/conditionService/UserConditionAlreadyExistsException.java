package me.kqlqk.behealthy.workout_service.exception.exceptions.conditionService;

public class UserConditionAlreadyExistsException extends RuntimeException {
    private static final String USER_CONDITION_ALREADY_EXISTS = "UserConditionAlreadyExists";

    public UserConditionAlreadyExistsException(String message) {
        super(USER_CONDITION_ALREADY_EXISTS + " | " + message);
    }
}
