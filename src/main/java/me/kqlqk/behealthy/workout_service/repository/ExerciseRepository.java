package me.kqlqk.behealthy.workout_service.repository;

import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.enums.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findById(int id);

    boolean existsById(int id);

    Exercise findByName(String name);

    boolean existsByName(String name);

    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);

    boolean existsByMuscleGroup(MuscleGroup muscleGroup);

    List<Exercise> findByAlternativeId(int id);
}
