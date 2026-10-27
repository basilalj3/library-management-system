package project;

public class Student extends User {
    private String department;

    public Student() {
        super();
    }

    public Student(String id, String name, String dept) {
        super(id, name);
        this.department = dept;
    }

    @Override
    public String toString() {
        return "Student: " + super.toString() + " | Dept: " + department;
    }
}
