package me.kqlqk.behealthy.workout_service.dto;

import lombok.Data;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;

@Data
public class UserWorkoutDTO {
    private long id;
    private String exerciseName;
    private MuscleGroup muscleGroup;
    private int reps;
    private int sets;
    private int numberPerDay;
    private int day;
    private long userId;

    public static UserWorkout convertUserWorkoutDTOtoUserWorkout(UserWorkoutDTO userWorkoutDTO) {
        UserWorkout userWorkout = new UserWorkout();
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
