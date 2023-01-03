package me.kqlqk.behealthy.workout_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;

import javax.persistence.*;

@Entity
@Table(name = "user_workouts", schema = "public", catalog = "workoutservicedb")
@Data
@NoArgsConstructor
public class UserWorkouts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "muscle_group", nullable = false)
    private MuscleGroup muscleGroup;

    @Column(name = "reps", nullable = false)
    private int reps;

    @Column(name = "sets", nullable = false)
    private int sets;

    @Column(name = "number_per_day", nullable = false)
    private int numberPerDay;

    @Column(name = "day", nullable = false)
    private int day;
}
