package integration.me.kqlqk.behealthy.workout_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.workout_service.dto.user_workout.AddUserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.service.impl.UserWorkoutServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class UserWorkoutRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserWorkoutServiceImpl userWorkoutService;

    @Test
    public void getUserWorkout_shouldReturnListWithUserWorkout() throws Exception {
        mockMvc.perform(get("/api/v1/workout/user")
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].exerciseName").exists())
                .andExpect(jsonPath("$[0].rep").exists())
                .andExpect(jsonPath("$[0].set").exists())
                .andExpect(jsonPath("$[0].numberPerDay").exists())
                .andExpect(jsonPath("$[0].day").exists());
    }

    @Test
    public void getUserWorkout_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(get("/api/v1/workout/user")
                                .param("userId", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("UserWorkout with userId = 0 not found")));

        mockMvc.perform(get("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }

    @Test
    public void addExercise_shouldSaveUserWorkoutToDb() throws Exception {
        AddUserWorkoutDTO addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 12, 4, 1, 1);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addExercise_shouldReturnJsonWithException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AddUserWorkoutDTO addUserWorkoutDTO = new AddUserWorkoutDTO("", 12, 4, 1, 1);
        String json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("ExerciseName should be between 1 and 50 characters")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("123456789012345678901234567890123456789012345678901", 12, 4, 1, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("ExerciseName should be between 1 and 50 characters")));


        addUserWorkoutDTO = new AddUserWorkoutDTO(null, 12, 4, 1, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("ExerciseName should be between 1 and 50 characters")));


        addUserWorkoutDTO = new AddUserWorkoutDTO(" ", 12, 4, 1, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("ExerciseName should be between 1 and 50 characters")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", -1, 4, 1, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Rep should be between 0 and 1000")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 1001, 4, 1, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Rep should be between 0 and 1000")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 12, 0, 1, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Set should be between 1 and 100")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 12, 101, 1, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Set should be between 1 and 100")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 12, 4, 0, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("NumberPerDay should be between 1 and 100")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 12, 4, 101, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("NumberPerDay should be between 1 and 100")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 12, 4, 1, 0);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Day should be between 1 and 7")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 12, 4, 1, 8);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Day should be between 1 and 7")));


        addUserWorkoutDTO = new AddUserWorkoutDTO("new exercise", 12, 4, 1, 1);
        json = objectMapper.writeValueAsString(addUserWorkoutDTO);

        mockMvc.perform(post("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));
    }

    @Test
    public void removeExercise_shouldRemoveExerciseFromDb() throws Exception {
        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);
        String exerciseName = oldWorkouts.get(0).getExerciseName();

        mockMvc.perform(delete("/api/v1/workout/user")
                                .param("userId", "1")
                                .param("exerciseName", exerciseName))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void removeExercise_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(delete("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("exerciseName", "exercise"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'userId' for method parameter type long is not present")));

        mockMvc.perform(delete("/api/v1/workout/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Required request parameter 'exerciseName' for method parameter type String is not present")));
    }
}
