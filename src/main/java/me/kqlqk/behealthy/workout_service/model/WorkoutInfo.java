package me.kqlqk.behealthy.workout_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "workout_info", schema = "public", catalog = "workout_service_db")
@Data
@NoArgsConstructor
public class WorkoutInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "workout_day", nullable = false)
    private int day;

    @Column(name = "number_per_day", nullable = false)
    private int numberPerDay;

    @OneToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(name = "exercise_rep", nullable = false)
    private int rep;

    @Column(name = "exercise_set", nullable = false)
    private int set;

    @Transient
    private int workoutsPerWeek;

    public WorkoutInfo(long userId, int day, int numberPerDay, Exercise exercise, int rep, int set) {
        this.userId = userId;
        this.exercise = exercise;
        this.day = day;
        this.numberPerDay = numberPerDay;
        this.rep = rep;
        this.set = set;
    }

    public WorkoutInfo(long id, long userId, int day, int numberPerDay, Exercise exercise, int rep, int set) {
        this.id = id;
        this.userId = userId;
        this.day = day;
        this.numberPerDay = numberPerDay;
        this.exercise = exercise;
        this.rep = rep;
        this.set = set;
    }
}
