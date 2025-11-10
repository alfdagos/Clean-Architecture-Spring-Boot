package it.alf.cleana.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.alf.cleana.security.JwtTokenProvider;
import it.alf.cleana.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public UserController(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<it.alf.cleana.dto.UserDto> register(@jakarta.validation.Valid @RequestBody it.alf.cleana.dto.RegisterUserDto body) {
        var user = userService.register(body.getEmail(), body.getPassword());
        var dto = new it.alf.cleana.dto.UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<java.util.Map<String, String>> login(@jakarta.validation.Valid @RequestBody it.alf.cleana.dto.LoginRequestDto body) {
        var maybe = userService.findByEmail(body.getEmail());
        if (maybe.isEmpty() || !userService.checkPassword(maybe.get(), body.getPassword())) {
            return ResponseEntity.status(401).build();
        }
    var token = tokenProvider.createToken(maybe.get().getId(), maybe.get().getEmail());
        return ResponseEntity.ok(java.util.Map.of("token", token));
    }
}
