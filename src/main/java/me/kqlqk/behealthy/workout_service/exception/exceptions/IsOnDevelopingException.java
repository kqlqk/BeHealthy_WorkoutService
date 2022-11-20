package me.kqlqk.behealthy.workout_service.exception.exceptions;

public class IsOnDevelopingException extends RuntimeException {
    private static final String IS_ON_DEVELOPING = "IsOnDeveloping";

    public IsOnDevelopingException(String message) {
        super(IS_ON_DEVELOPING + " | " + message);
    }
}
