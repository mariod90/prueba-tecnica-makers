package com.makers.gestionprestamos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lendings")
public class Lending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El monto es obligatorio")
    @Column(nullable = false)
    @Min(value = 1, message = "El monto debe ser al menos 1")
    private Double amount;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @NotNull(message = "El plazo es obligatorio")
    @Min(value = 1, message = "El plazo debe ser al menos 1 mes")
    @Column(nullable = false)
    private Integer termInMonths;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum State {
        PENDING,
        APPROVED,
        REJECTED
    }

}
