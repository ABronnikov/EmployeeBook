package actions.impl;

import input.ConsoleInput;
import input.Input;
import java.time.LocalDateTime;
import mapper.EmployeeMapper;
import model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import repository.impl.EmployeeMemRepository;
import service.EmployeeService;

class CreateActionTest {

    private final CreateAction createAction = new CreateAction();
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeMemRepository employeeMemRepository = Mockito.mock(EmployeeMemRepository.class);
    private final EmployeeService employeeService = new EmployeeService(employeeMemRepository, employeeMapper);

    @Test
    void whenSave() {
        Input input = Mockito.mock(ConsoleInput.class);
        Mockito.when(input.askStr("Enter name: ")).thenReturn("Nikita");
        Mockito.when(input.askStr("Enter city: ")).thenReturn("Moscow");
        Employee employee = new Employee("Nikita", "Moscow");
        employee.setId(1L);
        employee.setCreated(LocalDateTime.now());
        Mockito.when(employeeMemRepository.save(ArgumentMatchers.any(Employee.class))).thenReturn(employee);
        boolean saveSuccess = createAction.execute(input, employeeService);
        Assertions.assertTrue(saveSuccess);
    }

}