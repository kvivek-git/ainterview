package com.ainterview.repository;

import com.ainterview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository
        extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findAll();

}
