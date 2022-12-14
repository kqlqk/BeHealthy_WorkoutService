package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.dto.ExerciseDTO;
import me.kqlqk.behealthy.workout_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.dto.WorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.enums.Gender;
import me.kqlqk.behealthy.workout_service.exception.exceptions.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.conditionService.ExerciseException;
import me.kqlqk.behealthy.workout_service.feign_client.ConditionClient;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
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
    private ConditionClient conditionClient;

    @Test
    public void save_shouldSaveEntityToDb() {
        int oldSize = workoutInfoService.getByUserId(1).size();

        WorkoutInfoDTO workoutInfoDTO = new WorkoutInfoDTO();
        workoutInfoDTO.setUserId(1);
        workoutInfoDTO.setDay(1);
        workoutInfoDTO.setExercise(exerciseService.getById(1));
        workoutInfoDTO.setNumberPerDay(1);

        workoutInfoService.save(workoutInfoDTO);

        int newSize = workoutInfoService.getByUserId(1).size();

        assertThat(newSize).isEqualTo(oldSize + 1);
    }

    @Test
    public void updateWorkoutWithAlternativeExercise_shouldUpdateUserWorkoutWithAlternativeExercise() {
        ExerciseDTO toChange = ExerciseDTO.convertExerciseToExerciseDTO(exerciseService.getByName("seated dumbbell press"));

        workoutInfoService.getByUserId(1).forEach(System.out::println);

        workoutInfoService.updateWorkoutWithAlternativeExercise(1, toChange);

        workoutInfoService.getByUserId(1).forEach(System.out::println);

        workoutInfoService.getByUserId(1).forEach(workoutInfo ->
                assertThat(workoutInfo.getExercise().getId()).isNotEqualTo(toChange.getId()));
    }

    @Test
    public void updateWorkoutWithAlternativeExercise_shouldThrowException() {
        ExerciseDTO toChange = ExerciseDTO.convertExerciseToExerciseDTO(exerciseService.getByName("seated dumbbell press"));
        String badName = "-";
        toChange.setName(badName);

        assertThrows(ExerciseException.class, () -> workoutInfoService.updateWorkoutWithAlternativeExercise(1, toChange));


        ExerciseDTO toChange2 = ExerciseDTO.convertExerciseToExerciseDTO(exerciseService.getByName("smith machine seated press"));

        assertThrows(ExerciseNotFoundException.class, () -> workoutInfoService.updateWorkoutWithAlternativeExercise(1, toChange2));
    }

    @Test
    public void generateAndSaveWorkout_shouldGenerateAnsSaveWorkout() {
        UserConditionDTO userConditionDTO = new UserConditionDTO(1, 2, Gender.MALE);
        when(conditionClient.getUserConditionByUserId(2)).thenReturn(userConditionDTO);

        workoutInfoService.generateAndSaveWorkout(2, 1);
        List<WorkoutInfo> workoutInfos = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos).hasSize(6);
        for (WorkoutInfo workoutInfo : workoutInfos) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 1).isTrue();
        }
        workoutInfoRepository.deleteAll();


        workoutInfoService.generateAndSaveWorkout(2, 2);
        List<WorkoutInfo> workoutInfos2 = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos2).hasSize(9);
        for (WorkoutInfo workoutInfo : workoutInfos2) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 2).isTrue();
        }
        workoutInfoRepository.deleteAll();

        workoutInfoService.generateAndSaveWorkout(2, 3);
        List<WorkoutInfo> workoutInfos3 = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos3).hasSize(14);
        for (WorkoutInfo workoutInfo : workoutInfos3) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 3).isTrue();
        }
        workoutInfoRepository.deleteAll();

        workoutInfoService.generateAndSaveWorkout(2, 4);
        List<WorkoutInfo> workoutInfos4 = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos4).hasSize(21);
        for (WorkoutInfo workoutInfo : workoutInfos4) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 4).isTrue();
        }
        workoutInfoRepository.deleteAll();

        workoutInfoService.generateAndSaveWorkout(2, 5);
        List<WorkoutInfo> workoutInfos5 = workoutInfoService.getByUserId(2);
        assertThat(workoutInfos5).hasSize(21);
        for (WorkoutInfo workoutInfo : workoutInfos5) {
            assertThat(workoutInfo.getWorkoutsPerWeek() == 5).isTrue();
        }
        workoutInfoRepository.deleteAll();
    }
}
