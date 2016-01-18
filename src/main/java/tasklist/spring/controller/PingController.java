package tasklist.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tasklist.spring.repository.TasksRepository;

@Controller
public class PingController {

    @Autowired
    TasksRepository tasksRepository;

    @RequestMapping("/ping")
    public String greeting(Model model) {
        try {
            long count = tasksRepository.count();
            if (count >= 0) {
                model.addAttribute("message", " I am Alive! There are: " + count + " rows.");
            } else {
                model.addAttribute("message", "  JPA is OK");
            }
            return "ping";
        }
        catch(Exception ex) {
            model.addAttribute("message", " JPA Error found: " + ex.getMessage());
            return "ping";
        }
    }
}
