package me.kqlqk.behealthy.workout_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutInfoDTO {
    private long id;
    private int workoutDay;
    private int numberPerDay;
    private Exercise exercise;
    private int workoutsPerWeek;

    public static List<WorkoutInfoDTO> convertListOfWorkoutInfoToListOfWorkoutInfoDTO(List<WorkoutInfo> workoutInfos) {
        List<WorkoutInfoDTO> res = new ArrayList<>();

        for (WorkoutInfo workoutInfo : workoutInfos) {
            WorkoutInfoDTO workoutInfoDTO = new WorkoutInfoDTO(
                    workoutInfo.getId(),
                    workoutInfo.getWorkoutDay(),
                    workoutInfo.getNumberPerDay(),
                    workoutInfo.getExercise(),
                    workoutInfo.getWorkoutsPerWeek());

            res.add(workoutInfoDTO);
        }

        return res;
    }
}
