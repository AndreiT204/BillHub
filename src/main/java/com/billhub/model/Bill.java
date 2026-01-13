package com.billhub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDate dueDate;

    @Column(name = "is_paid")
    private boolean paid;

    @Column(nullable = false)
    private String provider;

    @Column(name = "target_user", nullable = false)
    private String userUsername;

    public Bill(Double amount, LocalDate dueDate, String provider, String userUsername) {
        this.amount = amount;
        this.dueDate = dueDate;
        this.provider = provider;
        this.userUsername = userUsername;
        this.paid = false;
    }
}