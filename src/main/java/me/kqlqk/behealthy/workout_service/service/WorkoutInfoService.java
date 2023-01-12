package me.kqlqk.behealthy.workout_service.service;

import me.kqlqk.behealthy.workout_service.dto.ExerciseDTO;
import me.kqlqk.behealthy.workout_service.dto.WorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkoutInfoService {
    List<WorkoutInfo> getByUserId(long userId);

    boolean existsByUserId(long userId);

    void save(WorkoutInfoDTO workoutInfoDTO);

    void updateWorkoutWithAlternativeExercise(long userId, ExerciseDTO toChange);

    void generateAndSaveWorkout(long userId, int workoutsPerWeek);
}
