package com.datastore.service.TafDatastoreService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true, length = 20)
    private String username;
    @Column(name = "email", length = 30)
    private String email;
    @Column(name = "phone_number", length = 20 )
    private String phone;
    @Column(name = "created_at",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated_at;

    @PrePersist
    public void createdPrePersist() {
        this.created_at = LocalDateTime.now();// Set created_at when the entity is first persisted
        this.updated_at = LocalDateTime.now();// Set updated_at as well to the same value initially
    }
    @PreUpdate
    public void preUpdate() {
        this.updated_at = LocalDateTime.now();  // Update updated_at whenever the entity is updated
    }

}
