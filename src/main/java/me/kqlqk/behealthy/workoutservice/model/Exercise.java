package me.kqlqk.behealthy.workoutservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kqlqk.behealthy.workoutservice.model.enums.MuscleGroup;

import javax.persistence.*;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "muscle_group", nullable = false)
    private MuscleGroup muscleGroup;
}
