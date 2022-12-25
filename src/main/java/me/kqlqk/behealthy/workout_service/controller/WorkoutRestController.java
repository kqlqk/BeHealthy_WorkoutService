package me.kqlqk.behealthy.workout_service.controller;

import me.kqlqk.behealthy.workout_service.dto.WorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.exception.exceptions.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.WorkoutNotFoundException;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import me.kqlqk.behealthy.workout_service.service.WorkoutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
public class WorkoutRestController {
    private final WorkoutInfoService workoutInfoService;
    private final ExerciseService exerciseService;

    @Autowired
    public WorkoutRestController(WorkoutInfoService workoutInfoService, ExerciseService exerciseService) {
        this.workoutInfoService = workoutInfoService;
        this.exerciseService = exerciseService;
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

    @GetMapping("/exercises")
    public ResponseEntity<?> getExercisesByParams(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String muscleGroup) {
        if (name == null && muscleGroup == null) {
            throw new ExerciseNotFoundException("Was not provided 'name' or 'muscleGroup'");
        }
        if (name != null && muscleGroup != null) {
            throw new ExerciseNotFoundException("Provide only 1 filter");
        }

        if (name != null) {
            return ResponseEntity.ok(exerciseService.getByName(name));
        } else {
            Stream.of(MuscleGroup.values())
                    .filter(m -> m.name().equalsIgnoreCase(muscleGroup))
                    .findAny()
                    .orElseThrow(() -> new ExerciseNotFoundException("Muscle group not found"));

            return ResponseEntity.ok(exerciseService.getByMuscleGroup(MuscleGroup.valueOf(muscleGroup.toUpperCase())));
        }
    }
}
