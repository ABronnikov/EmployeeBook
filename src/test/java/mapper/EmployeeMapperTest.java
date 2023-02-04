package mapper;

import dto.EmployeeDto;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;
import model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class EmployeeMapperTest {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Test
    void fromEmployeeToEmployeeDto() {
        try {
            Employee employee = new Employee("Alex", "Omsk");
            log.info("Employee DTO - {}", employeeMapper.fromEmployeeToEmployeeDto(employee));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void fromEmployeeDtoToEmployee() {
        try {
            EmployeeDto employee = new EmployeeDto("Alex", "Omsk");
            log.info("Employee - {}", employeeMapper.fromEmployeeDtoToEmployeeEntity(employee));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void parseDate() {
        try {
            String date = "30-09-2022 21:08";
            employeeMapper.parseDate(date);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void whenParseDateWithException() {
        Assertions.assertThrows(DateTimeParseException.class, () -> {
            String date = "djslkdlasdjlasd";
            employeeMapper.parseDate(date);
        });
    }

}