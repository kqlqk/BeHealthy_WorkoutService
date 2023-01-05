package me.kqlqk.behealthy.workout_service.exception.exceptions;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class ExerciseNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    private static final String EXERCISE_NOT_FOUND = "ExerciseNotFound";

    public ExerciseNotFoundException(String message) {
        super(EXERCISE_NOT_FOUND + " | " + message);
    }
}
