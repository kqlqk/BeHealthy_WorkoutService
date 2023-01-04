package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.exception.exceptions.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.service.impl.ExerciseServiceImpl;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
public class ExerciseServiceImplTest {
    @Autowired
    private ExerciseServiceImpl exerciseService;

    @RepeatedTest(50)
    public void getSpecificAmountOfMuscleGroup_shouldReturnSpecificAmountOfMuscleGroup() {
        List<Exercise> exercises = exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.CHEST_TRICEPS);

        assertThat(exercises).isNotEmpty();

        for (Exercise exercise : exercises) {
            assertThat(exercise.getMuscleGroup()).isEqualTo(MuscleGroup.CHEST_TRICEPS);
        }
    }

    @Test
    public void getAlternative_shouldReturnListWithAlternativeExercises() {
        Exercise e = exerciseService.getByName("dips");

        List<Exercise> exercises = exerciseService.getAlternative(e);

        assertThat(exercises).hasSize(1);
        assertThat(exercises.get(0).getName()).isEqualTo("bench press");
        assertThat(exercises.get(0).getAlternativeId()).isEqualTo(e.getAlternativeId());
    }

    @Test
    public void getAlternative_shouldReturnException() {
        Exercise e = exerciseService.getByName("machine fly");

        assertThrows(ExerciseNotFoundException.class, () -> exerciseService.getAlternative(e));
    }
}
