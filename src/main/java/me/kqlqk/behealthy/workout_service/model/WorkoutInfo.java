package me.kqlqk.behealthy.workout_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "workout_info", schema = "public", catalog = "workoutservicedb")
@Data
@NoArgsConstructor
public class WorkoutInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "day", nullable = false)
    private int day;

    @Column(name = "number_per_day", nullable = false)
    private int numberPerDay;

    @OneToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(name = "reps", nullable = false)
    private int reps;

    @Column(name = "sets", nullable = false)
    private int sets;

    @Transient
    private int workoutsPerWeek;

    public WorkoutInfo(long userId, Exercise exercise, int day, int numberPerDay, int reps, int sets) {
        this.userId = userId;
        this.exercise = exercise;
        this.day = day;
        this.numberPerDay = numberPerDay;
        this.reps = reps;
        this.sets = sets;
    }
}
