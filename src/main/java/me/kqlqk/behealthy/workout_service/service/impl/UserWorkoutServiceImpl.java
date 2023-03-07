package me.kqlqk.behealthy.workout_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.workout_service.exception.exceptions.exercise.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.user_workout.UserWorkoutNotFoundException;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.repository.UserWorkoutRepository;
import me.kqlqk.behealthy.workout_service.service.UserWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserWorkoutServiceImpl implements UserWorkoutService {
    private final UserWorkoutRepository userWorkoutRepository;

    @Autowired
    public UserWorkoutServiceImpl(UserWorkoutRepository userWorkoutRepository) {
        this.userWorkoutRepository = userWorkoutRepository;
    }

    @Override
    public List<UserWorkout> getByUserId(long userId) {
        if (!userWorkoutRepository.existsByUserId(userId)) {
            throw new UserWorkoutNotFoundException("UserWorkout with userId = " + userId + " not found");
        }

        return userWorkoutRepository.getByUserId(userId);
    }

    @Override
    public void save(@NonNull UserWorkout userWorkout) {
        userWorkout.setId(0);
        userWorkoutRepository.save(userWorkout);
    }

    @Override
    @Transactional
    public void remove(long userId, String exerciseName) {
        if (!userWorkoutRepository.existsByExerciseNameIgnoreCase(exerciseName)) {
            throw new ExerciseNotFoundException("Exercise with exerciseName = " + exerciseName + " for user with userId = " + userId + " not found");
        }

        List<UserWorkout> userWorkouts = getByUserId(userId);

        for (UserWorkout userWorkout : userWorkouts) {
            if (userWorkout.getExerciseName().equalsIgnoreCase(exerciseName)) {
                userWorkoutRepository.deleteByExerciseNameIgnoreCase(exerciseName);
            }
        }

    }
}
