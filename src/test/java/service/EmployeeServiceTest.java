package service;

import dto.EmployeeDto;
import java.util.List;
import mapper.EmployeeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.impl.EmployeeMemRepository;
import sort.Ordered;

class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService(new EmployeeMemRepository(), new EmployeeMapper());

    @Test
    void whenSortingDefault() {
        EmployeeDto first = new EmployeeDto("Nikita", "Moscow");
        EmployeeDto second = new EmployeeDto("Arina", "Moscow");
        EmployeeDto third = new EmployeeDto("Boris", "Moscow");
        employeeService.save(first);
        employeeService.save(second);
        employeeService.save(third);
        List<EmployeeDto> result = employeeService.findAll(Ordered.DEFAULT);
        Assertions.assertEquals("Nikita", result.get(0).getName());
        Assertions.assertEquals("Arina", result.get(1).getName());
        Assertions.assertEquals("Boris", result.get(2).getName());
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void whenSortingAsc() {
        EmployeeDto first = new EmployeeDto("Nikita", "Moscow");
        EmployeeDto second = new EmployeeDto("Arina", "Moscow");
        EmployeeDto third = new EmployeeDto("Boris", "Moscow");
        employeeService.save(first);
        employeeService.save(second);
        employeeService.save(third);
        List<EmployeeDto> result = employeeService.findAll(Ordered.ASC);
        Assertions.assertEquals("Arina", result.get(0).getName());
        Assertions.assertEquals("Boris", result.get(1).getName());
        Assertions.assertEquals("Nikita", result.get(2).getName());
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void whenSortingDesc() {
        EmployeeDto first = new EmployeeDto("Nikita", "Moscow");
        EmployeeDto second = new EmployeeDto("Arina", "Moscow");
        EmployeeDto third = new EmployeeDto("Boris", "Moscow");
        employeeService.save(first);
        employeeService.save(second);
        employeeService.save(third);
        List<EmployeeDto> result = employeeService.findAll(Ordered.DESC);
        Assertions.assertEquals("Nikita", result.get(0).getName());
        Assertions.assertEquals("Boris", result.get(1).getName());
        Assertions.assertEquals("Arina", result.get(2).getName());
        Assertions.assertEquals(3, result.size());
    }

}