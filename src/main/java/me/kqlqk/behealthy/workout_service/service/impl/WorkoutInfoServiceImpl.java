package me.kqlqk.behealthy.workout_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.workout_service.dto.condition_client.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.exception.exceptions.exercise.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.workout_info.WorkoutInfoAlreadyExistsException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.workout_info.WorkoutInfoNotFoundException;
import me.kqlqk.behealthy.workout_service.feign_client.ConditionClient;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import me.kqlqk.behealthy.workout_service.model.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.repository.WorkoutInfoRepository;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import me.kqlqk.behealthy.workout_service.service.WorkoutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutInfoServiceImpl implements WorkoutInfoService {
    private final WorkoutInfoRepository workoutInfoRepository;
    private final ConditionClient conditionClient;
    private final ExerciseService exerciseService;

    @Autowired
    public WorkoutInfoServiceImpl(WorkoutInfoRepository workoutInfoRepository, ConditionClient conditionClient, ExerciseService exerciseService) {
        this.workoutInfoRepository = workoutInfoRepository;
        this.conditionClient = conditionClient;
        this.exerciseService = exerciseService;
    }


    @Override
    public List<WorkoutInfo> getByUserId(long userId) {
        if (!workoutInfoRepository.existsByUserId(userId)) {
            throw new WorkoutInfoNotFoundException("WorkoutInfos with userId = " + userId + " not found");
        }

        List<WorkoutInfo> workoutInfos = workoutInfoRepository.findByUserId(userId);

        return setWorkoutsPerWeekAndReturn(workoutInfos);
    }

    private List<WorkoutInfo> setWorkoutsPerWeekAndReturn(List<WorkoutInfo> workoutInfos) {
        int maxWorkoutsPerWeek = 1;
        for (WorkoutInfo workoutInfo : workoutInfos) {
            if (workoutInfo.getDay() > maxWorkoutsPerWeek) {
                maxWorkoutsPerWeek = workoutInfo.getDay();
            }
        }

        int finalMaxWorkoutsPerWeek = maxWorkoutsPerWeek;
        return workoutInfos
                .stream()
                .peek(workoutInfo -> workoutInfo.setWorkoutsPerWeek(finalMaxWorkoutsPerWeek))
                .collect(Collectors.toList());
    }

    @Override
    public void save(@NonNull WorkoutInfo workoutInfo) {
        if (workoutInfoRepository.existsById(workoutInfo.getId())) {
            throw new WorkoutInfoAlreadyExistsException("WorkoutInfo with id = " + workoutInfo.getId() + " already exists");
        }

        workoutInfo.setId(0);
        workoutInfoRepository.save(workoutInfo);
    }

    @Override
    public void update(@NonNull WorkoutInfo workoutInfo) {
        if (!workoutInfoRepository.existsById(workoutInfo.getId())) {
            throw new WorkoutInfoNotFoundException("WorkoutInfo with id = " + workoutInfo.getId() + " not found");
        }

        workoutInfoRepository.save(workoutInfo);
    }


    @Override
    public void updateWorkoutWithAlternativeExercise(long userId, @NonNull Exercise toChange) {
        List<WorkoutInfo> workout = getByUserId(userId);
        List<Exercise> alternatives = exerciseService.getAlternative(toChange);

        if (workout.stream().noneMatch(w -> w.getExercise().getId() == toChange.getId())) {
            throw new ExerciseNotFoundException("User's workout hasn't exercise with name = " + toChange.getName());
        }

        Exercise alternativeExercise = alternatives.get((int) (Math.random() * alternatives.size()));

        for (WorkoutInfo workoutInfo : workout) {
            if (workoutInfo.getExercise().getId() == toChange.getId()) {
                workoutInfo.setExercise(alternativeExercise);
                break;
            }
        }

        workout.forEach(this::update);
    }

    @Override
    @Transactional
    public void generateAndSaveCompleteWorkout(long userId, int workoutsPerWeek) {
        if (userId < 1) {
            throw new WorkoutInfoNotFoundException("userId should be > 1");
        }
        if (workoutsPerWeek < 1 || workoutsPerWeek > 5) {
            throw new WorkoutInfoNotFoundException("workoutsPerWeek should be between 1 and 5");
        }
        if (workoutInfoRepository.existsByUserId(userId)) {
            workoutInfoRepository.deleteByUserId(userId);
        }

        UserConditionDTO userConditionDTO = conditionClient.getUserConditionByUserId(userId);

        switch (userConditionDTO.getGender()) {
            case MALE:
                generateMaleWorkout(userId, workoutsPerWeek);
                break;

            case FEMALE:
                generateFemaleWorkout(userId, workoutsPerWeek);
                break;
        }

    }

    private void generateMaleWorkout(long userId, int workoutsPerWeek) {
        switch (workoutsPerWeek) {
            case 1:
                generateAndSave1DayMaleSplit(userId);
                break;

            case 2:
                generateAndSave2DaysMaleSplit(userId);
                break;

            case 3:
                generateAndSave3DaysMaleSplit(userId);
                break;

            case 4:
                generateAndSave4DaysMaleSplit(userId);
                break;

            case 5:
                generateAndSave5DaysMaleSplit(userId);
                break;
        }
    }

    private void generateAndSave1DayMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                6,
                5));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));
    }

    private void generateAndSave2DaysMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                6,
                5));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                5));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                4));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));
    }

    private void generateAndSave3DaysMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                5));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                6));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                8,
                5));

        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                5));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                5));
    }

    private void generateAndSave4DaysMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                5));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                6,
                5));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FOREARMS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                5));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                5));
    }

    private void generateAndSave5DaysMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                3));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS_LATS).get(0),
                8,
                3));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS_FOREARMS).get(0),
                8,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                4));
    }

    private void generateFemaleWorkout(long userId, int workoutsPerWeek) {
        switch (workoutsPerWeek) {
            case 1:
                generateAndSave1DayFemaleSplit(userId);
                break;

            case 2:
                generateAndSave2DaysFemaleSplit(userId);
                break;

            case 3:
                generateAndSave3DaysFemaleSplit(userId);
                break;

            case 4:
                generateAndSave4DaysFemaleSplit(userId);
                break;

            case 5:
                generateAndSave5DaysFemaleSplit(userId);
                break;
        }
    }

    private void generateAndSave1DayFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                10,
                3));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));
    }

    private void generateAndSave2DaysFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                6,
                5));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                10,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                10,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS_LATS).get(0),
                8,
                3));
    }

    private void generateAndSave3DaysFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                15,
                3));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                12,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                6));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                3));
    }

    private void generateAndSave4DaysFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                15,
                3));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                12,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                6));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                10,
                3));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));
    }


    private void generateAndSave5DaysFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                4));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                15,
                3));

        save(new WorkoutInfo(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                12,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                6));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                4,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                10,
                3));


        numberPerDay = 1;
        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                3));

        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                15,
                3));

        save(new WorkoutInfo(
                userId,
                5,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                12,
                4));
    }
}
