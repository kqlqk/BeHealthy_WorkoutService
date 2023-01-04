package integration.me.kqlqk.behealthy.workout_service.controller.rest.v1;

import annotations.ControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kqlqk.behealthy.workout_service.dto.UserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.service.impl.UserWorkoutServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        mockMvc.perform(get("/api/v1/user/workout")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].exerciseName").exists())
                .andExpect(jsonPath("$[0].muscleGroup").exists())
                .andExpect(jsonPath("$[0].reps").exists())
                .andExpect(jsonPath("$[0].sets").exists())
                .andExpect(jsonPath("$[0].numberPerDay").exists())
                .andExpect(jsonPath("$[0].day").exists())
                .andExpect(jsonPath("$[0].userId").exists());
    }

    @Test
    public void addExercise_shouldAddExerciseToDb() throws Exception {
        UserWorkoutDTO userWorkoutDTO = new UserWorkoutDTO();
        userWorkoutDTO.setExerciseName("new exercise");
        userWorkoutDTO.setMuscleGroup(MuscleGroup.ABS);
        userWorkoutDTO.setReps(12);
        userWorkoutDTO.setSets(4);
        userWorkoutDTO.setNumberPerDay(1);
        userWorkoutDTO.setDay(1);
        userWorkoutDTO.setUserId(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userWorkoutDTO);

        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);

        mockMvc.perform(post("/api/v1/user/workout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk());

        List<UserWorkout> newWorkouts = userWorkoutService.getByUserId(1);

        assertThat(newWorkouts).hasSize(oldWorkouts.size() + 1);
    }

    @Test
    public void removeExercise_shouldRemoveExerciseFromDb() throws Exception {
        List<UserWorkout> oldWorkouts = userWorkoutService.getByUserId(1);
        String existedIdForCurrentUser = String.valueOf(oldWorkouts.get(0).getId());
        String existedNameForCurrentUser = "deadlift";

        mockMvc.perform(delete("/api/v1/user/workout")
                        .param("userId", "1")
                        .param("exerciseId", existedIdForCurrentUser))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/v1/user/workout")
                        .param("userId", "1")
                        .param("exerciseName", existedNameForCurrentUser))
                .andDo(print())
                .andExpect(status().isOk());

        List<UserWorkout> newWorkouts = userWorkoutService.getByUserId(1);

        assertThat(newWorkouts).hasSize(oldWorkouts.size() - 2);
    }
}
