package com.datastore.service.TafDatastoreService.repositories;

import com.datastore.service.TafDatastoreService.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
