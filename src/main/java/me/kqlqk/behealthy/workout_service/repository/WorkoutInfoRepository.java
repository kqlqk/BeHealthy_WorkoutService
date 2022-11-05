package me.kqlqk.behealthy.workout_service.repository;

import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WorkoutInfoRepository extends JpaRepository<WorkoutInfo, Long> {
    WorkoutInfo findById(long id);

    List<WorkoutInfo> findByUserId(long userId);

    List<WorkoutInfo> findByUserIdAndWorkoutDay(long userId, int workoutDay);

    boolean existsByUserId(long userId);

    @Transactional
    void deleteByUserId(long userId);
}
