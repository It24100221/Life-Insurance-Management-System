package com.group08.demo.Service;

import com.group08.demo.Entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerValidationService {


    public boolean ValidateCustomerRegistration(Customer customer) {
        if (customer.getCusName() != null
                && customer.getCusNIC() != null
                && customer.getCusNIC().length() == 12
                && customer.getCusAddress() != null
                && customer.getCusPhone() != null
                && customer.getCusPhone().length() == 10
                && customer.getCusPhone().startsWith("0")
                && customer.getCusEmail() != null
                && customer.getCusEmail().contains("@")
                && customer.getCusPassword() != null) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean ValidateCustomerLogin(String NIC, String password) {
        if (NIC != null && password != null) {
            return true;
        }
        else{
            return false;
        }
    }
}
