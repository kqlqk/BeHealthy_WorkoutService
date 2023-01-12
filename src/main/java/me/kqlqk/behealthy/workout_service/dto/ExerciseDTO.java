package me.kqlqk.behealthy.workout_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.Exercise;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDTO {
    private int id;

    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    private String description;

    private MuscleGroup muscleGroup;

    private Integer alternativeId;

    public static ExerciseDTO convertExerciseToExerciseDTO(Exercise exercise) {
        return new ExerciseDTO(exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getMuscleGroup(),
                exercise.getAlternativeId());
    }
}
