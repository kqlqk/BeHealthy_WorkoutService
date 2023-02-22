package me.kqlqk.behealthy.workout_service.controller;

import me.kqlqk.behealthy.workout_service.dto.exercise.GetExerciseDTO;
import me.kqlqk.behealthy.workout_service.dto.workout_info.AddUpdateWorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.dto.workout_info.GetWorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.exercise.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.model.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import me.kqlqk.behealthy.workout_service.service.WorkoutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<GetWorkoutInfoDTO> getWorkoutInfos(@RequestParam long userId) {
        return GetWorkoutInfoDTO.convertList(workoutInfoService.getByUserId(userId));
    }

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkoutInfos(@RequestParam long userId, @RequestBody @Valid AddUpdateWorkoutInfoDTO addWorkoutInfoDTO) {
        workoutInfoService.generateAndSaveCompleteWorkout(userId, addWorkoutInfoDTO.getWorkoutsPerWeek());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/workout")
    public ResponseEntity<?> updateWorkoutInfos(@RequestParam long userId, @RequestBody @Valid AddUpdateWorkoutInfoDTO updateWorkoutInfoDTO) {
        workoutInfoService.generateAndSaveCompleteWorkout(userId, updateWorkoutInfoDTO.getWorkoutsPerWeek());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/exercises")
    public ResponseEntity<?> getExercisesByParams(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String muscleGroup) {
        if (name == null && muscleGroup == null) {
            throw new ExerciseNotFoundException("Was not provided 'name' or 'muscleGroup' filter");
        }
        if (name != null && muscleGroup != null) {
            throw new ExerciseNotFoundException("Provide only 1 filter");
        }

        if (name != null) {
            return ResponseEntity.ok(GetExerciseDTO.convert(exerciseService.getByName(name)));
        }
        else {
            return ResponseEntity.ok(GetExerciseDTO.convertList(exerciseService.getByMuscleGroup(MuscleGroup.valueOf(muscleGroup.toUpperCase()))));
        }
    }

    @PutMapping("/workout/alternative")
    public ResponseEntity<?> updateWorkoutWithAlternativeExercise(@RequestParam long userId,
                                                                  @RequestParam String exerciseName) {
        workoutInfoService.updateWorkoutWithAlternativeExercise(userId, exerciseService.getByName(exerciseName));

        return ResponseEntity.ok().build();
    }
}
