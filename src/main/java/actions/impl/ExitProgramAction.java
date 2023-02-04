package actions.impl;

import actions.EmployeeAction;
import input.Input;
import service.EmployeeService;

public class ExitProgramAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Exit program ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        employeeService.closeAllSession();
        return false;
    }
}
