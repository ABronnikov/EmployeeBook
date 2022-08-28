package service;

import model.Employee;
import repository.EmployeeRepository;
import util.Constants;

public class EmployeeService {

    private EmployeeRepository employeeRepository = new EmployeeRepository();

    public boolean save(Employee employee) {
        Employee result = employeeRepository.save(employee);
        return result.getId() != Constants.INCORRECT_ID;
    }

    public boolean delete(long id) {
        return employeeRepository.delete(id);
    }

    public Employee[] findAll() {
        return employeeRepository.findAll();
    }

}
