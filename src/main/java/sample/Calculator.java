package sample;

import java.util.concurrent.ExecutionException;

public interface Calculator {
    /**
     * calculate sum of an integer array
     *
     * @param maxNumber
     * @return
     */
    public long sum(int maxNumber) throws InterruptedException, ExecutionException;

    /**
     * shutdown pool or reclaim any related resources
     */
    public void shutdown();
}