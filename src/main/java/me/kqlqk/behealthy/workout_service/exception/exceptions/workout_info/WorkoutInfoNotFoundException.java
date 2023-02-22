package me.kqlqk.behealthy.workout_service.exception.exceptions.workout_info;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class WorkoutInfoNotFoundException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public WorkoutInfoNotFoundException(String message) {
        super(message);
    }
}
