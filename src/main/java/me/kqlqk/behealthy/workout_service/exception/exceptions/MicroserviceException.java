package me.kqlqk.behealthy.workout_service.exception.exceptions;

public class MicroserviceException extends RuntimeException {
    private final static String MICROSERVICE = "MICROSERVICE";

    public MicroserviceException(String message) {
        super(MICROSERVICE + " | " + message);
    }
}
