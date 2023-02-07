package me.kqlqk.behealthy.workout_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutInfoDTO {
    private long id;
    private long userId;
    private int day;
    private int numberPerDay;
    private Exercise exercise;
    private int reps;
    private int sets;

    @Min(value = 2, message = "workoutsPerWeek should be valid (2 or 3 or 4)")
    @Max(value = 4, message = "workoutsPerWeek should be valid (2 or 3 or 4)")
    private int workoutsPerWeek;

    public WorkoutInfoDTO(long id, long userId, int day, int numberPerDay, Exercise exercise, int reps, int sets) {
        this.id = id;
        this.userId = userId;
        this.day = day;
        this.numberPerDay = numberPerDay;
        this.exercise = exercise;
        this.reps = reps;
        this.sets = sets;
    }

    public WorkoutInfoDTO(long userId, int day, int numberPerDay, Exercise exercise, int reps, int sets) {
        this.userId = userId;
        this.day = day;
        this.numberPerDay = numberPerDay;
        this.exercise = exercise;
        this.reps = reps;
        this.sets = sets;
    }

    public static List<WorkoutInfoDTO> convertListOfWorkoutInfoToListOfWorkoutInfoDTO(List<WorkoutInfo> workoutInfos) {
        List<WorkoutInfoDTO> res = new ArrayList<>();

        for (WorkoutInfo workoutInfo : workoutInfos) {
            WorkoutInfoDTO workoutInfoDTO = new WorkoutInfoDTO(
                    workoutInfo.getId(),
                    workoutInfo.getUserId(),
                    workoutInfo.getDay(),
                    workoutInfo.getNumberPerDay(),
                    workoutInfo.getExercise(),
                    workoutInfo.getReps(),
                    workoutInfo.getSets());

            res.add(workoutInfoDTO);
        }

        return res;
    }

    public static WorkoutInfoDTO convertToWorkoutInfoDTO(WorkoutInfo workoutInfo) {
        return new WorkoutInfoDTO(
                workoutInfo.getId(),
                workoutInfo.getUserId(),
                workoutInfo.getDay(),
                workoutInfo.getNumberPerDay(),
                workoutInfo.getExercise(),
                workoutInfo.getReps(),
                workoutInfo.getSets());
    }

    public WorkoutInfo convertToWorkoutInfo() {
        return new WorkoutInfo(id, userId, day, numberPerDay, exercise, reps, sets);
    }
}
