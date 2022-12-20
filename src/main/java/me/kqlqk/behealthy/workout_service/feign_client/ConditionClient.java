package me.kqlqk.behealthy.workout_service.feign_client;

import me.kqlqk.behealthy.workout_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.MicroserviceException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeoutException;

@FeignClient(name = "conditionService", fallbackFactory = ConditionClient.Fallback.class)
public interface ConditionClient {
    @GetMapping("/api/v1/condition")
    UserConditionDTO getUserConditionByUserId(@RequestParam long userId);

    @Component
    class Fallback implements FallbackFactory<ConditionClient> {
        @Override
        public ConditionClient create(Throwable cause) {
            if (cause instanceof TimeoutException) {
                throw new MicroserviceException("Service is unavailable");
            }

            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else {
                throw new RuntimeException("Unhandled exception: " + cause.getMessage());
            }
        }
    }
}
