package project.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/*
 One line of the activity log (replaces the old in-memory Notification list).
 */
@Entity
@Table(name = "activity_log")
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected ActivityLog() {
        // required by JPA
    }

    public ActivityLog(String message) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
