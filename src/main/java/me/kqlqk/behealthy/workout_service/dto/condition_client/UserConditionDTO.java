package me.kqlqk.behealthy.workout_service.dto.condition_client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.model.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConditionDTO {
    private long userId;
    private Gender gender;
}