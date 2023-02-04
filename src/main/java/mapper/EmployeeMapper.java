package mapper;

import static util.Constants.PATTERN_DATE;

import dto.EmployeeDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Employee;

/**
 * Маппер для класса Employee
 */
public class EmployeeMapper {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);

    /**
     * Преобразует сущность в ДТО + парсит дату в формат 1980-10-15 15:33
     * @param employee entity
     * @return dto
     */
    public EmployeeDto fromEmployeeToEmployeeDto (Employee employee) {
        return EmployeeDto.builder()
            .name(employee.getName())
            .city(employee.getCity())
            .created(employee.getCreated().format(dateTimeFormatter))
            .build();
    }

    /**
     * Преобразует employeeDto в сущность employee entity.
     * @param employeeDto dto
     * @return employee entity
     */
    public Employee fromEmployeeDtoToEmployeeEntity (EmployeeDto employeeDto) {
        return Employee.builder()
            .name(employeeDto.getName())
            .city(employeeDto.getCity())
            .created(LocalDateTime.now())
            .build();
    }

    /**
     * Парсим строку в дату.
     * @param date дата предствленная строкой.
     * @return дата в формате LocalDateTime.
     */
    public LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

}
