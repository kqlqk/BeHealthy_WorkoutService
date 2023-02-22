package me.kqlqk.behealthy.workout_service.dto.user_workout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.model.enums.MuscleGroup;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserWorkoutDTO {
    private String exerciseName;
    private MuscleGroup muscleGroup;
    private int rep;
    private int set;
    private int numberPerDay;
    private int day;

    public static List<GetUserWorkoutDTO> convertList(List<UserWorkout> userWorkouts) {
        List<GetUserWorkoutDTO> res = new ArrayList<>();

        userWorkouts.forEach(u -> res.add(new GetUserWorkoutDTO(u.getExerciseName(),
                                                                u.getMuscleGroup(),
                                                                u.getRep(),
                                                                u.getSet(),
                                                                u.getNumberPerDay(),
                                                                u.getDay())));

        return res;
    }
}
