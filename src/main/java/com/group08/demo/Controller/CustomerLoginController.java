package com.group08.demo.Controller;

import com.group08.demo.Service.CustomerValidationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerLoginController {

    private final CustomerValidationService customerValidationService;

    public CustomerLoginController(CustomerValidationService customerValidationService) {
        this.customerValidationService = customerValidationService;
    }

    @GetMapping("/customer/login")
    public String showLoginForm() {
        return "CustomerLogin"; // Maps to customer-login.html
    }

    @PostMapping("/customer/login")
    public String processLogin(@RequestParam String nic, @RequestParam String password, Model model) {
        if (customerValidationService.ValidateCustomerLogin(nic, password)) {
            return "redirect:/customer/dashboard"; // Redirect to customer dashboard
        }
        model.addAttribute("error", "Invalid NIC or password");
        return "CustomerLogin"; // Return to login page with error
    }
}