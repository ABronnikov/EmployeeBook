package util;

import dto.EmployeeDto;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OutputUtil {

    public void print(List<EmployeeDto> employees) {
        if (employees.isEmpty()) {
            System.out.println("Employees not found.");
        } else {
            employees.forEach(System.out::println);
        }
    }
}
