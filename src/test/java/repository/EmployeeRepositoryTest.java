package repository;

import model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmployeeRepositoryTest {

    @Test
    void whenSave() {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        Employee employee = new Employee("Aleksey", "Russia");
        employeeRepository.save(employee);
        Assertions.assertNotNull(employeeRepository.findAll()[0]);
    }

    @Test
    void whenSaveWithTheHelpId() {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        Employee employee = new Employee("Aleksey", "Russia");
        employeeRepository.save(employee);
        Employee result = employeeRepository.findById(employee.getId());
        Assertions.assertNotNull(result);
    }
}