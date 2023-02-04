package actions.impl;

import actions.EmployeeAction;
import input.Input;
import service.EmployeeService;

public class DeleteAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Delete Employee ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        long id = input.askLong("Enter id: ");
        if (employeeService.delete(id)) {
            System.out.println("Successfully");
        } else {
            System.out.println("Unsuccessfully");
        }
        return true;
    }
}
