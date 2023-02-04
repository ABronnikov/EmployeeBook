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

class FindAllActionTest {

    private final FindAllAction findAllAction = new FindAllAction();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeMemRepository employeeMemRepository = new EmployeeMemRepository();
    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);


    @Test
    void whenFindAll() {
        Input input = Mockito.mock(ConsoleInput.class);
        Employee employee = new Employee("Nikita", "Moscow");
        employee.setCreated(LocalDateTime.now());
        employeeMemRepository.save(employee);
        String date = employee.getCreated().format(dateTimeFormatter);
        PrintStream out = System.out;
        System.setOut(new PrintStream(output));
        findAllAction.execute(input, employeeService);
        Assertions.assertEquals("Employee info. Name - Nikita, City - Moscow, Created - " + date, output.toString().replaceAll("\n",""));
        System.setOut(out);
    }

}