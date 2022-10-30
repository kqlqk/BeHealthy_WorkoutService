package me.kqlqk.behealthy.workout_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConditionDTO {
    private long id;
    private long userId;
    private Gender gender;
}