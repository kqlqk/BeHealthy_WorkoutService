package me.kqlqk.behealthy.workout_service.dto;

import lombok.Data;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserWorkoutDTO {
    private long id;

    @Size(min = 1, max = 50, message = "exerciseName should be between 1 and 50 characters")
    @NotBlank(message = "exerciseName should be between 1 and 50 characters")
    private String exerciseName;

    @NotNull
    private MuscleGroup muscleGroup;

    private int reps;

    private int sets;

    private int numberPerDay;

    private int day;

    private long userId;

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
