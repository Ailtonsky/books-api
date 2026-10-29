package com.turing.books_api.service;

import com.turing.books_api.dto.auth.LoginRequest;
import com.turing.books_api.dto.auth.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
