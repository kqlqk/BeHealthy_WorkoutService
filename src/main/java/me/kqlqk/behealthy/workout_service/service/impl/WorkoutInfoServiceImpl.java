package me.kqlqk.behealthy.workout_service.service.impl;

import lombok.NonNull;
import me.kqlqk.behealthy.workout_service.dto.ExerciseDTO;
import me.kqlqk.behealthy.workout_service.dto.UserConditionDTO;
import me.kqlqk.behealthy.workout_service.dto.WorkoutInfoDTO;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;
import me.kqlqk.behealthy.workout_service.exception.exceptions.ExerciseNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.WorkoutNotFoundException;
import me.kqlqk.behealthy.workout_service.exception.exceptions.conditionService.ExerciseException;
import me.kqlqk.behealthy.workout_service.feign_client.ConditionClient;
import me.kqlqk.behealthy.workout_service.model.Exercise;
import me.kqlqk.behealthy.workout_service.model.WorkoutInfo;
import me.kqlqk.behealthy.workout_service.repository.WorkoutInfoRepository;
import me.kqlqk.behealthy.workout_service.service.ExerciseService;
import me.kqlqk.behealthy.workout_service.service.WorkoutInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkoutInfoServiceImpl implements WorkoutInfoService {
    private final WorkoutInfoRepository workoutInfoRepository;
    private final ConditionClient conditionClient;
    private final ExerciseService exerciseService;
    private final Validator validator;

    @Autowired
    public WorkoutInfoServiceImpl(WorkoutInfoRepository workoutInfoRepository, ConditionClient conditionClient, ExerciseService exerciseService, Validator validator) {
        this.workoutInfoRepository = workoutInfoRepository;
        this.conditionClient = conditionClient;
        this.exerciseService = exerciseService;
        this.validator = validator;
    }


    @Override
    public List<WorkoutInfo> getByUserId(long userId) {
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
    public boolean existsByUserId(long userId) {
        return workoutInfoRepository.existsByUserId(userId);
    }

    @Override
    public void save(@NonNull WorkoutInfoDTO workoutInfoDTO) {
        workoutInfoRepository.save(workoutInfoDTO.convertToWorkoutInfo());
    }

    private void saveListOfWorkout(@NonNull List<WorkoutInfo> workoutInfos) {
        for (WorkoutInfo workoutInfo : workoutInfos) {
            save(WorkoutInfoDTO.convertToWorkoutInfoDTO(workoutInfo));
        }
    }

    @Override
    public void updateWorkoutWithAlternativeExercise(long userId, @NonNull ExerciseDTO toChange) {
        Set<ConstraintViolation<ExerciseDTO>> constraintViolationsName = validator.validateProperty(toChange, "name");

        if (!constraintViolationsName.isEmpty()) {
            throw new ExerciseException(constraintViolationsName.iterator().next().getMessage());
        }

        List<WorkoutInfo> workout = getByUserId(userId);
        List<Exercise> alternatives = exerciseService.getAlternative(toChange);

        if (workout
                .stream()
                .noneMatch(w -> w.getExercise().getId() == toChange.getId())) {
            throw new ExerciseNotFoundException("User's workout hasn't exercise with name = " + toChange.getName());
        }

        Exercise alternativeExercise = alternatives.get((int) (Math.random() * alternatives.size()));

        for (WorkoutInfo workoutInfo : workout) {
            if (workoutInfo.getExercise().getId() == toChange.getId()) {
                workoutInfo.setExercise(alternativeExercise);
                break;
            }
        }

        saveListOfWorkout(workout);
    }

    @Override
    public void generateAndSaveWorkout(long userId, int workoutsPerWeek) {
        if (userId < 1) {
            throw new WorkoutNotFoundException("userId should be > 1");
        }
        if (workoutsPerWeek < 1 || workoutsPerWeek > 5) {
            throw new WorkoutNotFoundException("workoutsPerWeek should be between 1 and 5");
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
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                6,
                5));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));
    }

    private void generateAndSave2DaysMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                6,
                5));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                5));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                4));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));
    }

    private void generateAndSave3DaysMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                5));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                6));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                8,
                5));

        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                5));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                5));
    }

    private void generateAndSave4DaysMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                5));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                6,
                5));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FOREARMS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                5));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                12,
                5));
    }

    private void generateAndSave5DaysMaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST).get(0),
                8,
                3));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS_LATS).get(0),
                8,
                3));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BICEPS_FOREARMS).get(0),
                8,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
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
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                10,
                3));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));
    }

    private void generateAndSave2DaysFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                6,
                5));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                10,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                10,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS_LATS).get(0),
                8,
                3));
    }

    private void generateAndSave3DaysFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                15,
                3));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                12,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                6));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                3));
    }

    private void generateAndSave4DaysFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                15,
                3));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                12,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                6));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                10,
                3));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));
    }


    private void generateAndSave5DaysFemaleSplit(long userId) {
        int numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                4));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                15,
                3));

        save(new WorkoutInfoDTO(
                userId,
                1,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                12,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FULL_BACK).get(0),
                8,
                6));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRAPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                2,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LOWER_BACK).get(0),
                12,
                3));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.FRONT_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.LATERAL_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.REAR_DELTS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                3,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                15,
                4));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CHEST_TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.TRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                4,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.BUTTOKS).get(0),
                10,
                3));


        numberPerDay = 1;
        save(new WorkoutInfoDTO(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS_BUTTOKS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.QUADRICEPS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.HAMSTRINGS).get(0),
                8,
                3));

        save(new WorkoutInfoDTO(
                userId,
                5,
                numberPerDay++,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.CALVES).get(0),
                15,
                3));

        save(new WorkoutInfoDTO(
                userId,
                5,
                numberPerDay,
                exerciseService.getSpecificAmountOfMuscleGroup(1, MuscleGroup.ABS).get(0),
                12,
                4));
    }
}
