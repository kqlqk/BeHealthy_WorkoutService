package me.kqlqk.behealthy.workout_service.service;

import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkoutInfoService {
    WorkoutInfo getById(long id);

    List<WorkoutInfo> getByUserId(long userId);

    void deleteByUserId(long userId);

    boolean existsByUserId(long userId);

    void save(WorkoutInfo workoutInfo);

    void generateAndSaveWorkout(long userId, int workoutsPerWeek);
}
