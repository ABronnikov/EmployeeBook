package actions.impl;

import actions.EmployeeAction;
import input.Input;
import service.EmployeeService;
import sort.Ordered;
import util.OutputUtil;

public class FindAllAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Find all Employees ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        OutputUtil.print(employeeService.findAll(Ordered.DEFAULT));
        return true;
    }
}
