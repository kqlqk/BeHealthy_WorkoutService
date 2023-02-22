package me.kqlqk.behealthy.workout_service.feign_client;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import me.kqlqk.behealthy.workout_service.exception.exceptions.RuntimeNotWrappedByHystrixException;
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
        }
        catch (IOException e) {
            if (e instanceof JsonParseException) {
                throw new RuntimeNotWrappedByHystrixException("Service is unavailable");
            }

            throw new RuntimeNotWrappedByHystrixException(e.getMessage());
        }

        String errorMessage;
        if (info.get("info") != null) {
            errorMessage = info.get("info");
        }
        else if (info.get("error") != null) {
            errorMessage = info.get("error");
        }
        else {
            errorMessage = "No details about exception";
        }

        return new RuntimeNotWrappedByHystrixException(errorMessage);
    }
}
