package com.datastore.service.TafDatastoreService.controllers;

import com.datastore.service.TafDatastoreService.entity.Bookings;
import com.datastore.service.TafDatastoreService.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class TafDatastoreBookingController {
    private final BookingRepository bookingRepository;

    @Autowired
    public TafDatastoreBookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookings> getBookingById(@PathVariable Long id){
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Bookings>> getAllBookings(){
        List<Bookings> bookings = bookingRepository.findAll();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<Bookings> createBooking(@RequestBody Bookings bookings){
        Bookings savedBooking = bookingRepository.save(bookings);
        return ResponseEntity.ok(savedBooking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookings> updateBooking(@PathVariable Long id, @RequestBody Bookings bookings){
        bookings.setId(id);
        Bookings updatedBooking = bookingRepository.save(bookings);
        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        bookingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
