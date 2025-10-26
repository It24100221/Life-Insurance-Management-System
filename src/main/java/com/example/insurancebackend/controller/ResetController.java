// ResetController.java (src/main/java/com/example/insurancebackend/controller/ResetController.java)
package com.example.insurancebackend.controller;

import com.example.insurancebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResetController {

    @Autowired
    private UserService userService;

    @PostMapping("/reset")
    public ResponseEntity<Void> resetData() {
        userService.resetData();
        return ResponseEntity.ok().build();
    }
}