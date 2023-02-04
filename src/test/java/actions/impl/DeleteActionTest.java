package actions.impl;

import static org.junit.jupiter.api.Assertions.*;

import input.ConsoleInput;
import input.Input;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import mapper.EmployeeMapper;
import model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import repository.impl.EmployeeMemRepository;
import service.EmployeeService;
import sort.Ordered;

class DeleteActionTest {

    private final DeleteAction deleteAction = new DeleteAction();

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeMemRepository employeeMemRepository = new EmployeeMemRepository();
    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);

    @Test
    void whenDelete() {
        Input input = Mockito.mock(ConsoleInput.class);
        Employee employeeFirst = new Employee("Nikita", "Moscow");
        employeeFirst.setCreated(LocalDateTime.now());
        employeeMemRepository.save(employeeFirst);
        employeeFirst.setId(1L);
        Mockito.when(input.askLong("Enter id: ")).thenReturn(1L);
        PrintStream out = System.out;
        System.setOut(new PrintStream(output));
        deleteAction.execute(input, employeeService);
        Assertions.assertEquals("Successfully", output.toString().replaceAll("\n",""));
        System.setOut(out);
    }



}