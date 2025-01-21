package com.datastore.service.TafDatastoreService.controllers;

import com.datastore.service.TafDatastoreService.DTO.BookingDTO;
import com.datastore.service.TafDatastoreService.entity.Bookings;
import com.datastore.service.TafDatastoreService.entity.Flights;
import com.datastore.service.TafDatastoreService.entity.Users;
import com.datastore.service.TafDatastoreService.repositories.BookingRepository;
import com.datastore.service.TafDatastoreService.repositories.FlightRepository;
import com.datastore.service.TafDatastoreService.repositories.UsersRepository;
import com.datastore.service.TafDatastoreService.service.BookingsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class TafDatastoreBookingController {
    @Autowired
    private BookingsServiceImpl bookingsService;
    private final BookingRepository bookingRepository;
    private final UsersRepository usersRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public TafDatastoreBookingController(BookingRepository bookingRepository, UsersRepository usersRepository, FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.usersRepository = usersRepository;
        this.flightRepository = flightRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        return bookingRepository.findById(id)
                .map(booking -> ResponseEntity.ok(bookingsService.mapToDTO(booking)))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Bookings> bookings = bookingRepository.findAll();
        List<BookingDTO> bookingDTOs = bookings.stream()
                .map(this::mapToDTO)  // Map each entity to DTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookingDTOs);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Bookings>> getBookingsByUserId(@PathVariable Long userId) {
        List<Bookings> bookings = bookingsService.findBookingsByUserId(userId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        Bookings booking = mapToEntity(bookingDTO);
        Bookings savedBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(bookingsService.mapToDTO(savedBooking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        Bookings existingBooking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        // Map the incoming DTO to the existing entity
        existingBooking.setUser(usersRepository.findById(bookingDTO.getUserId()).orElseThrow());
        existingBooking.setFlight(flightRepository.findById(bookingDTO.getFlightId()).orElseThrow());
        existingBooking.setStatus(bookingDTO.getStatus());

        Bookings updatedBooking = bookingRepository.save(existingBooking);
        return ResponseEntity.ok(bookingsService.mapToDTO(updatedBooking));
    }

    //    @DeleteMapping("/{id}")
//    public ResponseEntity<BookingDTO> deleteBooking(@PathVariable Long id){
//        bookingRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        // Check if the booking exists
        Optional<Bookings> bookingOptional = bookingRepository.findById(id);

        if (bookingOptional.isEmpty()) {
            // Return a meaningful message if booking is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found with id: " + id);
        }

        Bookings booking = bookingOptional.get();

        // Check if the booking is already cancelled
        if ("Cancelled".equalsIgnoreCase(booking.getStatus())) {
            // Return a meaningful message if booking is already cancelled
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking with id: " + id + " is already cancelled.");
        }

        // Proceed with setting the status to 'Cancelled' instead of deleting if needed
        booking.setStatus("Cancelled");
        bookingRepository.save(booking);

        // Return a success message
        return ResponseEntity.ok("Booking with id: " + id + " has been cancelled successfully.");
    }

    // Method to map Booking entity to BookingDTO
    private BookingDTO mapToDTO(Bookings booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getUserId());  // Map userId from User object
        dto.setFlightId(booking.getFlight().getFlightId());  // Map flightId from Flight object
        dto.setStatus(booking.getStatus());
        dto.setMessage(booking.getMessage());
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        return dto;
    }

    // Method to map BookingDTO to Booking entity
    private Bookings mapToEntity(BookingDTO bookingDTO) {
        Bookings booking = new Bookings();
        Users user = usersRepository.findById(bookingDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Flights flight = flightRepository.findById(bookingDTO.getFlightId()).orElseThrow(() -> new RuntimeException("Flight not found"));
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setStatus(bookingDTO.getStatus());
        return booking;
    }

}

