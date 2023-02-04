package actions.impl;

import static util.Constants.PATTERN_DATE;

import input.ConsoleInput;
import input.Input;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mapper.EmployeeMapper;
import model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.impl.EmployeeMemRepository;
import service.EmployeeService;

public class SortedByOrderActionTest {

    private final SortedByOrderAction sortedByOrderAction = new SortedByOrderAction();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeMemRepository employeeMemRepository = new EmployeeMemRepository();
    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);


    @Test
    void whenSortedByOrderActionSortAsc() {
        Input input = Mockito.mock(ConsoleInput.class);
        Employee employee = new Employee("Nikita", "Moscow");
        employee.setCreated(LocalDateTime.now());
        employeeMemRepository.save(employee);
        Employee employee1 = new Employee("Vera", "Saratov");
        employee1.setCreated(LocalDateTime.now());
        employeeMemRepository.save(employee1);
        Mockito.when(input.askStr("Enter order type (ASC/DESC): ")).thenReturn("ASC");
        String date = employee.getCreated().format(dateTimeFormatter);
        String date1 = employee1.getCreated().format(dateTimeFormatter);
        PrintStream out = System.out;
        System.setOut(new PrintStream(output));
        sortedByOrderAction.execute(input, employeeService);
        Assertions.assertEquals("Employee info. Name - Nikita, City - Moscow, Created - " + date + "Employee info. Name - Vera, City - Saratov, Created - " + date1, output.toString().replaceAll("\n",""));
        System.setOut(out);
    }

    @Test
    void whenSortedByOrderActionSortDesc() {
        Input input = Mockito.mock(ConsoleInput.class);
        Employee employee = new Employee("Nikita", "Moscow");
        employee.setCreated(LocalDateTime.now());
        employeeMemRepository.save(employee);
        Employee employee1 = new Employee("Vera", "Saratov");
        employee1.setCreated(LocalDateTime.now());
        employeeMemRepository.save(employee1);
        Mockito.when(input.askStr("Enter order type (ASC/DESC): ")).thenReturn("DESC");
        String date = employee.getCreated().format(dateTimeFormatter);
        String date1 = employee1.getCreated().format(dateTimeFormatter);
        PrintStream out = System.out;
        System.setOut(new PrintStream(output));
        sortedByOrderAction.execute(input, employeeService);
        Assertions.assertEquals("Employee info. Name - Vera, City - Saratov, Created - " + date1 + "Employee info. Name - Nikita, City - Moscow, Created - " + date, output.toString().replaceAll("\n",""));
        System.setOut(out);
    }
}
