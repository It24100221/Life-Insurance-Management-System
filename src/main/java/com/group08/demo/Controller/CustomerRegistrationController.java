package com.group08.demo.Controller;

import com.group08.demo.Entity.Customer;
import com.group08.demo.Service.CustomerValidationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerRegistrationController {

    private final CustomerValidationService customerValidationService;

    public CustomerRegistrationController(CustomerValidationService customerValidationService) {
        this.customerValidationService = customerValidationService;
    }

    @GetMapping("/customer/register")
    public String showRegistrationForm() {
        return "CustomerRegistration"; // Maps to customer-registration.html
    }

    @PostMapping("/customer/register")
    public String processRegistration(
            @RequestParam String name,
            @RequestParam String nic,
            @RequestParam String address,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {
        Customer customer = new Customer(name, nic, address, phone, email, password);
        if (customerValidationService.ValidateCustomerRegistration(customer)) {
            // save customer to database here
            return "redirect:/customer/login"; // Redirect to login page after successful registration
        }
        model.addAttribute("error", "Invalid registration details. Please check your inputs.");
        return "CustomerRegistration"; // Return to registration page
    }
}
