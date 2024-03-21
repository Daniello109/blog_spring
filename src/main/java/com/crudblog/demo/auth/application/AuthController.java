package com.crudblog.demo.auth.application;

import com.crudblog.demo.auth.domain.entity.Token;
import com.crudblog.demo.auth.domain.entity.User;
import com.crudblog.demo.auth.domain.service.*;
import com.crudblog.demo.auth.infrastructure.exception.PasswordForgottenErrorException;
import com.crudblog.demo.auth.infrastructure.exception.RegistrationErrorException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final UserLoginService userLoginService;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private UserRegistrationService userRegistrationService;
    private PasswordForgottenService passwordForgottenService;

    public AuthController(
            UserLoginService userLoginService,
            JwtTokenService jwtTokenService,
            UserDetailsServiceImpl userDetailsService,
            UserRegistrationService userRegistrationService,
            PasswordForgottenService passwordForgottenService
    ) {
        this.userLoginService = userLoginService;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
        this.passwordForgottenService = passwordForgottenService;
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

    @PostMapping("/password-forgotten/{email}")
    public ResponseEntity<?> passwordForgotten(@PathVariable String email) throws RegistrationErrorException {
        try {
            return ResponseEntity.status(201).body(passwordForgottenService.tokenGenerator(email));
        } catch (Exception e) {
            //throw new PasswordForgottenErrorException(e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/new-password/{token}")
    public ResponseEntity<?> newPassword(@PathVariable String token, @RequestBody User userBody) {
        try {
            passwordForgottenService.checkTokenValidityAndCreateNewPassword(token, userBody);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
