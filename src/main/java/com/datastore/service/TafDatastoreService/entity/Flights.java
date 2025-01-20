package com.datastore.service.TafDatastoreService.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @Column(name = "flight_num",nullable = false, unique = true ,length = 20)
	private String flightNumber;

    @Column(name = "departure_city",nullable = false ,length = 30)
    private String departure;

    @Column(name = "arrival_city",nullable = false ,length = 30)
    private String arrival;

    private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
    private BigDecimal price;
    private int availableSeats;

    @Column(name = "created_at",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
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
