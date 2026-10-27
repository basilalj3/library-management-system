package project;

public class Professor extends User {
    private String faculty;

    public Professor() {
        super();
    }

    public Professor(String id, String name, String faculty) {
        super(id, name);
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Professor: " + super.toString() + " | Faculty: " + faculty;
    }
}
