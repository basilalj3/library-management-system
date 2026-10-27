package project;

public abstract class User {
    protected String id;
    protected String name;

    public User() {
        this.id = "U000";
        this.name = "Unknown";
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
