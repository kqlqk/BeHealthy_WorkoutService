package me.kqlqk.behealthy.workout_service.service;

import me.kqlqk.behealthy.workout_service.dto.UserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserWorkoutService {
    List<UserWorkout> getByUserId(long userId);

    void save(UserWorkoutDTO userWorkoutDTO);

    void remove(long userId, long exerciseId);
}
