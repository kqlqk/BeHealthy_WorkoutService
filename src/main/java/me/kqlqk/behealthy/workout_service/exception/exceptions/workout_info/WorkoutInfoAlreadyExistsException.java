package me.kqlqk.behealthy.workout_service.exception.exceptions.workout_info;

public class WorkoutInfoAlreadyExistsException extends RuntimeException {
    public WorkoutInfoAlreadyExistsException(String message) {
        super(message);
    }
}
