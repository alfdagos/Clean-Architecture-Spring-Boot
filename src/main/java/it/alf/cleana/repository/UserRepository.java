package it.alf.cleana.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.alf.cleana.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
