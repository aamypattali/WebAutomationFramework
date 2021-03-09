
package com.task;

import org.apache.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @author ssathinadhan
 */
public class TaskExecutor {

    private static Logger logger = Logger.getLogger(TaskExecutor.class.getName());

    @Test(alwaysRun = true)
    @Parameters({ "taskLoopCount" })
    public void executeTasks(final int taskLoopCount) {
        if (taskLoopCount <= 0) {
            TaskExecutor.logger.info("Exiting executeTasks since loop is defined as 0");
            return;
        }

        System.out.println("Executing error tasks in " + taskLoopCount + " passes");
        
        final TaskListManger taskListManager = TaskListManger.getInstance();

        for (int i = 0; i < taskLoopCount; i++) {
            TaskExecutor.logger.info("Start executing pass " + (i + 1));
            taskListManager.executeTask();
            TaskExecutor.logger.info("End executing pass " + (i + 1));
        }
    }
}
