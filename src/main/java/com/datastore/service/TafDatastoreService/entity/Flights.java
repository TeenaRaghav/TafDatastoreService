package com.datastore.service.TafDatastoreService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="flight_num", unique = true ,length = 20)
	private String flight_number;

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
    private Date created_at;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

}
