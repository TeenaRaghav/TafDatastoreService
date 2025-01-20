package com.datastore.service.TafDatastoreService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flights flight;

    private String status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void createdPrePersist() {
        this.createdAt = LocalDateTime.now();// Set created_at when the entity is first persisted
        this.updatedAt = LocalDateTime.now();// Set updated_at as well to the same value initially
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();  // Update updated_at whenever the entity is updated
    }


}
