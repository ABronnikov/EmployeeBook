package actions.impl;

import actions.EmployeeAction;
import dto.EmployeeDto;
import input.Input;
import model.Employee;
import service.EmployeeService;

public class UpdateAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Update Employee ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        long id = input.askLong("Enter id: ");
        String name = input.askStr("Enter name: ");
        String city = input.askStr("Enter city: ");
        if (employeeService.update(id, new EmployeeDto(name, city))) {
            System.out.println("Successfully.");
        } else {
            System.out.println("Unsuccessfully.");
        }
        return true;
    }
}
