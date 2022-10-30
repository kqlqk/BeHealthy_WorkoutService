package me.kqlqk.behealthy.workout_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "workout_info")
@Getter
@Setter
@NoArgsConstructor
public class WorkoutInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "workout_day", nullable = false)
    private int workoutDay;

    @Column(name = "number_per_day", nullable = false)
    private int numberPerDay;

    @OneToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    public WorkoutInfo(long userId, Exercise exercise, int workoutDay, int numberPerDay) {
        this.userId = userId;
        this.exercise = exercise;
        this.workoutDay = workoutDay;
        this.numberPerDay = numberPerDay;
    }
}