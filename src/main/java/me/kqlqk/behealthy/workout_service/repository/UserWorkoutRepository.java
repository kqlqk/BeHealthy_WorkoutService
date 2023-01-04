package me.kqlqk.behealthy.workout_service.repository;

import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkoutRepository extends JpaRepository<UserWorkout, Long> {
    List<UserWorkout> getByUserId(long userId);

    boolean existsByUserId(long userId);

    boolean existsByExerciseName(String exerciseName);
}
