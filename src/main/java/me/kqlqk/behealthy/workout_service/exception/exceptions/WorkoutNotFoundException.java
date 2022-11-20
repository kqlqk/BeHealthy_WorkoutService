package me.kqlqk.behealthy.workout_service.exception.exceptions;

public class WorkoutNotFoundException extends RuntimeException {
    private static final String WORKOUT_NOT_FOUND = "WorkoutNotFound";

    public WorkoutNotFoundException(String message) {
        super(WORKOUT_NOT_FOUND + " | " + message);
    }
}
