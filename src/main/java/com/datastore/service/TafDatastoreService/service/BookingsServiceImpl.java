package com.datastore.service.TafDatastoreService.service;

import com.datastore.service.TafDatastoreService.DTO.BookingDTO;
import com.datastore.service.TafDatastoreService.entity.Bookings;
import com.datastore.service.TafDatastoreService.entity.Flights;
import com.datastore.service.TafDatastoreService.entity.Users;
import com.datastore.service.TafDatastoreService.repositories.BookingRepository;
import com.datastore.service.TafDatastoreService.repositories.FlightRepository;
import com.datastore.service.TafDatastoreService.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingsServiceImpl {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FlightRepository flightsRepository;

    @Autowired
    private BookingRepository bookingsRepository;

    // Method to create a booking
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Fetch user using userId
        Users user = usersRepository.findById(bookingDTO.getUserId()).orElse(null);
        if (user == null) {
            return createBookingResponse(null, "Failed", "User not found", null, null);
        }

        // Fetch flight using flightId
        Flights flight = flightsRepository.findById(bookingDTO.getFlightId()).orElse(null);
        if (flight == null) {
            return createBookingResponse(null, "Failed", "Flight not found", null, null);
        }

        // Create a new Booking entity and associate user and flight
        Bookings booking = new Bookings();
        booking.setUser(user);  // Set the User object
        booking.setFlight(flight);  // Set the Flight object
        booking.setStatus("Confirmed");  // Set booking status

        // Save the booking entity to the database
        bookingsRepository.save(booking);

        // Convert the entity back to DTO for the response
        return mapToDTO(booking);
    }

    // Method to map Booking entity to BookingDTO
    public BookingDTO mapToDTO(Bookings booking) {
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


    // Utility method to create a response DTO
    private BookingDTO createBookingResponse(Long id, String status, String message, Long userId, Long flightId) {
        BookingDTO responseDTO = new BookingDTO();
        responseDTO.setId(id);
        responseDTO.setStatus(status);
        responseDTO.setMessage(message);
        responseDTO.setUserId(userId);
        responseDTO.setFlightId(flightId);
        return responseDTO;
    }

    public List<Bookings> findBookingsByUserId(Long userId) {
        return bookingsRepository.findByUser_UserId(userId);
    }
}

