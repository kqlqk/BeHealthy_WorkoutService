package me.kqlqk.behealthy.workout_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.workout_service.model.UserWorkout;
import me.kqlqk.behealthy.workout_service.repository.UserWorkoutRepository;
import me.kqlqk.behealthy.workout_service.service.UserWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return userWorkoutRepository.getByUserId(userId);
    }

    @Override
    public void save(@NonNull UserWorkout userWorkout) {
        userWorkoutRepository.save(userWorkout);
    }

    @Override
    public void remove(long id) {
        userWorkoutRepository.deleteById(id);
    }
}
