package me.kqlqk.behealthy.workout_service.feign_client;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import me.kqlqk.behealthy.workout_service.exception.exceptions.MicroserviceException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.conditionService.UserConditionAlreadyExistsException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.conditionService.UserConditionNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        Map<String, String> info;
        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            info = objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            if (e instanceof JsonParseException) {
                throw new MicroserviceException("Service is unavailable");
            }

            throw new RuntimeException(e);
        }

        String errorMessage = info.get("info") != null ? info.get("info") : "No details about exception";

        if (response.status() == 404) {
            return new RuntimeException(errorMessage);
        }

        if (errorMessage.startsWith("UserConditionAlreadyExists")) {
            throw new UserConditionAlreadyExistsException(getErrorMessageWithoutPrefix(errorMessage, "UserConditionAlreadyExists"));
        } else if (errorMessage.startsWith("UserConditionNotFound")) {
            throw new UserConditionNotFoundException(getErrorMessageWithoutPrefix(errorMessage, "UserConditionNotFound"));
        }

        return null;
    }

    private String getErrorMessageWithoutPrefix(String errorWithPrefix, String prefix) {
        String[] arr = errorWithPrefix.split(prefix + " \\| ");

        return arr[1];
    }
}
