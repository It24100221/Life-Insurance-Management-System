package com.example.insurancebackend.service;

import com.example.insurancebackend.dto.AuthResponse;
import com.example.insurancebackend.dto.LoginRequest;
import com.example.insurancebackend.dto.RegisterRequest;
import com.example.insurancebackend.entity.User;
import com.example.insurancebackend.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register(RegisterRequest request) {
        if (request.getPassword() == null || request.getPassword().length() < 4) {
            return new AuthResponse(null, "Password must be at least 4 characters.", null);
        }
        if (request.getConfirmPassword() == null || !request.getPassword().equals(request.getConfirmPassword())) {
            return new AuthResponse(null, "Passwords do not match.", null);
        }
        if (userRepository.findByUserEmailIgnoreCase(request.getEmail()) != null) {
            return new AuthResponse(null, "Email already exists.", null);
        }
        if (request.getNic() != null && userRepository.findByUserNic(request.getNic()) != null) {
            return new AuthResponse(null, "NIC already exists.", null);
        }

        User user = new User();
        user.setUserFullname(request.getFullName());
        user.setUserEmail(request.getEmail());
        user.setUserPhone(request.getPhone());
        user.setUserNic(request.getNic());
        user.setUserPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        String role = (request.getRole() == null || request.getRole().isBlank()) ? "user" : request.getRole();
        user.setUserRole(role);
        user.setUserIsActive(true);
        userRepository.save(user);
        return new AuthResponse(null, "Registration successful. Please log in.", role);
    }

    public AuthResponse login(LoginRequest request) {
        // Case-insensitive email match
        User user = userRepository.findByUserEmailIgnoreCase(request.getEmail());
        if (user == null) {
            return new AuthResponse(null, "Invalid email or password.", null);
        }

        boolean passwordMatches;
        String inputPassword = request.getPassword();
        String storedPassword = user.getUserPassword();

        // Try BCrypt first
        try {
            passwordMatches = storedPassword != null && !storedPassword.isEmpty() && BCrypt.checkpw(inputPassword, storedPassword);
        } catch (Exception ignored) {
            passwordMatches = false;
        }

        // Fallback: if stored password seems plaintext and equals input, accept and upgrade hash
        if (!passwordMatches && storedPassword != null && storedPassword.equals(inputPassword)) {
            passwordMatches = true;
            // upgrade to BCrypt
            user.setUserPassword(BCrypt.hashpw(inputPassword, BCrypt.gensalt()));
            userRepository.save(user);
        }

        if (!passwordMatches) {
            return new AuthResponse(null, "Invalid email or password.", null);
        }
        if (!user.getUserIsActive()) {
            return new AuthResponse(null, "Account is not active.", null);
        }

        // Optional role validation if UI sends a role
        if (request.getRole() != null && !request.getRole().isBlank()) {
            String requestedRole = request.getRole().trim().toLowerCase();
            String actualRole = user.getUserRole() == null ? "" : user.getUserRole().trim().toLowerCase();
            if (!requestedRole.equals(actualRole)) {
                return new AuthResponse(null, "Selected role does not match your account.", null);
            }
        }

        AuthResponse resp = new AuthResponse(null, "Login successful.", user.getUserRole());
        resp.setUserId(user.getUserId());
        resp.setFullName(user.getUserFullname());
        resp.setEmail(user.getUserEmail());
        resp.setNic(user.getUserNic());
        resp.setPhone(user.getUserPhone());
        return resp;
    }
}