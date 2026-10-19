package io.daymark.task.repository;

import io.daymark.task.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findByCompleted(boolean completed, Pageable pageable);

    Page<TaskEntity> findByDueDateBeforeAndCompletedFalse(LocalDate date, Pageable pageable);
}
