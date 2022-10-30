package integration.me.kqlqk.behealthy.workout_service.repository;

import annotations.RepositoryTest;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class ExerciseRepositoryTest {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    public void findById_shouldFindByIdOrReturnNull() {
        Exercise exercise = exerciseRepository.findById(1);

        assertThat(exercise).isNotNull();
        assertThat(exercise.getId()).isEqualTo(1);

        Exercise nullExercise = exerciseRepository.findById(99);
        assertThat(nullExercise).isNull();
    }

    @Test
    public void findByName_shouldFindByNameOrReturnNull() {
        Exercise exercise = exerciseRepository.findByName("bench press");

        assertThat(exercise).isNotNull();
        assertThat(exercise.getName()).isEqualTo("bench press");

        Exercise nullExercise = exerciseRepository.findByName("random");
        assertThat(nullExercise).isNull();
    }

    @Test
    public void findByMuscleGroup_shouldFindByMuscleGroupOrReturnEmptyList() {
        List<Exercise> exercises = exerciseRepository.findByMuscleGroup(MuscleGroup.CHEST_TRICEPS);

        assertThat(exercises).isNotNull();
    }
}


