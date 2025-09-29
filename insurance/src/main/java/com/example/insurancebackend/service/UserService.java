// UserService.java (src/main/java/com/example/insurancebackend/service/UserService.java)
package com.example.insurancebackend.service;

import com.example.insurancebackend.entity.User;
import com.example.insurancebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> getUsers(int page, int size, String sortBy, String sortDir, String query, String role) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return userRepository.searchUsers(query.isEmpty() ? null : query, role.isEmpty() ? null : role, pageable);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUserFullname(user.getUserFullname());
        existingUser.setUserEmail(user.getUserEmail());
        existingUser.setUserPhone(user.getUserPhone());
        existingUser.setUserNic(user.getUserNic());
        existingUser.setUserRole(user.getUserRole());
        existingUser.setUserIsActive(user.getUserIsActive());
        if (user.getUserPassword() != null && !user.getUserPassword().isEmpty()) {
            existingUser.setUserPassword(user.getUserPassword());
        }
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void resetData() {
        userRepository.deleteAll();
        List<User> sampleUsers = List.of(
                createSampleUser("John Doe", "john@example.com", "123-456-7890", "NIC12345", "password123", "USER"),
                createSampleUser("Jane Smith", "jane@example.com", "987-654-3210", "NIC67890", "password123", "AGENT")
        );
        userRepository.saveAll(sampleUsers);
    }

    private User createSampleUser(String fullname, String email, String phone, String nic, String password, String role) {
        User user = new User();
        user.setUserFullname(fullname);
        user.setUserEmail(email);
        user.setUserPhone(phone);
        user.setUserNic(nic);
        user.setUserPassword(password);
        user.setUserRole(role);
        user.setUserIsActive(true);
        return user;
    }
}