package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.dto.UserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
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
        UserWorkoutDTO userWorkoutDTO = new UserWorkoutDTO();
        String badName = " ";
        userWorkoutDTO.setExerciseName(badName);
        userWorkoutDTO.setUserId(1);
        userWorkoutDTO.setDay(1);
        userWorkoutDTO.setNumberPerDay(1);
        userWorkoutDTO.setMuscleGroup(MuscleGroup.ABS);
        userWorkoutDTO.setReps(10);
        userWorkoutDTO.setSets(4);

        assertThrows(UserWorkoutException.class, () -> userWorkoutService.save(userWorkoutDTO));


        UserWorkoutDTO userWorkoutDTO2 = new UserWorkoutDTO();
        userWorkoutDTO2.setExerciseName("some exercise");
        userWorkoutDTO2.setUserId(1);
        userWorkoutDTO2.setDay(1);
        userWorkoutDTO2.setNumberPerDay(1);
        userWorkoutDTO2.setMuscleGroup(null);
        userWorkoutDTO2.setReps(10);
        userWorkoutDTO2.setSets(4);

        assertThrows(UserWorkoutException.class, () -> userWorkoutService.save(userWorkoutDTO2));
    }

    @Test
    public void remove_shouldRemoveExerciseFromDb() {
        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);
        String existedNameForCurrentUser = "deadlift";
        long existedIdForCurrentUser = oldWorkouts.get(0).getId();

        userWorkoutService.remove(1, existedIdForCurrentUser);
        userWorkoutService.remove(1, existedNameForCurrentUser);

        List<UserWorkout> newWorkouts = userWorkoutService.getByUserId(1);

        assertThat(newWorkouts).hasSize(oldWorkouts.size() - 2);
    }

    @Test
    public void remove_shouldNotRemoveExerciseFromDb() {
        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);
        int notExistedId = 999;

        userWorkoutService.remove(1, notExistedId);

        List<UserWorkout> newWorkouts = userWorkoutService.getByUserId(1);

        assertThat(newWorkouts).hasSize(oldWorkouts.size());
    }
}
