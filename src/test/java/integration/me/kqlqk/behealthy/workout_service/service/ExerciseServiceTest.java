package integration.me.kqlqk.behealthy.workout_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.service.impl.ExerciseServiceImpl;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
public class ExerciseServiceTest {
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
}
