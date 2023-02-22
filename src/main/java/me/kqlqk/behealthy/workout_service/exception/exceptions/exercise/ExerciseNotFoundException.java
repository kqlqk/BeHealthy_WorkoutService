package me.kqlqk.behealthy.workout_service.exception.exceptions.exercise;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class ExerciseNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public ExerciseNotFoundException(String message) {
        super(message);
    }
}
