package com.datastore.service.TafDatastoreService.repositories;

import com.datastore.service.TafDatastoreService.entity.Flights;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flights, Long> {
}
