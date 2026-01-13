package com.billhub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private boolean isCritical = true;

    private String provider;

    public Notice(String message, boolean isCritical, String provider) {
        this.message = message;
        this.isCritical = isCritical;
        this.provider = provider;
    }
}