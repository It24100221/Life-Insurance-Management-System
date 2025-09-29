package com.group08.demo.Service;

import com.group08.demo.Entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeValidationService {

    public boolean ValidateEmployeeInsertion(Employee employee) {
        if (employee.getEmployeeName()!=null || !employee.getEmployeeName().isEmpty()
                && employee.getEmployeeNIC()!=null || !employee.getEmployeeNIC().isEmpty()
                && employee.getEmployeeNIC().length()!= 12
                && employee.getEmployeePassword()!=null || !employee.getEmployeePassword().isEmpty()) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean ValidateEmployeeLogin(String NIC, String Password) {
        if (NIC!=null || !NIC.isEmpty()
                && NIC.length()!= 12
                && Password!=null || !Password.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean CheckAdminLogin(String NIC,String Password) {
        if (NIC.equals("000000000000") && Password.equals("Admin123")) {
            return true;
        }
        else{
            return false;
        }
    }
}
