package me.kqlqk.behealthy.workout_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.repository.ExerciseRepository;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Exercise getById(int id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public Exercise getByName(String name) {
        return exerciseRepository.findByName(name);
    }

    @Override
    public List<Exercise> getByMuscleGroup(MuscleGroup muscleGroup) {
        return exerciseRepository.findByMuscleGroup(muscleGroup);
    }

    public List<Exercise> getSpecificAmountOfMuscleGroup(int amount, @NonNull MuscleGroup muscleGroup) {
        List<Exercise> exercises = getByMuscleGroup(muscleGroup);
        List<Exercise> result = new ArrayList<>();

        int count = 0;
        while (count < amount) {
            int id = (int) (Math.random() * exercises.size());
            result.add(exercises.get(id));
            exercises.remove(id);
            count++;
        }

        return result;
    }
}
