package repository.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import repository.Repository;

@Slf4j
public class EmployeeHibernateRepository implements Repository<Long, Employee> {

    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    @Override
    public void init() {
        this.registry = new StandardServiceRegistryBuilder()
            .configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata()
            .buildSessionFactory();
    }

    @Override
    public Employee save(Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        return employee;
    }

    @Override
    public boolean update(Long id, Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            /*
            Эквивалентно след коду
            Query<Employee> query = session.createQuery("update Employee set name = :name, city = :city where id = :id");
            query.setParameter("name", employee.getName());
            query.setParameter("city", employee.getCity());
            query.setParameter("id", id);
            query.executeUpdate();*/
            Employee result = session.get(Employee.class, id);
            result.setName(employee.getName());
            result.setCity(employee.getCity());
            session.update(result);
            session.getTransaction().commit();
        } catch (HibernateException hibernateException) {
            return false;
        }
         return true;
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employee result = session.get(Employee.class, id);
            session.delete(result);
            session.getTransaction().commit();
        } catch (HibernateException hibernateException) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.getTransaction().commit();
        return Optional.ofNullable(employee);
    }

    @Override
    public List<Employee> findAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Employee> employees = session.createQuery("from Employee", Employee.class).getResultList();
        session.getTransaction().commit();
        return employees;
    }

    @Override
    public List<Employee> findByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Employee> query = session.createQuery("from Employee where name = :name", Employee.class);
        query.setParameter("name", name);
        session.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public List<Employee> findByCreatedDateInterval(LocalDateTime start, LocalDateTime end) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Employee> query = session.createQuery("from Employee where created between :startTime and :endTime", Employee.class);
        query.setParameter("startTime", start);
        query.setParameter("endTime", end);
        session.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public void close() {
        try {
            StandardServiceRegistryBuilder.destroy(registry);
            log.info("Session is closed.");
        } catch (Exception e) {
            log.error("Incorrect close session. Message - {}. Canonical exception - {}", e.getMessage(), e.getClass().getCanonicalName());
        }
    }
}
