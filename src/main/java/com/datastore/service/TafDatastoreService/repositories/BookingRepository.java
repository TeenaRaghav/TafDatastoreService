package com.datastore.service.TafDatastoreService.repositories;

import com.datastore.service.TafDatastoreService.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Bookings, Long> {

    List<Bookings> findByUser_UserId(Long userId);
}
