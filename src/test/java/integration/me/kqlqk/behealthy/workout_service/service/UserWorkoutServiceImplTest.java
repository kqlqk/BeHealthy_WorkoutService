package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.dto.UserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.exception.exceptions.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.UserWorkoutException;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.service.impl.UserWorkoutServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
public class UserWorkoutServiceImplTest {
    @Autowired
    private UserWorkoutServiceImpl userWorkoutService;

    @Test
    public void getByUserId_shouldReturnListWithUserWorkout() {
        List<UserWorkout> workouts = userWorkoutService.getByUserId(1);

        assertThat(workouts).hasSize(2);
    }

    @Test
    public void save_shouldSaveWorkoutToDb() {
        UserWorkoutDTO userWorkoutDTO = new UserWorkoutDTO();
        userWorkoutDTO.setUserId(1);
        userWorkoutDTO.setExerciseName("some exercise");
        userWorkoutDTO.setDay(1);
        userWorkoutDTO.setNumberPerDay(1);
        userWorkoutDTO.setMuscleGroup(MuscleGroup.ABS);
        userWorkoutDTO.setReps(10);
        userWorkoutDTO.setSets(4);

        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);

        userWorkoutService.save(userWorkoutDTO);

        List<UserWorkout> newWorkouts = userWorkoutService.getByUserId(1);

        assertThat(newWorkouts).hasSize(oldWorkouts.size() + 1);
    }

    @Test
    public void save_shouldThrowException() {
        String badName = " ";
        UserWorkoutDTO userWorkoutDTO = new UserWorkoutDTO(badName, MuscleGroup.ABS, 10, 4, 1, 1, 1);
        assertThrows(UserWorkoutException.class, () -> userWorkoutService.save(userWorkoutDTO));

        UserWorkoutDTO userWorkoutDTO2 = new UserWorkoutDTO("some exercise", null, 10, 4, 1, 1, 1);
        assertThrows(UserWorkoutException.class, () -> userWorkoutService.save(userWorkoutDTO2));

        int badReps = -1;
        UserWorkoutDTO userWorkoutDTO3 = new UserWorkoutDTO("some exercise", MuscleGroup.ABS, badReps, 4, 1, 1, 1);
        assertThrows(UserWorkoutException.class, () -> userWorkoutService.save(userWorkoutDTO3));

        int badSets = 0;
        UserWorkoutDTO userWorkoutDTO4 = new UserWorkoutDTO("some exercise", MuscleGroup.ABS, 10, badSets, 1, 1, 1);
        assertThrows(UserWorkoutException.class, () -> userWorkoutService.save(userWorkoutDTO4));

        int badNumberPerDay = -4;
        UserWorkoutDTO userWorkoutDTO5 = new UserWorkoutDTO("some exercise", MuscleGroup.ABS, 10, 4, badNumberPerDay, 1, 1);
        assertThrows(UserWorkoutException.class, () -> userWorkoutService.save(userWorkoutDTO5));

        int badDay = 100;
        UserWorkoutDTO userWorkoutDTO6 = new UserWorkoutDTO("some exercise", MuscleGroup.ABS, 10, 4, 1, badDay, 1);
        assertThrows(UserWorkoutException.class, () -> userWorkoutService.save(userWorkoutDTO6));
    }

    @Test
    public void remove_shouldRemoveExerciseFromDb() {
        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);
        long existedIdForCurrentUser = oldWorkouts.get(0).getId();

        userWorkoutService.remove(1, existedIdForCurrentUser);

        List<UserWorkout> newWorkouts = userWorkoutService.getByUserId(1);

        assertThat(newWorkouts).hasSize(oldWorkouts.size() - 1);
    }

    @Test
    public void remove_shouldThrowException() {
        int notExistedId = 999;

        assertThrows(ExerciseNotFoundException.class, () -> userWorkoutService.remove(1, notExistedId));
    }
}
