package com.turing.books_api.service.impl;

import com.turing.books_api.dto.auth.LoginRequest;
import com.turing.books_api.dto.auth.LoginResponse;
import com.turing.books_api.dto.user.UserResponse;
import com.turing.books_api.entity.User;
import com.turing.books_api.exception.ResourceNotFoundException;
import com.turing.books_api.repository.UserRepository;
import com.turing.books_api.security.JwtService;
import com.turing.books_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // Valida usuario y contraseña con BCrypt; si fallan lanza BadCredentialsException (401)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new LoginResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds(),
                UserResponse.from(user)
        );
    }
}
