package integration.me.kqlqk.behealthy.workout_service.repository;

import annotations.RepositoryTest;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import me.kqlqk.behealthy.workout_service.repository.WorkoutInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class WorkoutInfoRepositoryTest {
    @Autowired
    private WorkoutInfoRepository workoutInfoRepository;

    @Test
    public void findById_shouldFindByIdOrReturnNull() {
        WorkoutInfo workoutInfo = workoutInfoRepository.findById(1);

        assertThat(workoutInfo).isNotNull();

        WorkoutInfo nullWorkoutInfo = workoutInfoRepository.findById(99);
        assertThat(nullWorkoutInfo).isNull();
    }


    @Test
    public void findByUserId_shouldFindByUserIdOrReturnEmptyList() {
        List<WorkoutInfo> workoutInfos = workoutInfoRepository.findByUserId(1);

        assertThat(workoutInfos).isNotEmpty();

        List<WorkoutInfo> emptyWorkoutInfos = workoutInfoRepository.findByUserId(99);
        assertThat(emptyWorkoutInfos).isEmpty();
    }

    @Test
    public void existsByUserId_shouldCheckIfExistsByUserId() {
        boolean workoutInfo = workoutInfoRepository.existsByUserId(1);

        assertThat(workoutInfo).isTrue();

        boolean emptyWorkoutInfo = workoutInfoRepository.existsByUserId(99);
        assertThat(emptyWorkoutInfo).isFalse();
    }

    @Test
    public void findByUserIdAndWorkoutDay_shouldFindByUserIdAndWorkoutDayOrReturnEmptyList() {
        List<WorkoutInfo> workoutInfos = workoutInfoRepository.findByUserIdAndWorkoutDay(1, 1);

        assertThat(workoutInfos).isNotEmpty();

        List<WorkoutInfo> emptyWorkoutInfos = workoutInfoRepository.findByUserIdAndWorkoutDay(99, 1);
        assertThat(emptyWorkoutInfos).isEmpty();
    }
}
