package it.alf.cleana.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import it.alf.cleana.domain.User;
import it.alf.cleana.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repo;
    private final it.alf.cleana.security.PBKDF2PasswordHasher passwordHasher;

    public UserService(UserRepository repo, it.alf.cleana.security.PBKDF2PasswordHasher passwordHasher) {
        this.repo = repo;
        this.passwordHasher = passwordHasher;
    }

    public User register(String email, String password) {
        if (repo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        var user = new User(email, passwordHasher.hash(password));
        return repo.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordHasher.verify(rawPassword, user.getPasswordHash());
    }
}
