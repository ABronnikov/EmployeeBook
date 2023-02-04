package repository.impl;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import repository.Repository;

import static util.Constants.INCORRECT_INDEX;

@Slf4j
public class EmployeeMemRepository implements Repository<Long, Employee> {

    private final List<Employee> store = new ArrayList<>();

    public void init() {
        log.info("Инициализировано хранилище в памяти.");
    }

    public Employee save(Employee employee) {
        long id = generateId();
        employee.setId(id);
        store.add(employee);
        log.info("Пользователь с id - {} сохранен.", id);
        return employee;
    }

    public boolean update(Long id, Employee employee) {
        int index = indexOf(id);
        boolean result = index != INCORRECT_INDEX;
        if (result) {
            employee.setId(id);
            employee.setCreated(store.get(index).getCreated());
            store.set(index, employee);
        }
        return result;
    }

    public boolean delete(Long id) {
        int index = indexOf(id);
        boolean result = index != INCORRECT_INDEX;
        if (result) {
            store.remove(index);
        }
        return result;
    }

    public Optional<Employee> findById(Long id) {
        int index = indexOf(id);
        return index != INCORRECT_INDEX
            ? Optional.of(store.get(index))
            : Optional.empty();
    }

    public List<Employee> findAll() {
        return new ArrayList<>(store);
    }

    public List<Employee> findByName(String name) {
        return store.stream()
            .filter(employee -> employee.getName().equals(name))
            .collect(Collectors.toList());
    }

    public List<Employee> findByCreatedDateInterval(LocalDateTime start, LocalDateTime end) {
        Predicate<Employee> afterDate = employee -> !employee.getCreated().isAfter(end);
        Predicate<Employee> beforeDate = employee -> !employee.getCreated().isBefore(start);
        return store.stream()
            .filter(afterDate.and(beforeDate))
            .collect(Collectors.toList());
    }

    @Override
    public void close() {
        log.info("This is mem repository. Session is not found.");
    }

    private int indexOf(long id) {
        return IntStream.range(0, store.size())
            .filter(index -> store.get(index).getId() == id)
            .findFirst()
            .orElse(INCORRECT_INDEX);
    }

    private long generateId() {
        return Math.abs(new Random().nextLong());
    }
}
