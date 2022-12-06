package me.kqlqk.behealthy.workout_service.exception.exceptions;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(String message) {
        super(message);
    }
}
