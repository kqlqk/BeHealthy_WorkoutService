package me.kqlqk.behealthy.workout_service.repository;

import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findById(int id);

    Exercise findByName(String name);

    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);
}
