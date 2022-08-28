package actions;

import input.Input;
import model.Employee;
import service.EmployeeService;

public class CreateAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Create a new Employee ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        String name = input.askStr("Enter name: ");
        String city = input.askStr("Enter city: ");
        Employee employee = new Employee(name, city);
        return employeeService.save(employee);
    }
}
