package actions;

import input.Input;
import service.EmployeeService;

public interface EmployeeAction {

    String name();

    boolean execute(Input input, EmployeeService employeeService);
}
