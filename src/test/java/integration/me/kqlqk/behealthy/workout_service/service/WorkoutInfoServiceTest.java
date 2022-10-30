package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import me.kqlqk.behealthy.workout_service.repository.WorkoutInfoRepository;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import me.kqlqk.behealthy.workout_service.service.impl.WorkoutInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class WorkoutInfoServiceTest {
    @Autowired
    private WorkoutInfoServiceImpl workoutInfoService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private WorkoutInfoRepository workoutInfoRepository;

    @Test
    public void save_shouldSaveEntityToDb() {
        int size = workoutInfoService.getByUserId(1).size();

        WorkoutInfo workoutInfo = new WorkoutInfo();
        workoutInfo.setUserId(1);
        workoutInfo.setWorkoutDay(1);
        workoutInfo.setExercise(exerciseService.getById(1));
        workoutInfo.setNumberPerDay(1);

        workoutInfoService.save(workoutInfo);

        int updatedSize = workoutInfoService.getByUserId(1).size();


        assertThat(updatedSize).isGreaterThan(size);
    }

    @Test
    public void generateAndSaveUpperLowerBodySplit_shouldGenerateAndSaveUpperLowerBodySplit() {
        workoutInfoRepository.deleteAll();
        int size = workoutInfoService.getByUserId(1).size();

        workoutInfoService.generateAndSaveUpperLowerBodySplit(1);

        int newSize = workoutInfoService.getByUserId(1).size();

        assertThat(newSize).isGreaterThan(size);
    }

    @Test
    public void generateAndSavePushPullLegsSplit_shouldGenerateAndSavePushPullLegsSplit() {
        workoutInfoRepository.deleteAll();
        int size = workoutInfoService.getByUserId(1).size();

        workoutInfoService.generateAndSavePushPullLegsSplit(1);

        int newSize = workoutInfoService.getByUserId(1).size();

        assertThat(newSize).isGreaterThan(size);
    }

    @Test
    public void generateAndSave4DaysSplit_shouldGenerateAndSave4DaysSplit() {
        workoutInfoRepository.deleteAll();
        int size = workoutInfoService.getByUserId(1).size();

        workoutInfoService.generateAndSave4DaysSplit(1);

        int newSize = workoutInfoService.getByUserId(1).size();

        assertThat(newSize).isGreaterThan(size);
    }
}
