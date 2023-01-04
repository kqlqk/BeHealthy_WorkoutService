package integration.me.kqlqk.behealthy.workout_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.workout_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.dto.WorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.enums.Gender;
import me.kqlqk.behealthy.workout_service.feign_client.ConditionClient;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import me.kqlqk.behealthy.workout_service.service.WorkoutInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class WorkoutRestControllerTest {
    @MockBean
    private ConditionClient conditionClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkoutInfoService workoutInfoService;

    @Autowired
    private ExerciseService exerciseService;

    @Test
    public void getWorkout_shouldReturnWorkoutList() throws Exception {
        mockMvc.perform(get("/api/v1/workout")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].day").exists())
                .andExpect(jsonPath("$[0].numberPerDay").exists())
                .andExpect(jsonPath("$[0].exercise").isMap())
                .andExpect(jsonPath("$[0].exercise.id").exists())
                .andExpect(jsonPath("$[0].exercise.name").exists())
                .andExpect(jsonPath("$[0].exercise.description").exists())
                .andExpect(jsonPath("$[0].exercise.muscleGroup").exists())
                .andExpect(jsonPath("$[0].reps").exists())
                .andExpect(jsonPath("$[0].sets").exists());
    }

    @Test
    public void createWorkout_shouldCreateWorkout() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO(1, 2, Gender.MALE);
        when(conditionClient.getUserConditionByUserId(2)).thenReturn(userConditionDTO);

        WorkoutInfoDTO workoutInfoDTO = new WorkoutInfoDTO();
        workoutInfoDTO.setWorkoutsPerWeek(3);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(workoutInfoDTO);

        mockMvc.perform(post("/api/v1/workout")
                        .param("userId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createWorkout_shouldReturnJsonWithException() throws Exception {
        WorkoutInfoDTO workoutInfoDTO = new WorkoutInfoDTO();
        workoutInfoDTO.setWorkoutsPerWeek(3);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(workoutInfoDTO);

        mockMvc.perform(post("/api/v1/workout")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("WorkoutNotFound | User's workout with userId = 1 already exists")));
    }

    @Test
    public void updateWorkout_shouldUpdateWorkout() throws Exception {
        UserConditionDTO userConditionDTO = new UserConditionDTO(1, 1, Gender.MALE);
        when(conditionClient.getUserConditionByUserId(1)).thenReturn(userConditionDTO);

        WorkoutInfoDTO workoutInfoDTO = new WorkoutInfoDTO();
        workoutInfoDTO.setWorkoutsPerWeek(2);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(workoutInfoDTO);

        mockMvc.perform(put("/api/v1/workout")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateWorkout_shouldReturnJsonWithException() throws Exception {
        WorkoutInfoDTO workoutInfoDTO = new WorkoutInfoDTO();
        workoutInfoDTO.setWorkoutsPerWeek(2);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(workoutInfoDTO);

        mockMvc.perform(put("/api/v1/workout")
                        .param("userId", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("WorkoutNotFound | User's workout with userId = 2 not found")));
    }

    @Test
    public void getExercisesByParams_shouldReturnExerciseByParams() throws Exception {
        mockMvc.perform(get("/api/v1/exercises")
                        .param("name", "dips"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.muscleGroup").exists());


        mockMvc.perform(get("/api/v1/exercises")
                        .param("muscleGroup", "chest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].description").exists())
                .andExpect(jsonPath("$[0].muscleGroup").exists());
    }

    @Test
    public void getExercisesByParams_shouldReturnJsonWithException() throws Exception {
        mockMvc.perform(get("/api/v1/exercises")
                        .param("name", "dips")
                        .param("muscleGroup", "chest"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("ExerciseNotFound | Provide only 1 filter")));

        mockMvc.perform(get("/api/v1/exercises"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.info").exists())
                .andExpect(jsonPath("$.info", is("ExerciseNotFound | Was not provided 'name' or 'muscleGroup'")));
    }

    @Test
    public void updateWorkoutWithAlternativeExercise_shouldUpdateWorkoutWithAlternativeExercise() throws Exception {
        Exercise toChange = exerciseService.getByName("seated dumbbell press");

        mockMvc.perform(put("/api/v1/workout/alternative")
                        .param("userId", "1")
                        .param("exerciseNameToChange", "seated dumbbell press"))
                .andDo(print())
                .andExpect(status().isOk());

        workoutInfoService.getByUserId(1).forEach(workoutInfo ->
                assertThat(workoutInfo.getExercise().getId()).isNotEqualTo(toChange.getId()));
    }

}
