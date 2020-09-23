package crowd.entity;

import java.util.List;
import java.util.Map;

public class Student {

    private String name;
    private Integer id;
    private Address address;
    private List<Subject> subjects;
    private Map<String,String> map;

    public Student() {
    }

    public Student(String name, Integer id, Address address, List<Subject> subjects, Map<String, String> map) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.subjects = subjects;
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", address=" + address +
                ", subjects=" + subjects +
                ", map=" + map +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
