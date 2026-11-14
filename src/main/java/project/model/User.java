package project.model;

import jakarta.persistence.*;

/*
 Abstract superclass for library users.
 Stored in one table "library_users" ("user" is a reserved word in SQL).
 */
@Entity
@Table(name = "library_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public abstract class User {
    @Id
    protected String id;

    @Column(nullable = false)
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

    // Lets the JSON show whether the user is a Student or a Professor
    public UserRole getRole() {
        return UserRole.valueOf(getClass().getSimpleName().toUpperCase());
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}
