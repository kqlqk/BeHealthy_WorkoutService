package me.kqlqk.behealthy.workout_service.exception.exceptions;

import com.netflix.hystrix.exception.ExceptionNotWrappedByHystrix;

public class MicroserviceException extends RuntimeException implements ExceptionNotWrappedByHystrix {
    private final static String MICROSERVICE = "MICROSERVICE";

    public MicroserviceException(String message) {
        super(MICROSERVICE + " | " + message);
    }
}
