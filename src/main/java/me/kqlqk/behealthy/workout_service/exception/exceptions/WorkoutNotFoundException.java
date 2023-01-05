package me.kqlqk.behealthy.workout_service.exception.exceptions;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class WorkoutNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    private static final String WORKOUT_NOT_FOUND = "WorkoutNotFound";

    public WorkoutNotFoundException(String message) {
        super(WORKOUT_NOT_FOUND + " | " + message);
    }
}
