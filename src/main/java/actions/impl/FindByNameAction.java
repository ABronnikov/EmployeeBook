package actions.impl;

import actions.EmployeeAction;
import dto.EmployeeDto;
import input.Input;
import java.util.List;
import service.EmployeeService;
import util.OutputUtil;

public class FindByNameAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Find Employees By Name ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        String name = input.askStr("Enter name: ");
        OutputUtil.print(employeeService.findAllByName(name));
        return true;
    }
}
