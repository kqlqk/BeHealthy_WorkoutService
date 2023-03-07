package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.exception.exceptions.exercise.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.user_workout.UserWorkoutNotFoundException;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.repository.UserWorkoutRepository;
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

    @Autowired
    private UserWorkoutRepository userWorkoutRepository;

    @Test
    public void getByUserId_shouldReturnListWithUserWorkout() {
        List<UserWorkout> workouts = userWorkoutService.getByUserId(1);

        assertThat(workouts).hasSize(2);
    }

    @Test
    public void save_shouldSaveUserWorkoutToDb() {
        UserWorkout userWorkout = new UserWorkout();
        userWorkout.setUserId(1);
        userWorkout.setExerciseName("some exercise");
        userWorkout.setDay(1);
        userWorkout.setNumberPerDay(1);
        userWorkout.setRep(10);
        userWorkout.setSet(4);

        int oldUserWorkoutSize = userWorkoutRepository.findAll().size();

        userWorkoutService.save(userWorkout);

        int newUserWorkoutSize = userWorkoutRepository.findAll().size();

        assertThat(newUserWorkoutSize).isEqualTo(oldUserWorkoutSize + 1);
    }

    @Test
    public void remove_shouldRemoveExerciseFromDb() {
        int oldUserWorkoutSize = userWorkoutRepository.findAll().size();

        userWorkoutService.remove(1, "bench press");

        int newUserWorkoutSize = userWorkoutRepository.findAll().size();

        assertThat(newUserWorkoutSize).isEqualTo(oldUserWorkoutSize - 1);
    }

    @Test
    public void remove_shouldThrowException() {
        assertThrows(UserWorkoutNotFoundException.class, () -> userWorkoutService.remove(0, "bench press"));
        assertThrows(ExerciseNotFoundException.class, () -> userWorkoutService.remove(1, "badName"));
    }
}
