package me.kqlqk.behealthy.workout_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.enums.WorkoutsPerWeek;
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
    private WorkoutsPerWeek workoutsPerWeek;

    public static List<WorkoutInfoDTO> convertListOfWorkoutInfoToListOfWorkoutInfoDTO(List<WorkoutInfo> workoutInfos) {
        List<WorkoutInfoDTO> res = new ArrayList<>();

        WorkoutsPerWeek wpw = null;
        int maxWorkoutDay = 2;

        for (WorkoutInfo workoutInfo : workoutInfos) {
            if (workoutInfo.getWorkoutDay() > maxWorkoutDay) {
                maxWorkoutDay = workoutInfo.getWorkoutDay();
            }
        }

        switch (maxWorkoutDay) {
            case 2:
                wpw = WorkoutsPerWeek.TWO;
                break;
            case 3:
                wpw = WorkoutsPerWeek.THREE;
                break;
            case 4:
                wpw = WorkoutsPerWeek.FOUR;
                break;
        }

        for (WorkoutInfo workoutInfo : workoutInfos) {
            WorkoutInfoDTO workoutInfoDTO = new WorkoutInfoDTO(
                    workoutInfo.getId(),
                    workoutInfo.getWorkoutDay(),
                    workoutInfo.getNumberPerDay(),
                    workoutInfo.getExercise(),
                    wpw);

            res.add(workoutInfoDTO);
        }

        return res;
    }
}
