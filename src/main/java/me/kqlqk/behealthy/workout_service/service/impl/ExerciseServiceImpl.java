package me.kqlqk.behealthy.workout_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.workout_service.exception.exceptions.exercise.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.enums.MuscleGroup;
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
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise with id = " + id + " not found"));
    }

    @Override
    public Exercise getByName(@NonNull String name) {
        return exerciseRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise with name = " + name + " not found"));
    }

    @Override
    public List<Exercise> getByMuscleGroup(@NonNull MuscleGroup muscleGroup) {
        List<Exercise> res = exerciseRepository.findByMuscleGroup(muscleGroup)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise with muscleGroup = " + muscleGroup.name() + " not found"));

        if (res.isEmpty()) {
            throw new ExerciseNotFoundException("Exercise with muscleGroup = " + muscleGroup.name() + " not found");
        }

        return res;
    }

    public List<Exercise> getSpecificAmountOfMuscleGroup(int amount, @NonNull MuscleGroup muscleGroup) {
        List<Exercise> exercises = getByMuscleGroup(muscleGroup);
        List<Exercise> result = new ArrayList<>();

        if (exercises.size() < amount) {
            throw new ExerciseNotFoundException("There's only " + exercises.size() + " exercise for muscle group = " + muscleGroup.name());
        }

        int count = 0;
        while (count < amount) {
            int id = (int) (Math.random() * exercises.size());
            result.add(exercises.get(id));
            exercises.remove(id);
            count++;
        }

        return result;
    }

    @Override
    public List<Exercise> getAlternative(@NonNull Exercise exercise) {
        if (exercise.getAlternativeId() == null) {
            throw new ExerciseNotFoundException("There are no alternative exercises for " + exercise.getName());
        }

        List<Exercise> alternatives = exerciseRepository.findByAlternativeId(exercise.getAlternativeId())
                .orElseThrow(() -> new ExerciseNotFoundException("There are no alternative exercises for " + exercise.getName()));

        alternatives.removeIf(alt -> alt.getId() == exercise.getId());

        return alternatives;
    }
}
