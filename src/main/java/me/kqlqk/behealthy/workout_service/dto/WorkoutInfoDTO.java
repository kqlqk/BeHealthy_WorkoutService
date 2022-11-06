package me.kqlqk.behealthy.workout_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.enums.WorkoutsPerWeek;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutInfoDTO {
    private long userId;
    private WorkoutsPerWeek workoutsPerWeek;
}
