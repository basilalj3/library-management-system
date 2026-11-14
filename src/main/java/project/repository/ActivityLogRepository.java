package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.ActivityLog;

import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // Spring Data builds the query from the method name: newest first
    List<ActivityLog> findAllByOrderByIdDesc();
}
