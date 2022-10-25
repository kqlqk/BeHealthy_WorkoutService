package me.kqlqk.behealthy.workout_service.repository;

import me.kqlqk.behealthy.workout_service.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise findById(int id);

    Exercise findByName(String name);
}
