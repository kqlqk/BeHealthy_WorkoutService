package me.kqlqk.behealthy.workout_service.feign_client;

import me.kqlqk.behealthy.workout_service.dto.UserConditionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "conditionService")
public interface ConditionClient {
    @GetMapping("/api/v1/condition")
    UserConditionDTO getUserConditionByUserId(@RequestParam long userId);
}
