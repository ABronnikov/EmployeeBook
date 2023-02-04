package actions.impl;

import actions.EmployeeAction;
import dto.EmployeeDto;
import input.Input;
import java.util.Optional;
import service.EmployeeService;

public class FindByIdAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Find Employee By Id ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        long id = input.askLong("Enter id: ");
        Optional<EmployeeDto> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            System.out.println(employee.get());
        } else {
            System.out.println("Employee not found.");
        }
        return true;
    }
}

