import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tasklist.spring.entities.TasksEntity;
import tasklist.spring.manager.TasksPrioritiser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TasksPrioritiserTest {

    List<TasksEntity> tasksEntityList = new ArrayList<>();
    TasksPrioritiser tasksPrioritiser = new TasksPrioritiser();

    int highp_len = 1;
    int medp_len = 1;
    int lowp_len = 1;

    @Before
    public void setUp() {
        tasksEntityList.addAll(Arrays.asList(
                TasksPrioritiser.createTasksEntity(0, "task1", "descr1", "p", "", "high"),
                TasksPrioritiser.createTasksEntity(1, "task2", "descr2", "p", "", "medium"),
                TasksPrioritiser.createTasksEntity(2, "task3", "descr3", "p", "", "low"),
                TasksPrioritiser.createTasksEntity(3, "task4", "descr4", "d", "", "high")
        ));

        tasksPrioritiser.addTask(tasksEntityList.get(2));
        tasksPrioritiser.addTask(tasksEntityList.get(1));
        tasksPrioritiser.addTask(tasksEntityList.get(3));
        tasksPrioritiser.addTask(tasksEntityList.get(0));
    }

    @Test
    public void testPrioritizing(){
        Assert.assertEquals(tasksEntityList, tasksPrioritiser.getPrioritizedTasksList());
    }

    @Test
    public void add_High_PriorityTask() {
        tasksEntityList.add(0, TasksPrioritiser.createTasksEntity(
                                                4,
                                                "task1",
                                                "descr1",
                                                "p",
                                                "",
                                                "high"));

        tasksPrioritiser.addTask(tasksEntityList.get(0));
        Assert.assertEquals(tasksEntityList, tasksPrioritiser.getPrioritizedTasksList());

        highp_len++;
    }

    @Test
    public void add_Medium_PriorityTask() {
        tasksEntityList.add(highp_len,
                            TasksPrioritiser.createTasksEntity(
                                                4,
                                                "task1",
                                                "descr1",
                                                "p",
                                                "",
                                                "medium"));

        tasksPrioritiser.addTask(tasksEntityList.get(highp_len));
        Assert.assertEquals(tasksEntityList, tasksPrioritiser.getPrioritizedTasksList());
        medp_len++;
    }

    @Test
    public void add_Low_PriorityTask() {
        int idx = highp_len + medp_len;
        tasksEntityList.add(idx,
                            TasksPrioritiser.createTasksEntity(
                                                4,
                                                "task1",
                                                "descr1",
                                                "p",
                                                "",
                                                "low"));

        tasksPrioritiser.addTask(tasksEntityList.get(idx));
        Assert.assertEquals(tasksEntityList, tasksPrioritiser.getPrioritizedTasksList());
        lowp_len++;
    }

    @Test
    public void add_Done_High_PriorityTask() {
        int idx = highp_len + medp_len + lowp_len;
        tasksEntityList.add(idx,
                            TasksPrioritiser.createTasksEntity(
                                                4,
                                                "task1",
                                                "descr1",
                                                "d",
                                                "",
                                                "high"));

        tasksPrioritiser.addTask(tasksEntityList.get(idx));
        Assert.assertEquals(tasksEntityList, tasksPrioritiser.getPrioritizedTasksList());
    }

    @Test
    public void add_Done_Medium_PriorityTask() {
        int idx = highp_len + medp_len + lowp_len;
        tasksEntityList.add(idx,
                            TasksPrioritiser.createTasksEntity(
                                                4,
                                                "task1",
                                                "descr1",
                                                "d",
                                                "",
                                                "medium"));

        tasksPrioritiser.addTask(tasksEntityList.get(idx));
        Assert.assertEquals(tasksEntityList, tasksPrioritiser.getPrioritizedTasksList());
    }

    @Test
    public void add_Done_Low_PriorityTask() {
        int idx = highp_len + medp_len + lowp_len;
        tasksEntityList.add(idx,
                            TasksPrioritiser.createTasksEntity(
                                                4,
                                                "task1",
                                                "descr1",
                                                "d",
                                                "",
                                                "low"));

        tasksPrioritiser.addTask(tasksEntityList.get(idx));
        Assert.assertEquals(tasksEntityList, tasksPrioritiser.getPrioritizedTasksList());
    }

    private TasksEntity getTasksEntity(String priority, String status) {
        TasksEntity tasksEntity = new TasksEntity();
        tasksEntity.setPriority(priority);
        tasksEntity.setStatus(status);
        return tasksEntity;
    }
}
