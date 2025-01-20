package com.datastore.service.TafDatastoreService.DTO;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long flightId;
    private String status;
    private String message;
    private LocalDateTime createdAt;
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
