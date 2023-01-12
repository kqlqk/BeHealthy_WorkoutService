package me.kqlqk.behealthy.workout_service.exception.exceptions;

public class UserWorkoutException extends RuntimeException {
    private static final String USER_WORKOUT_EXCEPTION = "UserWorkout";

    public UserWorkoutException(String message) {
        super(USER_WORKOUT_EXCEPTION + " | " + message);
    }
}
