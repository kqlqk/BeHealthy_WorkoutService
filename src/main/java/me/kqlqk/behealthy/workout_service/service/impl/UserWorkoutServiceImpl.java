package me.kqlqk.behealthy.workout_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.workout_service.dto.UserWorkoutDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.UserWorkoutException;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.repository.UserWorkoutRepository;
import me.kqlqk.behealthy.workout_service.service.UserWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class UserWorkoutServiceImpl implements UserWorkoutService {
    private final UserWorkoutRepository userWorkoutRepository;
    private final Validator validator;

    @Autowired
    public UserWorkoutServiceImpl(UserWorkoutRepository userWorkoutRepository, Validator validator) {
        this.userWorkoutRepository = userWorkoutRepository;
        this.validator = validator;
    }

    @Override
    public List<UserWorkout> getByUserId(long userId) {
        return userWorkoutRepository.getByUserId(userId);
    }

    @Override
    public void save(@NonNull UserWorkoutDTO userWorkoutDTO) {
        Set<ConstraintViolation<UserWorkoutDTO>> constraintViolationsName = validator.validate(userWorkoutDTO);

        if (!constraintViolationsName.isEmpty()) {
            throw new UserWorkoutException(constraintViolationsName.iterator().next().getMessage());
        }

        userWorkoutRepository.save(UserWorkoutDTO.convertUserWorkoutDTOtoUserWorkout(userWorkoutDTO));
    }

    @Override
    public void remove(long userId, @NonNull String exerciseName) {
        if (!userWorkoutRepository.existsByUserId(userId)) {
            throw new ExerciseNotFoundException("Exercises for user with userId = " + userId + " not found");
        }

        if (!userWorkoutRepository.existsByExerciseName(exerciseName)) {
            throw new ExerciseNotFoundException("Exercise with name = " + exerciseName + " not found");
        }

        List<UserWorkout> userWorkouts = getByUserId(userId);

        for (UserWorkout userWorkout : userWorkouts) {
            if (userWorkout.getExerciseName().equalsIgnoreCase(exerciseName)) {
                userWorkoutRepository.deleteById(userWorkout.getId());
            }
        }
    }

    @Override
    public void remove(long userId, long exerciseId) {
        if (!userWorkoutRepository.existsByUserId(userId)) {
            throw new ExerciseNotFoundException("Exercises for user with userId = " + userId + " not found");
        }

        List<UserWorkout> userWorkouts = getByUserId(userId);

        for (UserWorkout userWorkout : userWorkouts) {
            if (userWorkout.getId() == exerciseId) {
                userWorkoutRepository.deleteById(exerciseId);
            }
        }

    }
}
