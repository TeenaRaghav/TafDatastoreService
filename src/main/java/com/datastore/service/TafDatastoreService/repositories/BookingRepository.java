package com.datastore.service.TafDatastoreService.repositories;

import com.datastore.service.TafDatastoreService.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Bookings, Long> {
}
