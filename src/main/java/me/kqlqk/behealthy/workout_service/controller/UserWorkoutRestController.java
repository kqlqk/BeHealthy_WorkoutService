package me.kqlqk.behealthy.workout_service.controller;

import me.kqlqk.behealthy.workout_service.dto.user_workout.AddUserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.dto.user_workout.GetUserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.service.UserWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserWorkoutRestController {
    private final UserWorkoutService userWorkoutService;

    @Autowired
    public UserWorkoutRestController(UserWorkoutService userWorkoutService) {
        this.userWorkoutService = userWorkoutService;
    }

    @GetMapping("/workout/user")
    public List<GetUserWorkoutDTO> getUserWorkout(@RequestParam long userId) {
        return GetUserWorkoutDTO.convertList(userWorkoutService.getByUserId(userId));
    }

    @PostMapping("/workout/user")
    public ResponseEntity<?> addExercise(@RequestParam long userId, @RequestBody @Valid AddUserWorkoutDTO addUserWorkoutDTO) {
        UserWorkout userWorkout = new UserWorkout();
        userWorkout.setUserId(userId);
        userWorkout.setExerciseName(addUserWorkoutDTO.getExerciseName());
        userWorkout.setRep(addUserWorkoutDTO.getRep());
        userWorkout.setSet(addUserWorkoutDTO.getSet());
        userWorkout.setNumberPerDay(addUserWorkoutDTO.getNumberPerDay());
        userWorkout.setDay(addUserWorkoutDTO.getDay());

        userWorkoutService.save(userWorkout);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/workout/user")
    public ResponseEntity<?> removeExercise(@RequestParam long userId,
                                            @RequestParam String exerciseName) {
        userWorkoutService.remove(userId, exerciseName);

        return ResponseEntity.ok().build();
    }
}
