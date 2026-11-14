package project.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {
    private String department;

    public Student() {
        super();
    }

    public Student(String id, String name, String dept) {
        super(id, name);
        this.department = dept;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Student: " + super.toString() + " | Dept: " + department;
    }
}
