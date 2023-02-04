package service;

import dto.EmployeeDto;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import mapper.EmployeeMapper;
import model.Employee;
import repository.Repository;

import java.util.List;
import sort.EmployeeComparatorAsc;
import sort.EmployeeComparatorDesc;
import sort.Ordered;

@RequiredArgsConstructor
public class EmployeeService {

    private final Repository<Long, Employee> employeeRepository;

    private final EmployeeMapper employeeMapper;

    public boolean save(EmployeeDto employeeDto) {
        return employeeRepository.save(employeeMapper.fromEmployeeDtoToEmployeeEntity(employeeDto)).getId() != 0;
    }

    public boolean update(long id, EmployeeDto employee) {
        return employeeRepository.update(id, employeeMapper.fromEmployeeDtoToEmployeeEntity(employee));
    }

    public boolean delete(long id) {
        return employeeRepository.delete(id);
    }

    public Optional<EmployeeDto> findById(long id) {
        return employeeRepository.findById(id)
            .map(employeeMapper::fromEmployeeToEmployeeDto);
    }

    public List<EmployeeDto> findAll(Ordered ordered) {
        List<EmployeeDto> employees = getMappingData(employeeRepository.findAll().stream());

        switch (ordered) {
            case ASC:
                employees.sort(new EmployeeComparatorAsc());
                return employees;
            case DESC:
                employees.sort(new EmployeeComparatorDesc());
                return employees;
            case DEFAULT:
                return employees;
        }
        return List.of();
    }

    public List<EmployeeDto> findAllByName(String name) {
        return getMappingData(employeeRepository.findByName(name).stream());
    }

    public List<EmployeeDto> findAllByIntervalDate(String start, String end) {
        return getMappingData(employeeRepository.findByCreatedDateInterval(
            employeeMapper.parseDate(start), employeeMapper.parseDate(end)
        ).stream());
    }

    public void closeAllSession() {
        employeeRepository.close();
    }

    private List<EmployeeDto> getMappingData(Stream<Employee> employeeStream) {
        return employeeStream
            .map(employeeMapper::fromEmployeeToEmployeeDto)
            .collect(Collectors.toList());
    }
}
