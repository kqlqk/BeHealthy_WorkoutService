package me.kqlqk.behealthy.workout_service.dto.user_workout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserWorkoutDTO {
    @Size(min = 1, max = 50, message = "ExerciseName should be between 1 and 50 characters")
    @NotBlank(message = "ExerciseName should be between 1 and 50 characters")
    private String exerciseName;

    @Min(value = 0, message = "Rep should be between 0 and 1000")
    @Max(value = 1000, message = "Rep should be between 0 and 1000")
    private int rep;

    @Min(value = 1, message = "Set should be between 1 and 100")
    @Max(value = 100, message = "Set should be between 1 and 100")
    private int set;

    @Min(value = 1, message = "NumberPerDay should be between 1 and 100")
    @Max(value = 100, message = "NumberPerDay should be between 1 and 100")
    private int numberPerDay;

    @Min(value = 1, message = "Day should be between 1 and 7")
    @Max(value = 7, message = "Day should be between 1 and 7")
    private int day;
}
