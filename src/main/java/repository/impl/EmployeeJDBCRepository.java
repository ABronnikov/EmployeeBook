package repository.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import model.Employee;
import repository.Repository;

@Slf4j
public class EmployeeJDBCRepository implements Repository<Long, Employee> {

    private Connection connection;
    @Override
    public void init() {
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("database.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password")
            ); {
                DatabaseMetaData metaData = connection.getMetaData();
                log.info("Connection to database is successful - {}", !metaData.getConnection().isClosed());
                log.info("Connection to database by url - {}", metaData.getURL());
                String script = new BufferedReader(new FileReader("src/main/resources/scripts/employees_table.sql"))
                    .lines()
                    .collect(Collectors.joining());
                try (Statement statement = connection.createStatement()) {
                    int result = statement.executeUpdate(script);
                    if (result == 0) {
                        log.info("Table employees created successful."); // TODO: Не совсем корректная фраза, потому что если таблица существует скрипт не отработает
                    }
                }
            }
        } catch (Exception e) {
            log.error("Ошибка подключения к базе данных. {}, {}", e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public Employee save(Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO employees(name, city, created) VALUES (?, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getCity());
            statement.setTimestamp(3, Timestamp.valueOf(employee.getCreated()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                long generatedId = rs.getLong("id");
                log.info("Employee with id - {} save successful.", generatedId);
                employee.setId(generatedId);
            }
        } catch (SQLException sqlException) {
            log.error("Exception in update method. {}, {}", sqlException, sqlException.getMessage());
        }
         return employee;
    }

    @Override
    public boolean update(Long id, Employee employee) {
        boolean rsl = false;
        try (PreparedStatement statement = connection.prepareStatement(
            "UPDATE employees SET name = ?, city = ? WHERE id = ?"
        )) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getCity());
            statement.setLong(3, id);
            if (statement.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException sqlException) {
            log.error("Exception in update method. {}, {}", sqlException, sqlException.getMessage());
        }
        return rsl;
    }

    @Override
    public boolean delete(Long id) {
        boolean rsl = false;
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?")) {
            statement.setLong(1, id);
            if (statement.executeUpdate() > 0) {
                rsl = true;
            }
        } catch (SQLException sqlException) {
            log.error("Exception in delete method. {}, {}", sqlException, sqlException.getMessage());
        }
        return rsl;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        Optional<Employee> rsl = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement("SELECT  * FROM  employees WHERE id = ?")) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee employee = Employee.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .city(rs.getString("city"))
                    .created(rs.getTimestamp("created").toLocalDateTime())
                    .build();
                rsl = Optional.ofNullable(employee);
            }
        } catch (SQLException sqlException) {
            log.error("Exception in findById method. {}, {}", sqlException, sqlException.getMessage());
        }
        return rsl;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM  employees");
            while (resultSet.next()) {
                Employee employee = Employee.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .city(resultSet.getString("city"))
                    .created(resultSet.getTimestamp("created").toLocalDateTime())
                    .build();
                result.add(employee);
            }
        } catch (SQLException sqlException) {
            log.error("Exception in findAll method. {}, {}", sqlException, sqlException.getMessage());
        }
        return result;
    }

    @Override
    public List<Employee> findByName(String name) {
        List<Employee> rsl = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT  * FROM  employees WHERE name = ?")) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee employee = Employee.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .city(rs.getString("city"))
                    .created(rs.getTimestamp("created").toLocalDateTime())
                    .build();
                rsl.add(employee);
            }
        } catch (SQLException sqlException) {
            log.error("Exception in findById method. {}, {}", sqlException, sqlException.getMessage());
        }
        return rsl;
    }

    @Override
    public List<Employee> findByCreatedDateInterval(LocalDateTime start, LocalDateTime end) {
        List<Employee> rsl = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM employees WHERE created BETWEEN ? AND ?")) {
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = Employee.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .city(resultSet.getString("city"))
                    .created(resultSet.getTimestamp("created").toLocalDateTime())
                    .build();
                rsl.add(employee);
            }
        } catch (SQLException sqlException) {
            log.error("Exception in findByCreatedDateInterval method. {}, {}", sqlException, sqlException.getMessage());
        }
        return rsl;
    }

    @Override
    public void close() {
        try {
            connection.close();
            log.info("Session is closed.");
        } catch (SQLException e) {
            log.error("Incorrect close session. Message - {}", e.getMessage());
        }
    }
}
