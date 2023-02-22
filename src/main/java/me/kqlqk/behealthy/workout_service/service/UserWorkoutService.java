package me.kqlqk.behealthy.workout_service.service;

import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserWorkoutService {
    List<UserWorkout> getByUserId(long userId);

    void save(UserWorkout userWorkout);

    void remove(long userId, String exerciseName);
}
