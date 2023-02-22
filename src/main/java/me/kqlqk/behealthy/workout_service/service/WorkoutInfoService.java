package me.kqlqk.behealthy.workout_service.service;

import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkoutInfoService {
    List<WorkoutInfo> getByUserId(long userId);

    void save(WorkoutInfo workoutInfo);

    void update(WorkoutInfo workoutInfo);

    void updateWorkoutWithAlternativeExercise(long userId, Exercise toChange);

    void generateAndSaveCompleteWorkout(long userId, int workoutsPerWeek);
}
