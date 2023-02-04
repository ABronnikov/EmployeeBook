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

public class UpdateActionTest {

    private final UpdateAction updateAction = new UpdateAction();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeMemRepository employeeMemRepository = new EmployeeMemRepository();
    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);

    @Test
    void whenUpdateAction() {
        Input input = Mockito.mock(ConsoleInput.class);
        Employee employee = new Employee("Nikita", "Moscow");
        employee.setCreated(LocalDateTime.now());
        employeeMemRepository.save(employee);
        employee.setId(1L);
        Mockito.when(input.askLong("Enter id: ")).thenReturn(1L);
        Mockito.when(input.askStr("Enter name: ")).thenReturn("Vera");
        Mockito.when(input.askStr("Enter city: ")).thenReturn("Saratov");
        PrintStream out = System.out;
        System.setOut(new PrintStream(output));
        updateAction.execute(input, employeeService);
        Assertions.assertEquals("Successfully.", output.toString().replaceAll("\n",""));
        System.setOut(out);
    }
}
