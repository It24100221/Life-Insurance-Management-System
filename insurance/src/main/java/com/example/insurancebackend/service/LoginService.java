package com.example.insurancebackend.service;

import com.example.insurancebackend.dto.AuthResponse;
import com.example.insurancebackend.dto.LoginRequest;
import com.example.insurancebackend.dto.RegisterRequest;
import com.example.insurancebackend.entity.Login;
import com.example.insurancebackend.repository.LoginRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public AuthResponse register(RegisterRequest request) {
        if (loginRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, "Email already exists.", null);
        }
        if (loginRepository.existsByNic(request.getNic())) {
            return new AuthResponse(null, "NIC already exists.", null);
        }

        Login login = new Login();
        login.setFullName(request.getFullName());
        login.setEmail(request.getEmail());
        login.setPhone(request.getPhone());
        login.setNic(request.getNic());
        login.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        login.setRole("user");
        loginRepository.save(login);
        return new AuthResponse(null, "Registration successful. Please log in.", null);
    }

    public AuthResponse login(LoginRequest request) {
        Login login = loginRepository.findByEmail(request.getEmail()).orElse(null);
        if (login == null || !BCrypt.checkpw(request.getPassword(), login.getPassword())) {
            return new AuthResponse(null, "Invalid email or password.", null);
        }
        if (!login.isActive()) {
            return new AuthResponse(null, "Account is not active.", null);
        }
        AuthResponse resp = new AuthResponse(null, "Login successful.", login.getRole());
        resp.setUserId(login.getUserId());
        resp.setFullName(login.getFullName());
        resp.setEmail(login.getEmail());
        resp.setNic(login.getNic());
        resp.setPhone(login.getPhone());
        return resp;
    }
}