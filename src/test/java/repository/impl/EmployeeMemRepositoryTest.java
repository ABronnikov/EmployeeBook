package repository.impl;

import java.lang.reflect.Field;
import java.util.List;
import lombok.SneakyThrows;
import mapper.EmployeeMapper;
import model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmployeeMemRepositoryTest {

    private final EmployeeMemRepository employeeMemRepository = new EmployeeMemRepository();

    @Test
    void saveTest() {
        Employee employee = employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        Assertions.assertTrue(employeeMemRepository.findById(employee.getId()).isPresent());
    }

    @Test
    void updateTest() {
        Employee employee = employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        boolean result = employeeMemRepository.update(employee.getId(),
            new Employee("Nikita", "Saratov"));
        Assertions.assertTrue(result);
        Employee checkEmployeeUpdated = employeeMemRepository.findById(employee.getId())
            .orElseThrow();
        Assertions.assertEquals("Nikita", checkEmployeeUpdated.getName());
        Assertions.assertEquals("Saratov", checkEmployeeUpdated.getCity());
    }

    @Test
    void updateTestNotSuccess() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        boolean result = employeeMemRepository.update(1l, new Employee("Nikita", "Saratov"));
        Assertions.assertFalse(result);
    }

    @Test
    void deleteTest() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        Employee employee = employeeMemRepository.save(new Employee("Diana", "Moscow"));
        boolean result = employeeMemRepository.delete(employee.getId());
        Assertions.assertTrue(result);
        Assertions.assertEquals(1, employeeMemRepository.findAll().size());
    }

    @Test
    void deleteTestNotSuccess() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        employeeMemRepository.save(new Employee("Diana", "Moscow"));
        boolean result = employeeMemRepository.delete(1l);
        Assertions.assertFalse(result);
    }

    @Test
    void findByNameTest() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        employeeMemRepository.save(new Employee("Diana", "Moscow"));
        employeeMemRepository.save(new Employee("Nikita", "Saratov"));
        List<Employee> employeeList = employeeMemRepository.findByName("Nikita");
        Assertions.assertEquals(2, employeeList.size());
        employeeList.forEach(employee -> Assertions.assertEquals("Nikita", employee.getName()));
    }

    @Test
    void findByNameTestIsEmpty() {
        employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        employeeMemRepository.save(new Employee("Diana", "Moscow"));
        employeeMemRepository.save(new Employee("Nikita", "Saratov"));
        List<Employee> employeeList = employeeMemRepository.findByName("Oleg");
        Assertions.assertTrue(employeeList.isEmpty());
    }

    @Test
    @SneakyThrows
    void findByCreatedDateIntervalTest() {
        EmployeeMapper employeeMapper = new EmployeeMapper();

        Employee employeeFirst = employeeMemRepository.save(new Employee("Nikita", "Moscow"));
        Class<? extends Employee> employeeFirstClass = employeeFirst.getClass();
        Field createdEmployeeFirstField = employeeFirstClass.getDeclaredField("created");
        createdEmployeeFirstField.setAccessible(true);
        createdEmployeeFirstField.set(employeeFirst, employeeMapper.parseDate("12-08-2022 12:00"));

        Employee employeeSecond = employeeMemRepository.save(new Employee("Diana", "Moscow"));
        Class<? extends Employee> employeeSecondClass = employeeSecond.getClass();
        Field createdEmployeeSecondField = employeeSecondClass.getDeclaredField("created");
        createdEmployeeSecondField.setAccessible(true);
        createdEmployeeSecondField.set(employeeSecond, employeeMapper.parseDate("14-08-2022 12:00"));

        Employee employeeThird = employeeMemRepository.save(new Employee("Nikita", "Saratov"));
        Class<? extends Employee> employeeThirdClass = employeeThird.getClass();
        Field createdEmployeeThirdField = employeeThirdClass.getDeclaredField("created");
        createdEmployeeThirdField.setAccessible(true);
        createdEmployeeThirdField.set(employeeThird, employeeMapper.parseDate("16-08-2022 12:00"));

        List<Employee> employeeList = employeeMemRepository.findByCreatedDateInterval(
            employeeMapper.parseDate("13-08-2022 12:00"),
            employeeMapper.parseDate("17-08-2022 12:00")
            );
        Assertions.assertEquals(2, employeeList.size());
        Assertions.assertEquals("Diana", employeeList.get(0).getName());
        Assertions.assertEquals("Nikita", employeeList.get(1).getName());
    }
}