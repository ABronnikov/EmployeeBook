package actions.impl;

import actions.EmployeeAction;
import input.Input;
import java.time.format.DateTimeParseException;
import service.EmployeeService;
import util.Constants;
import util.OutputUtil;

public class FindByIntervalDateAction implements EmployeeAction {

    @Override
    public String name() {
        return "==== Find Employees By Interval Date ====";
    }

    @Override
    public boolean execute(Input input, EmployeeService employeeService) {
        System.out.printf("Please enter date by format - %s\n", Constants.PATTERN_DATE);
        String start = input.askStr("Enter start date: ");
        String end = input.askStr("Enter end date: ");
        try {
            OutputUtil.print(employeeService.findAllByIntervalDate(start, end));
        } catch (DateTimeParseException exception) {
            System.out.println("Incorrect date format. Repeat please.");
        }
        return true;
    }
}
