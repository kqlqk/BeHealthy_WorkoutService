package me.kqlqk.behealthy.workout_service.exception.exceptions;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class RuntimeNotWrappedByHystrixException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    public RuntimeNotWrappedByHystrixException(String message) {
        super(message);
    }
}
