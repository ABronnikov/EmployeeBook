package actions;

import input.Input;
import model.Employee;
import service.EmployeeService;

public class FindAllAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Find all Employee ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        for (Employee employee: employeeService.findAll()) {
            System.out.println(employee);
        }
        return true;
    }
}
