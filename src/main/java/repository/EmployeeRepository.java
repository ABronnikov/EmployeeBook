package repository;

import java.util.Objects;
import model.Employee;
import util.Constants;

public class EmployeeRepository {

    private Employee[] employees = new Employee[10];
    private int index = 0;

    public Employee save(Employee employee) {
        if (employees.length - 1 == index) {
            System.out.println("Book size is max.");
            employee.setId(Constants.INCORRECT_ID);
        } else {
            employees[index] = employee;
            index++;
        }
        return employee;
    }

    public boolean update(Long id, Employee employee) {
        for (int i = 0; i < employees.length; i++) {

            long employeeId = employees[i].getId();
            if (employeeId == id) {
                employee.setId(employeeId);
                employees[i] = employee;
                return true;
            }
        }
        return false;
    }

    public boolean delete(Long id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i].getId() == id) {
                employees[i] = null;
                index--;
            }
        }

        for (int i = 0; i < employees.length - 1; i++) {
            if (Objects.isNull(employees[i])) {
                employees[i] = employees[i + 1];
                employees[i + 1] = null;
            }
        }
        return true;
    }

    public boolean delete(String country) {
        return true;
    }

    public Employee findById(Long id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public Employee[] findAllByName(String name) {
        return null;
    }

    public Employee[] findAll() {
        int count = 0;

        for (Employee employee : employees) {
            if (Objects.nonNull(employee)) {
                count++;
            }
        }

        Employee[] result = new Employee[count];

        for (Employee employee : employees) {
            if (Objects.nonNull(employee)) {
                count--;
                result[count] = employee;
            }
        }

        return result;
    }
}
