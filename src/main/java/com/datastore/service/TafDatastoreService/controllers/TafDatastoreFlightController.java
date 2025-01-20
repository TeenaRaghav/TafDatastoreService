package com.datastore.service.TafDatastoreService.controllers;

import com.datastore.service.TafDatastoreService.entity.Flights;
import com.datastore.service.TafDatastoreService.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class TafDatastoreFlightController {
    @Autowired
    private final FlightRepository flightRepository;

    @Autowired
    public TafDatastoreFlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Flights> getFlightById(@PathVariable Long id){
        return flightRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Flights>> getAllFlights() {
        List<Flights> flights = flightRepository.findAll();
        return ResponseEntity.ok(flights);
    }
    @PostMapping
    public ResponseEntity<Flights> createFlight(@RequestBody Flights flights){
        Flights savedFlight = flightRepository.save(flights);
        return ResponseEntity.ok(savedFlight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flights> updateFlight(@PathVariable Long id, @RequestBody Flights flights){
        flights.setFlightId(id);
        Flights updatedFlight = flightRepository.save(flights);
        return ResponseEntity.ok(updatedFlight);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
