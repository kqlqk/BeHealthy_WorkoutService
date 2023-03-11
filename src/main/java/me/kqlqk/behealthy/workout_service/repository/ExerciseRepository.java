package me.kqlqk.behealthy.workout_service.repository;

import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.enums.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findById(int id);

    Optional<Exercise> findByName(String name);

    Optional<List<Exercise>> findByMuscleGroup(MuscleGroup muscleGroup);

    Optional<List<Exercise>> findByAlternativeId(int alternativeId);
}
