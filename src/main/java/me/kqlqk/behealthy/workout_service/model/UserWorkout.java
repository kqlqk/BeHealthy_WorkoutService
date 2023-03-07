package me.kqlqk.behealthy.workout_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_workout", schema = "public", catalog = "workout_service_db")
@Data
@NoArgsConstructor
public class UserWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private long id;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "exercise_rep", nullable = false)
    private int rep;

    @Column(name = "exercise_set", nullable = false)
    private int set;

    @Column(name = "number_per_day", nullable = false)
    private int numberPerDay;

    @Column(name = "workout_day", nullable = false)
    private int day;

    @Column(name = "user_id", nullable = false)
    private long userId;
}
