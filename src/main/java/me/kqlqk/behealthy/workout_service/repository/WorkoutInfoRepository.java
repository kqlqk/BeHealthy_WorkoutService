package me.kqlqk.behealthy.workout_service.repository;

import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutInfoRepository extends JpaRepository<WorkoutInfo, Long> {
    Optional<List<WorkoutInfo>> findByUserId(long userId);

    boolean existsByUserId(long userId);

    void deleteByUserId(long userId);
}
