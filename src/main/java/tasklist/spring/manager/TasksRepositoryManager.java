package tasklist.spring.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tasklist.spring.entities.TasksEntity;
import tasklist.spring.repository.TasksRepository;

import java.util.*;

@Service
@ComponentScan
public class TasksRepositoryManager {

    TasksRepository tasksRepository;

    private final TasksPrioritiser tasksPrioritiser;

    private static final Integer MAX_TASKS = 100;

    public TasksRepositoryManager() {
        tasksPrioritiser = new TasksPrioritiser();
    }

    public List<TasksEntity> addInProgressTask(TasksEntity t) {
        TasksEntity saved = tasksRepository.save(t);
        tasksPrioritiser.addTask(saved);
        return tasksPrioritiser.getPrioritizedTasksList();
    }

    public List<TasksEntity> setTaskToFinished(TasksEntity t) {
        TasksEntity update = tasksRepository.save(t);
        tasksPrioritiser.removeTask(t);
        tasksPrioritiser.addTask(update);
        return tasksPrioritiser.getPrioritizedTasksList();
    }

    public List<TasksEntity> removeFinishedTask(Integer id) {
        TasksEntity task = tasksRepository.findOne(id);
        tasksRepository.delete(id);
        tasksPrioritiser.removeTask(task);
        return tasksPrioritiser.getPrioritizedTasksList();
    }

    public List<TasksEntity> loadTasks() {
        List<Object[]> tasks = tasksRepository.findByStatus("p");
        List<Object[]> tasksDone = tasksRepository.findByStatus("d");
        tasksPrioritiser.addAllTasks(tasks, false);
        tasksPrioritiser.addAllTasks(tasksDone, true);
        return tasksPrioritiser.getPrioritizedTasksList();
    }

    public void setTasksRepository(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }
}
