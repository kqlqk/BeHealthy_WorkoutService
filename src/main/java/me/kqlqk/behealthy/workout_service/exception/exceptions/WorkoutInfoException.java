package me.kqlqk.behealthy.workout_service.exception.exceptions;

public class WorkoutInfoException extends RuntimeException {
    private static final String WORKOUT_INFO_EXCEPTION = "WorkoutInfo";

    public WorkoutInfoException(String message) {
        super(WORKOUT_INFO_EXCEPTION + " | " + message);
    }
}
