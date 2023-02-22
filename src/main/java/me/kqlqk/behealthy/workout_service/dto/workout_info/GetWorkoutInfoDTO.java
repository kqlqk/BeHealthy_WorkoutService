package me.kqlqk.behealthy.workout_service.dto.workout_info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.dto.exercise.GetExerciseDTO;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkoutInfoDTO {
    private int day;
    private int numberPerDay;
    private GetExerciseDTO exercise;
    private int rep;
    private int set;
    private int workoutsPerWeek;

    public static List<GetWorkoutInfoDTO> convertList(List<WorkoutInfo> workoutInfos) {
        List<GetWorkoutInfoDTO> res = new ArrayList<>();

        workoutInfos.forEach(w -> res.add(new GetWorkoutInfoDTO(w.getDay(),
                                                                w.getNumberPerDay(),
                                                                GetExerciseDTO.convert(w.getExercise()),
                                                                w.getRep(),
                                                                w.getSet(),
                                                                w.getWorkoutsPerWeek())));

        return res;
    }
}
