package me.kqlqk.behealthy.workout_service.exception.exceptions;

public class ExerciseNotFoundException extends RuntimeException {
    private static final String EXERCISE_NOT_FOUND = "ExerciseNotFound";

    public ExerciseNotFoundException(String message) {
        super(EXERCISE_NOT_FOUND + " | " + message);
    }
}
