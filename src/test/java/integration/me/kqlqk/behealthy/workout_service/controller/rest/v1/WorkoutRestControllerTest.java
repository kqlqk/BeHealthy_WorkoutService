package integration.me.kqlqk.behealthy.workout_service.controller.rest.v1;

import annotations.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class WorkoutRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getWorkout_shouldReturnWorkoutList() throws Exception {
        mockMvc.perform(get("/api/v1/workout")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].userId").exists())
                .andExpect(jsonPath("$[0].workoutDay").exists())
                .andExpect(jsonPath("$[0].numberPerDay").exists())
                .andExpect(jsonPath("$[0].exercise").isMap())
                .andExpect(jsonPath("$[0].exercise.id").exists())
                .andExpect(jsonPath("$[0].exercise.name").exists())
                .andExpect(jsonPath("$[0].exercise.description").exists())
                .andExpect(jsonPath("$[0].exercise.muscleGroup").exists());
    }
}
