package me.kqlqk.behealthy.workout_service.controller;

import me.kqlqk.behealthy.workout_service.dto.UserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.WorkoutNotFound;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import me.kqlqk.behealthy.workout_service.service.WorkoutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class WorkoutRestController {
    private final WorkoutInfoService workoutInfoService;

    @Autowired
    public WorkoutRestController(WorkoutInfoService workoutInfoService) {
        this.workoutInfoService = workoutInfoService;
    }

    @GetMapping("/workout")
    public List<WorkoutInfo> getWorkout(@RequestParam("userId") long userId) {
        if (!workoutInfoService.existsByUserId(userId)) {
            throw new WorkoutNotFound("User's workout with userId = " + userId + " not found");
        }

        return workoutInfoService.getByUserId(userId);
    }

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@RequestBody UserWorkoutDTO userWorkoutDTO) {
        if (workoutInfoService.existsByUserId(userWorkoutDTO.getUserId())) {
            throw new WorkoutNotFound("User's workout with userId = " + userWorkoutDTO.getUserId() + " already exists");
        }

        workoutInfoService.generateAndSaveWorkout(userWorkoutDTO.getUserId(), userWorkoutDTO.getWorkoutsPerWeek());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/workout")
    public void updateWorkout(@RequestBody UserWorkoutDTO userWorkoutDTO) {
        if (!workoutInfoService.existsByUserId(userWorkoutDTO.getUserId())) {
            throw new WorkoutNotFound("User's workout with userId = " + userWorkoutDTO.getUserId() + " not found");
        }

        workoutInfoService.generateAndSaveWorkout(userWorkoutDTO.getUserId(), userWorkoutDTO.getWorkoutsPerWeek());
    }
}
