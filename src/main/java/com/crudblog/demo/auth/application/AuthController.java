package com.crudblog.demo.auth.application;

import com.crudblog.demo.auth.domain.entity.Token;
import com.crudblog.demo.auth.domain.entity.User;
import com.crudblog.demo.auth.domain.service.JwtTokenService;
import com.crudblog.demo.auth.domain.service.UserDetailsServiceImpl;
import com.crudblog.demo.auth.domain.service.UserLoginService;
import com.crudblog.demo.auth.domain.service.UserRegistrationService;
import com.crudblog.demo.auth.infrastructure.repository.exception.RegistrationErrorException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserLoginService userLoginService;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private UserRegistrationService userRegistrationService;

    public AuthController(
            UserLoginService userLoginService,
            JwtTokenService jwtTokenService,
            UserDetailsServiceImpl userDetailsService,
            UserRegistrationService userRegistrationService
    ) {
        this.userLoginService = userLoginService;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody User userBody) throws Exception {
        try {
            userLoginService.login(userBody);
            Token token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getUsername()));

            ResponseCookie jwtCookie = ResponseCookie.from("token", token.getToken())
                    .httpOnly(true)   // Marquer le cookie comme HttpOnly pour la sécurité
                    //.secure(true)     // Marquer le cookie comme sécurisé (transmis uniquement via HTTPS)
                    .path("/")        // Le cookie est accessible pour l'ensemble du domaine
                    .maxAge(24 * 60 * 60) // Définir la durée de vie du cookie (exemple : 24 heures)
                    .sameSite("Strict") // Politique SameSite pour le cookie
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .build();
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userBody) throws RegistrationErrorException {
        try {
            return ResponseEntity.status(201).body(userRegistrationService.UserRegistration(userBody));
        } catch (Exception e) {
            throw new RegistrationErrorException(e.getMessage());
        }
    }
}
