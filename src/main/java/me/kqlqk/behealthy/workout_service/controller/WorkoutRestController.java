package me.kqlqk.behealthy.workout_service.controller;

import me.kqlqk.behealthy.workout_service.exception.exceptions.WorkoutNotFound;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import me.kqlqk.behealthy.workout_service.service.WorkoutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
