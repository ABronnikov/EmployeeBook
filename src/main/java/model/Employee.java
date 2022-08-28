package model;

import util.Util;

public class Employee {

    private long id;
    private String name;
    private String city;
    /*private LocalDateTime localDateTime;*/

    public Employee() {
    }

    public Employee(String name, String city) {
        Long unique = Util.uniqueId();
        System.out.println(String.format("Employee created with id - %s", unique));
        this.id = unique;
        this.name = name;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", city='" + city + '\'' +
            '}';
    }
}
