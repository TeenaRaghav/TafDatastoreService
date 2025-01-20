package com.datastore.service.TafDatastoreService.controllers;

import com.datastore.service.TafDatastoreService.entity.Bookings;
import com.datastore.service.TafDatastoreService.entity.Users;
import com.datastore.service.TafDatastoreService.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class TafDatastoreUserController {
    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    public TafDatastoreUserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @GetMapping()
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> users = usersRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        return usersRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users user){
        Users savedUser = usersRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users user){
        user.setUserId(id);
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        Users updatedUser = usersRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        usersRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
