package me.kqlqk.behealthy.workout_service.controller;

import me.kqlqk.behealthy.workout_service.dto.UserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.WorkoutNotFoundException;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.service.UserWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserWorkoutRestController {
    private final UserWorkoutService userWorkoutService;

    @Autowired
    public UserWorkoutRestController(UserWorkoutService userWorkoutService) {
        this.userWorkoutService = userWorkoutService;
    }

    @GetMapping("/workout")
    public List<UserWorkout> getUserWorkout(@RequestParam long userId) {
        List<UserWorkout> workouts = userWorkoutService.getByUserId(userId);

        if (workouts.isEmpty()) {
            throw new WorkoutNotFoundException("User's with userId = " + userId + " workout not found");
        }

        return workouts;
    }

    @PostMapping("/workout")
    public ResponseEntity<?> addExercise(@RequestParam long userId, @RequestBody UserWorkoutDTO userWorkoutDTO) {
        userWorkoutDTO.setUserId(userId);
        userWorkoutService.save(userWorkoutDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/workout")
    public ResponseEntity<?> removeExercise(@RequestParam long userId,
                                            @RequestParam Long exerciseId) {
        userWorkoutService.remove(userId, exerciseId);

        return ResponseEntity.ok().build();
    }
}
