package me.kqlqk.behealthy.workout_service.feign_client;

import lombok.extern.slf4j.Slf4j;
import me.kqlqk.behealthy.workout_service.dto.condition_client.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.RuntimeNotWrappedByHystrixException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userConditionService", fallbackFactory = UserConditionClient.Fallback.class)
public interface UserConditionClient {
    @GetMapping("/api/v1/condition")
    UserConditionDTO getUserConditionByUserId(@RequestParam long userId);

    @Component
    @Slf4j
    class Fallback implements FallbackFactory<UserConditionClient> {
        @Override
        public UserConditionClient create(Throwable cause) {
            log.warn("Something went wrong: ", cause);

            throw new RuntimeNotWrappedByHystrixException("Service is unavailable");
        }
    }
}
