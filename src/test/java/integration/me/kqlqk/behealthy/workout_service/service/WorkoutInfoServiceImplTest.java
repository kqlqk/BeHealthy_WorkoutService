package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.dto.condition_client.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.exercise.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.workout_info.WorkoutInfoAlreadyExistsException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.workout_info.WorkoutInfoNotFoundException;
import me.kqlqk.behealthy.workout_service.feign_client.UserConditionClient;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import me.kqlqk.behealthy.workout_service.model.enums.Gender;
import me.kqlqk.behealthy.workout_service.repository.WorkoutInfoRepository;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import me.kqlqk.behealthy.workout_service.service.impl.WorkoutInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ServiceTest
public class WorkoutInfoServiceImplTest {
    @Autowired
    private WorkoutInfoServiceImpl workoutInfoService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private WorkoutInfoRepository workoutInfoRepository;

    @MockBean
    private UserConditionClient userConditionClient;

    @Test
    public void save_shouldSaveEntityToDb() {
        int oldSize = workoutInfoRepository.findAll().size();

        WorkoutInfo workoutInfo = new WorkoutInfo();
        workoutInfo.setUserId(1);
        workoutInfo.setDay(1);
        workoutInfo.setNumberPerDay(1);
        workoutInfo.setExercise(exerciseService.getById(1));
        workoutInfo.setRep(10);
        workoutInfo.setSet(4);

        workoutInfoService.save(workoutInfo);

        int newSize = workoutInfoRepository.findAll().size();

        assertThat(newSize).isEqualTo(oldSize + 1);
    }

    @Test
    public void save_shouldThrowException() {
        WorkoutInfo workoutInfo = new WorkoutInfo();
        workoutInfo.setId(1);

        assertThrows(WorkoutInfoAlreadyExistsException.class, () -> workoutInfoService.save(workoutInfo));
    }

    @Test
    public void update_shouldUpdateEntityInDb() {
        WorkoutInfo workoutInfo = workoutInfoRepository.findById(1L).get();
        workoutInfo.setDay(2);
        workoutInfo.setRep(5);

        workoutInfoService.update(workoutInfo);

        assertThat(workoutInfoRepository.findById(1L).get().getDay()).isEqualTo(workoutInfo.getDay());
        assertThat(workoutInfoRepository.findById(1L).get().getRep()).isEqualTo(workoutInfo.getRep());
    }

    @Test
    public void update_shouldThrowException() {
        WorkoutInfo workoutInfo = new WorkoutInfo();
        workoutInfo.setId(0);

        assertThrows(WorkoutInfoNotFoundException.class, () -> workoutInfoService.update(workoutInfo));
    }

    @Test
    public void updateWorkoutWithAlternativeExercise_shouldUpdateWorkoutWithAlternativeExercise() {
        Exercise toChange = exerciseService.getByName("seated dumbbell press");

        workoutInfoService.updateWorkoutWithAlternativeExercise(1, toChange);

        workoutInfoService.getByUserId(1).forEach(workoutInfo ->
                                                          assertThat(workoutInfo.getExercise().getId()).isNotEqualTo(toChange.getId()));
    }

    @Test
    public void updateWorkoutWithAlternativeExercise_shouldThrowException() {
        assertThrows(WorkoutInfoNotFoundException.class,
                     () -> workoutInfoService.updateWorkoutWithAlternativeExercise(0, exerciseService.getByName("seated dumbbell press")));

        assertThrows(ExerciseNotFoundException.class,
                     () -> workoutInfoService.updateWorkoutWithAlternativeExercise(1, exerciseService.getByName("smith machine seated press")));
    }

    @Test
    public void generateAndSaveWorkout_shouldGenerateAnsSaveWorkout() {
        UserConditionDTO userConditionDTO = new UserConditionDTO(2, Gender.MALE);
        when(userConditionClient.getUserConditionByUserId(2)).thenReturn(userConditionDTO);

        workoutInfoService.generateAndSaveCompleteWorkout(2, 1);
        List<WorkoutInfo> workoutInfos = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos).hasSize(6);
        for (WorkoutInfo workoutInfo : workoutInfos) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 1).isTrue();
        }

        workoutInfoService.generateAndSaveCompleteWorkout(2, 2);
        List<WorkoutInfo> workoutInfos2 = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos2).hasSize(9);
        for (WorkoutInfo workoutInfo : workoutInfos2) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 2).isTrue();
        }

        workoutInfoService.generateAndSaveCompleteWorkout(2, 3);
        List<WorkoutInfo> workoutInfos3 = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos3).hasSize(14);
        for (WorkoutInfo workoutInfo : workoutInfos3) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 3).isTrue();
        }

        workoutInfoService.generateAndSaveCompleteWorkout(2, 4);
        List<WorkoutInfo> workoutInfos4 = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos4).hasSize(21);
        for (WorkoutInfo workoutInfo : workoutInfos4) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 4).isTrue();
        }

        workoutInfoService.generateAndSaveCompleteWorkout(2, 5);
        List<WorkoutInfo> workoutInfos5 = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos5).hasSize(21);
        for (WorkoutInfo workoutInfo : workoutInfos5) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 5).isTrue();
        }
    }
}
