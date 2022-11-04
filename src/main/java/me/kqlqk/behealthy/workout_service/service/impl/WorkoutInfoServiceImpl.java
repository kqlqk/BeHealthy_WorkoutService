package me.kqlqk.behealthy.workout_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.workout_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.enums.Gender;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.enums.WorkoutsPerWeek;
import me.kqlqk.behealthy.workout_service.exception.exceptions.GenderIsOnDevelopingException;
import me.kqlqk.behealthy.workout_service.feign_client.KcalsCounterClient;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import me.kqlqk.behealthy.workout_service.repository.WorkoutInfoRepository;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import me.kqlqk.behealthy.workout_service.service.WorkoutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WorkoutInfoServiceImpl implements WorkoutInfoService {
    private final WorkoutInfoRepository workoutInfoRepository;
    private final KcalsCounterClient kcalsCounterClient;
    private final ExerciseService exerciseService;

    @Autowired
    public WorkoutInfoServiceImpl(WorkoutInfoRepository workoutInfoRepository, KcalsCounterClient kcalsCounterClient, ExerciseService exerciseService) {
        this.workoutInfoRepository = workoutInfoRepository;
        this.kcalsCounterClient = kcalsCounterClient;
        this.exerciseService = exerciseService;
    }

    @Override
    public WorkoutInfo getById(long id) {
        return workoutInfoRepository.findById(id);
    }

    @Override
    public List<WorkoutInfo> getByUserId(long userId) {
        return workoutInfoRepository.findByUserId(userId);
    }

    @Override
    public List<WorkoutInfo> getByUserIdAndWorkoutDay(long userId, int workoutDay) {
        return workoutInfoRepository.findByUserIdAndWorkoutDay(userId, workoutDay);
    }

    @Override
    public boolean existsByUserId(long userId) {
        return workoutInfoRepository.existsByUserId(userId);
    }

    @Override
    public void save(@NonNull WorkoutInfo workoutInfo) {
        workoutInfoRepository.save(workoutInfo);
    }

    @Override
    public void generateAndSaveWorkout(long userId, @NonNull WorkoutsPerWeek workoutsPerWeek) {
        UserConditionDTO userConditionDTO = kcalsCounterClient.getUserConditionByUserId(userId);

        if (userConditionDTO.getGender() != Gender.MALE) {
            throw new GenderIsOnDevelopingException("Workouts for females are on developing, coming soon in next updates");
        }

        switch (workoutsPerWeek) {
            case TWO:
                generateAndSaveUpperLowerBodySplit(userId);
                break;

            case THREE:
                generateAndSavePushPullLegsSplit(userId);
                break;

            case FOUR:
                generateAndSave4DaysSplit(userId);
                break;
        }

    }

    public void generateAndSaveUpperLowerBodySplit(long userId) {
        int numberPerDay = 1;
        List<Exercise> split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.CHEST_TRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS_CHEST),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 1, numberPerDay++));
        }


        numberPerDay = 1;
        split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 11, numberPerDay++));
        }


        numberPerDay = 1;
        split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.QUADRICEPS_BUTTOKS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 2, numberPerDay++));
        }
    }

    public void generateAndSavePushPullLegsSplit(long userId) {
        int numberPerDay = 1;
        List<Exercise> split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.CHEST_TRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST),
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.TRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 1, numberPerDay++));
        }


        numberPerDay = 1;
        split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK),
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.BICEPS))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 2, numberPerDay++));
        }


        numberPerDay = 1;
        split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS),
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.QUADRICEPS_BUTTOKS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 3, numberPerDay++));
        }
    }

    public void generateAndSave4DaysSplit(long userId) {
        int numberPerDay = 1;
        List<Exercise> split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.CHEST_TRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.CHEST),
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.TRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 1, numberPerDay++));
        }


        numberPerDay = 1;
        split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK),
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.BICEPS))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 2, numberPerDay++));
        }

        numberPerDay = 1;
        split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS),
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.REAR_DELTS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FOREARMS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 3, numberPerDay++));
        }

        numberPerDay = 1;
        split = Stream.of(
                        exerciseService.getSpecificAmountOfMuscleGroup(2, MuscleGroup.QUADRICEPS_BUTTOKS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS),
                        exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Exercise exercise : split) {
            save(new WorkoutInfo(userId, exercise, 4, numberPerDay++));
        }
    }

}
