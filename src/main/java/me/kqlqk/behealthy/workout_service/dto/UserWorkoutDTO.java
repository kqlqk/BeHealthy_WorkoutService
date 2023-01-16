package me.kqlqk.behealthy.workout_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkoutDTO {
    private long id;

    @Size(min = 1, max = 50, message = "exerciseName should be between 1 and 50 characters")
    @NotBlank(message = "exerciseName should be between 1 and 50 characters")
    private String exerciseName;

    @NotNull(message = "muscleGroup cannot be null")
    private MuscleGroup muscleGroup;

    @Min(value = 0, message = "reps should be between 0 and 1000")
    @Max(value = 1000, message = "reps should be between 0 and 1000")
    private int reps;

    @Min(value = 1, message = "sets should be between 1 and 100")
    @Max(value = 100, message = "sets should be between 1 and 100")
    private int sets;

    @Min(value = 1, message = "numberPerDay should be between 1 and 100")
    @Max(value = 100, message = "numberPerDay should be between 1 and 100")
    private int numberPerDay;

    @Min(value = 1, message = "day should be between 1 and 7")
    @Max(value = 7, message = "day should be between 1 and 7")
    private int day;

    private long userId;

    public UserWorkoutDTO(String exerciseName, MuscleGroup muscleGroup, int reps, int sets, int numberPerDay, int day, long userId) {
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.reps = reps;
        this.sets = sets;
        this.numberPerDay = numberPerDay;
        this.day = day;
        this.userId = userId;
    }

    public static UserWorkout convertUserWorkoutDTOtoUserWorkout(UserWorkoutDTO userWorkoutDTO) {
        UserWorkout userWorkout = new UserWorkout();
        userWorkout.setId(userWorkoutDTO.getId());
        userWorkout.setExerciseName(userWorkoutDTO.getExerciseName());
        userWorkout.setMuscleGroup(userWorkoutDTO.getMuscleGroup());
        userWorkout.setReps(userWorkoutDTO.getReps());
        userWorkout.setSets(userWorkoutDTO.getSets());
        userWorkout.setNumberPerDay(userWorkoutDTO.getNumberPerDay());
        userWorkout.setDay(userWorkoutDTO.getDay());
        userWorkout.setUserId(userWorkoutDTO.getUserId());

        return userWorkout;
    }

}
