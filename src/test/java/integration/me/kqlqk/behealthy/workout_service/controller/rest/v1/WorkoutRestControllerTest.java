package integration.me.kqlqk.behealthy.workout_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.workout_service.dto.condition_client.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.dto.workout_info.AddUpdateWorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.feign_client.UserConditionClient;
import me.kqlqk.behealthy.workout_service.model.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class WorkoutRestControllerTest {
    @MockBean
    private UserConditionClient userConditionClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getWorkoutInfos_shouldReturnWorkoutInfos() throws Exception {
        mockMvc.perform(get("/api/v1/workout")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].day").exists())
                .andExpect(jsonPath("$[0].numberPerDay").exists())
                .andExpect(jsonPath("$[0].exercise").isMap())
                .andExpect(jsonPath("$[0].exercise.name").exists())
                .andExpect(jsonPath("$[0].exercise.description").exists())
                .andExpect(jsonPath("$[0].exercise.muscleGroup").exists())
                .andExpect(jsonPath("$[0].rep").exists())
                .andExpect(jsonPath("$[0].set").exists());
    }

    @Test
    public void getWorkoutInfos_shouldReturnJsonException() throws Exception {
        mockMvc.perform(get("/api/v1/workout")
                                .param("userId", "0")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("WorkoutInfos with userId = 0 not found")));
    }

    @Test
    public void createWorkoutInfos_shouldCreateWorkoutInfos() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO(2, Gender.MALE);
        when(userConditionClient.getUserConditionByUserId(2)).thenReturn(userConditionDTO);

        AddUpdateWorkoutInfoDTO addWorkoutInfoDTO = new AddUpdateWorkoutInfoDTO(3);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(addWorkoutInfoDTO);

        mockMvc.perform(post("/api/v1/workout")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createWorkoutInfos_shouldReturnJsonWithException() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO(2, Gender.MALE);
        when(userConditionClient.getUserConditionByUserId(2)).thenReturn(userConditionDTO);

        ObjectMapper mapper = new ObjectMapper();
        AddUpdateWorkoutInfoDTO addWorkoutInfoDTO = new AddUpdateWorkoutInfoDTO(0);
        String json = mapper.writeValueAsString(addWorkoutInfoDTO);

        mockMvc.perform(post("/api/v1/workout")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("WorkoutsPerWeek should be between 1 and 5")));


        addWorkoutInfoDTO = new AddUpdateWorkoutInfoDTO(6);
        json = mapper.writeValueAsString(addWorkoutInfoDTO);

        mockMvc.perform(post("/api/v1/workout")
                                .param("userId", "2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("WorkoutsPerWeek should be between 1 and 5")));
    }

    @Test
    public void updateWorkoutInfos_shouldUpdateWorkoutInfos() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO(1, Gender.MALE);
        when(userConditionClient.getUserConditionByUserId(1)).thenReturn(userConditionDTO);

        ObjectMapper mapper = new ObjectMapper();
        AddUpdateWorkoutInfoDTO updateWorkoutInfoDTO = new AddUpdateWorkoutInfoDTO(5);
        String json = mapper.writeValueAsString(updateWorkoutInfoDTO);

        mockMvc.perform(put("/api/v1/workout")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateWorkoutInfos_shouldReturnJsonWithException() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO(1, Gender.MALE);
        when(userConditionClient.getUserConditionByUserId(1)).thenReturn(userConditionDTO);

        ObjectMapper mapper = new ObjectMapper();
        AddUpdateWorkoutInfoDTO addWorkoutInfoDTO = new AddUpdateWorkoutInfoDTO(0);
        String json = mapper.writeValueAsString(addWorkoutInfoDTO);

        mockMvc.perform(post("/api/v1/workout")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("WorkoutsPerWeek should be between 1 and 5")));


        addWorkoutInfoDTO = new AddUpdateWorkoutInfoDTO(6);
        json = mapper.writeValueAsString(addWorkoutInfoDTO);

        mockMvc.perform(post("/api/v1/workout")
                                .param("userId", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("WorkoutsPerWeek should be between 1 and 5")));
    }

    @Test
    public void getExercisesByParams_shouldReturnExerciseOrExercisesByParams() throws Exception {
        mockMvc.perform(get("/api/v1/exercises")
                                .param("name", "dips")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.muscleGroup").exists())
                .andExpect(jsonPath("$.alternativeId").exists());


        mockMvc.perform(get("/api/v1/exercises")
                                .param("muscleGroup", "chest")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].description").exists())
                .andExpect(jsonPath("$[0].muscleGroup").exists());
    }

    @Test
    public void getExercisesByParams_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(get("/api/v1/exercises")
                                .param("name", "dips")
                                .param("muscleGroup", "chest")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Provide only 1 filter")));

        mockMvc.perform(get("/api/v1/exercises")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("Was not provided 'name' or 'muscleGroup' filter")));
    }

    @Test
    public void updateWorkoutWithAlternativeExercise_shouldUpdateWorkoutWithAlternativeExercise() throws Exception {
        mockMvc.perform(put("/api/v1/workout/alternative")
                                .param("userId", "1")
                                .param("exerciseName", "seated dumbbell press"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
