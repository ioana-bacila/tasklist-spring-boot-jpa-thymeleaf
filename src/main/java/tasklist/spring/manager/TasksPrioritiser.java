package tasklist.spring.manager;


import tasklist.spring.entities.TasksEntity;

import java.util.ArrayList;
import java.util.List;

public class TasksPrioritiser {
    private int highTasksLen;
    private int mediumTasksLen;
    private int lowTasksLen;
    private List<TasksEntity> tasksEntityList;


    public TasksPrioritiser() {
        highTasksLen = 0;
        mediumTasksLen = 0;
        lowTasksLen = 0;
        tasksEntityList = new ArrayList<>();
    }

    public void addTask(TasksEntity task) {
        if (task.getStatus().equals("d")) {
            this.removeTask(task);
            int finishedTaskIdx = highTasksLen +
                                  mediumTasksLen +
                                  lowTasksLen;
            this.tasksEntityList.add(finishedTaskIdx, task);
            return;
        }

        if(task.getPriority().equals("high")) {
            this.tasksEntityList.add(0, task);
            highTasksLen++;
        } else if (task.getPriority().equals("medium")) {
            this.tasksEntityList.add(highTasksLen, task);
            mediumTasksLen++;
        } else {
            this.tasksEntityList.add(highTasksLen + mediumTasksLen, task);
            lowTasksLen ++;
        }
    }

    public void addAllTasks(List<Object[]> tasksEntityList, boolean concat) {
        int finishedTaskIdx = highTasksLen + mediumTasksLen + lowTasksLen;
        for(Object[] task : tasksEntityList) {
            TasksEntity tasksEntity = createTasksEntity(Integer.parseInt(task[0].toString()),
                                                        task[1].toString(),
                                                        task[2].toString(),
                                                        task[3].toString(),
                                                        task[4].toString(),
                                                        task[5].toString());
            if(concat) {
                this.tasksEntityList.add(finishedTaskIdx++, tasksEntity);
            } else {
                addTask(tasksEntity);
            }
        }
    }

    public void removeTask(TasksEntity task) {
        if (task.getStatus().equals("d")) {
            tasksEntityList.remove(task);
            return;
        }

        if(task.getPriority().equals("high")) {
            highTasksLen --;
        } else if (task.getPriority().equals("medium")) {
            mediumTasksLen --;
        } else {
            lowTasksLen --;
        }
        tasksEntityList.remove(task);
    }

    public List<TasksEntity> getPrioritizedTasksList() {
        return tasksEntityList;
    }

    public static TasksEntity createTasksEntity(Integer id,
                                                String title,
                                                String description,
                                                String status,
                                                String duedate,
                                                String priority) {

        TasksEntity tasksEntity = new TasksEntity();
        if (id != null) {
            tasksEntity.setId(id);
        }
        tasksEntity.setTitle(title);
        tasksEntity.setDescription(description);
        tasksEntity.setStatus(status);
        tasksEntity.setDuedate(duedate);
        tasksEntity.setPriority(priority);

        return tasksEntity;

    }
}
