package tasklist.spring.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tasklist.spring.entities.TasksEntity;

import java.util.List;
import java.util.Objects;

@Repository
@ComponentScan
public interface TasksRepository extends CrudRepository<TasksEntity, Integer>{

    @Query("SELECT id, title, description, status, duedate, priority  FROM TasksEntity WHERE status = ?1")
    List<Object[]> findByStatus(String state);
}
