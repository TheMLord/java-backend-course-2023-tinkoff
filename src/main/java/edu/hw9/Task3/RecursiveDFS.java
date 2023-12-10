package edu.hw9.Task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Recursive DFS.
 */
public class RecursiveDFS extends RecursiveTask<Boolean> {
    private static final Logger DFS_LOGGER =
        LogManager.getLogger(RecursiveDFS.class.getName());
    private static final String WORK_MESSAGE = "do task";

    private final List<List<Integer>> arrayAdj;
    private final boolean[] visitors;
    private final int v;
    private final int target;

    public RecursiveDFS(List<List<Integer>> arrayAdj, int v, int target, boolean[] visitors) {
        this.arrayAdj = arrayAdj;
        this.v = v;
        this.target = target;
        this.visitors = visitors;
    }

    @Override
    protected Boolean compute() {
        DFS_LOGGER.info(WORK_MESSAGE);

        boolean isFind = false;

        visitors[v] = true;

        if (v == target) {
            isFind = true;
        }

        List<RecursiveDFS> tasks = new ArrayList<>();

        for (int neighbor : arrayAdj.get(v)) {
            if (!visitors[neighbor]) {
                if (neighbor == target) {
                    isFind = true;
                } else {
                    var task = new RecursiveDFS(arrayAdj, neighbor, target, visitors);
                    tasks.add(task);
                    task.fork();
                }

            }
        }
        for (var task : tasks) {
            isFind |= task.join();
        }

        return isFind;
    }
}
