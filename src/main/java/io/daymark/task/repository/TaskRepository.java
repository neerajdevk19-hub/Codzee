package io.daymark.task.repository;

import io.daymark.task.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("""
            select task from TaskEntity task
            where (:completed is null or task.completed = :completed)
              and (:search is null
                   or lower(task.title) like lower(concat('%', :search, '%')) escape '!'
                   or lower(task.description) like lower(concat('%', :search, '%')) escape '!')
            """)
    Page<TaskEntity> searchTasks(
            @Param("search") String search,
            @Param("completed") Boolean completed,
            Pageable pageable);
}
