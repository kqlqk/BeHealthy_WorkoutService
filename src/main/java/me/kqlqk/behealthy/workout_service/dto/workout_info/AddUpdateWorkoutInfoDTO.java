package me.kqlqk.behealthy.workout_service.dto.workout_info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUpdateWorkoutInfoDTO {
    @Min(value = 1, message = "WorkoutsPerWeek should be between 1 and 5")
    @Max(value = 5, message = "WorkoutsPerWeek should be between 1 and 5")
    private int workoutsPerWeek;
}
