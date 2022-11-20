package me.kqlqk.behealthy.workout_service.controller;

import me.kqlqk.behealthy.workout_service.dto.WorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.WorkoutNotFoundException;
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
    public List<WorkoutInfoDTO> getWorkout(@RequestParam long userId) {
        if (!workoutInfoService.existsByUserId(userId)) {
            throw new WorkoutNotFoundException("User's workout with userId = " + userId + " not found");
        }

        return WorkoutInfoDTO.convertListOfWorkoutInfoToListOfWorkoutInfoDTO(workoutInfoService.getByUserId(userId));
    }

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@RequestParam long userId, @RequestBody WorkoutInfoDTO workoutInfoDTO) {
        if (workoutInfoService.existsByUserId(userId)) {
            throw new WorkoutNotFoundException("User's workout with userId = " + userId + " already exists");
        }

        workoutInfoService.generateAndSaveWorkout(userId, workoutInfoDTO.getWorkoutsPerWeek());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/workout")
    public void updateWorkout(@RequestParam long userId, @RequestBody WorkoutInfoDTO workoutInfoDTO) {
        if (!workoutInfoService.existsByUserId(userId)) {
            throw new WorkoutNotFoundException("User's workout with userId = " + userId + " not found");
        }

        workoutInfoService.generateAndSaveWorkout(userId, workoutInfoDTO.getWorkoutsPerWeek());
    }
}
