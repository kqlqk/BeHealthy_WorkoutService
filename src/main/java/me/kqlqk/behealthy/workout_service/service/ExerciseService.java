package me.kqlqk.behealthy.workout_service.service;

import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExerciseService {
    Exercise getById(int id);

    Exercise getByName(String name);

    List<Exercise> getByMuscleGroup(MuscleGroup muscleGroup);

    List<Exercise> getSpecificAmountOfMuscleGroup(int amount, MuscleGroup muscleGroup);

    List<Exercise> getAlternative(Exercise exercise);
}
