package me.kqlqk.behealthy.workout_service.repository;

import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserWorkoutRepository extends JpaRepository<UserWorkout, Long> {
    Optional<List<UserWorkout>> findByUserId(long userId);

    boolean existsByExerciseNameIgnoreCase(String exerciseName);

    void deleteByExerciseNameIgnoreCase(String exerciseName);
}
