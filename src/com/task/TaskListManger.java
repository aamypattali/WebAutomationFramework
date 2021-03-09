/**
 *
 */
package com.task;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author ssathinadhan
 */
public class TaskListManger {

    private static TaskListManger taskListManger = null;

    private static Logger logger = Logger.getLogger(TaskListManger.class.getName());

    private final List<Task> taskList = Collections.synchronizedList(new ArrayList<>());

    private TaskListManger() {
    }

    public static TaskListManger getInstance() {
        if (TaskListManger.taskListManger == null) {
            TaskListManger.taskListManger = new TaskListManger();
            TaskListManger.logger.info("Creating new instance of TaskList");
        }
        return TaskListManger.taskListManger;
    }

    public void executeTask() {
        if (this.taskList.isEmpty()) {
            TaskListManger.logger.info("No task in list to execute.");
            return;
        }

        for (final Task task : this.taskList) {
            this.executeClass(task);
        }
    }

    public void verifyTask(final Task task) {
        if (task == null) {
            TaskListManger.logger.warn("Null task recieved.");
            return;
        }

        // if task in error and tasklist does not contains task then add
        if (task.isError() && !this.taskList.contains(task)) {
            TaskListManger.logger.info("Adding " + task);
            this.taskList.add(task);
            return;
        }

        if (!task.isError() && this.taskList.contains(task)) {
            TaskListManger.logger.info("Removing " + task);
            this.taskList.remove(task);
            return;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void executeClass(final Task task) {
        try {

            final Class paramTypes[] = {};
            final Class cls = Class.forName(task.getClassName());
            if (cls == null) {
                System.out.println("Unable to load " + task.getClassName());
                return;
            }

            final Object obj = cls.newInstance();
            if (obj == null) {
                System.out.println("Unable to instantiate " + task.getClassName());
                return;
            }

            final Object[] params = null;
            for (final String methodName : task.getMethodNames()) {
                final Method method = cls.getMethod(methodName, paramTypes);
                if (method == null) {
                    System.out.println("Unable to access method " + methodName);
                    continue;
                }
                method.invoke(obj, params);
            }

        } catch (final AssertionError e) {
            System.out.println(task.getClassName() + " " + task.getMethodNames() + " test failed");
        } catch (final Exception e) {
            System.out.println(task.getClassName() + " " + task.getMethodNames() + " invoke failed");
        }

    }
}
