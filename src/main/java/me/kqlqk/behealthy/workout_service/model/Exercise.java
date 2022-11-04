package me.kqlqk.behealthy.workout_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kqlqk.behealthy.workout_service.enums.MuscleGroup;

import javax.persistence.*;

@Entity
@Table(name = "exercises")
@Getter
@Setter
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private int id;

    @Column(name = "name", unique = true, nullable = false, insertable = false, updatable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "muscle_group", nullable = false)
    private MuscleGroup muscleGroup;
}
