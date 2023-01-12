package me.kqlqk.behealthy.workout_service.exception.exceptions.conditionService;

public class ExerciseException extends RuntimeException {
    private static final String EXERCISE_EXCEPTION = "Exercise";

    public ExerciseException(String message) {
        super(EXERCISE_EXCEPTION + " | " + message);
    }
}
