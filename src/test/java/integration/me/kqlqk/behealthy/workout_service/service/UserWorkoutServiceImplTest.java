package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.service.impl.UserWorkoutServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        UserWorkout userWorkout = new UserWorkout();
        userWorkout.setUserId(1);
        userWorkout.setExerciseName("some exercise");
        userWorkout.setDay(1);
        userWorkout.setNumberPerDay(1);
        userWorkout.setMuscleGroup(MuscleGroup.ABS);
        userWorkout.setReps(10);
        userWorkout.setSets(4);

        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);

        userWorkoutService.save(userWorkout);

        List<UserWorkout> newWorkouts = userWorkoutService.getByUserId(1);

        assertThat(newWorkouts).hasSize(oldWorkouts.size() + 1);
    }

    @Test
    public void remove_shouldRemoveExerciseFromDb() {
        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);

        userWorkoutService.remove(oldWorkouts.get(0).getId());

        List<UserWorkout> newWorkouts = userWorkoutService.getByUserId(1);

        assertThat(newWorkouts).hasSize(oldWorkouts.size() - 1);
    }
}
