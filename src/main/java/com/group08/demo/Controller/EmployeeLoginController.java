package com.group08.demo.Controller;

import com.group08.demo.Service.EmployeeValidationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeLoginController {

    private final EmployeeValidationService employeeValidationService;

    public EmployeeLoginController(EmployeeValidationService employeeValidationService) {
        this.employeeValidationService = employeeValidationService;
    }

    @GetMapping("/employee/login")
    public String showLoginForm() {
        return "EmployeeLogin"; // Maps to employee-login.html
    }

    @PostMapping("/employee/login")
    public String processLogin(@RequestParam String nic, @RequestParam String password, Model model) {
        // Check if the credentials are for admin
        if (employeeValidationService.CheckAdminLogin(nic, password)) {
            return "redirect:/admin/dashboard"; // Redirect to admin dashboard
        }
        // Check for regular employee login
        if (employeeValidationService.ValidateEmployeeLogin(nic, password)) {
            return "redirect:/employee/dashboard"; // Redirect to employee dashboard
        }
        // Login failed
        model.addAttribute("error", "Invalid NIC or password");
        return "employee-login"; // Return to login page with error
    }
}