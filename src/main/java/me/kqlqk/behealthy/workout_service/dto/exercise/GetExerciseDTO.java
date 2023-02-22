package me.kqlqk.behealthy.workout_service.dto.exercise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.enums.MuscleGroup;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetExerciseDTO {
    private String name;
    private String description;
    private MuscleGroup muscleGroup;
    private Integer alternativeId;

    public static GetExerciseDTO convert(Exercise exercise) {
        return new GetExerciseDTO(exercise.getName(), exercise.getDescription(), exercise.getMuscleGroup(), exercise.getAlternativeId());
    }

    public static List<GetExerciseDTO> convertList(List<Exercise> exercises) {
        List<GetExerciseDTO> res = new ArrayList<>();

        exercises.forEach(e -> res.add(convert(e)));

        return res;
    }
}
