package tasklist.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tasklist.spring.entities.TasksEntity;
import tasklist.spring.manager.TasksPrioritiser;
import tasklist.spring.manager.TasksRepositoryManager;
import tasklist.spring.repository.TasksRepository;

import java.util.List;

@Controller
public class TasklistController {

    @Autowired
    TasksRepositoryManager repositoryManager;

    @RequestMapping(value = "/tasklist", method = RequestMethod.GET)
    public String loadTasks(Model model) {
        List<TasksEntity> tasks = repositoryManager.loadTasks();
        model.addAttribute("tasks",tasks);
        return "tasklist";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createInProgressTask(@RequestParam(value="title", required=true) String title,
                                       @RequestParam(value="description", required=true) String description,
                                       @RequestParam(value="status", required=true) String status,
                                       @RequestParam(value="duedate", required=false) String duedate,
                                       @RequestParam(value="priority", required=false) String priority,
                                       Model model) {
        TasksEntity tasksEntity = TasksPrioritiser.createTasksEntity(null,
                                                    title,
                                                    description,
                                                    status,
                                                    duedate,
                                                    priority);
        List<TasksEntity> tasks = repositoryManager.addInProgressTask(tasksEntity);
        model.addAttribute("tasks", tasks);
        return "tasklist";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateTask(@RequestParam(value="id", required=true) Integer id,
                             @RequestParam(value="title", required=true) String title,
                             @RequestParam(value="description", required=true) String description,
                             @RequestParam(value="status", required=true) String status,
                             @RequestParam(value="duedate", required=false) String duedate,
                             @RequestParam(value="priority", required=false) String priority,
                             Model model) {

        TasksEntity tasksEntity = TasksPrioritiser.createTasksEntity(id,
                                                    title,
                                                    description,
                                                    status,
                                                    duedate,
                                                    priority);

        List<TasksEntity> tasks = repositoryManager.setTaskToFinished(tasksEntity);
        model.addAttribute("tasks", tasks);
        return "tasklist";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String removeTask(@RequestParam(value="id", required=true) Integer id, Model model) {
        List<TasksEntity> tasks = repositoryManager.removeFinishedTask(id);
        model.addAttribute("tasks", tasks);
        return "tasklist";
    }
}
