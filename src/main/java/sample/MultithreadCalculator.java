package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author xiaoxi666
 * @date 2020-07-13 15:28
 */
public class MultithreadCalculator implements Calculator {
    ExecutorService service;
    private final int threadNum;

    public MultithreadCalculator(int threadNum) {
        this.threadNum = threadNum;
        service = Executors.newFixedThreadPool(threadNum);
    }


    @Override
    public long sum(int maxNumber) throws InterruptedException, ExecutionException {
        long sum = 0;
        List<CalThread> calThreads = new ArrayList<>();
        for (int i = 0; i < threadNum; ++i) {
            int fromInt = maxNumber * i / threadNum + 1;
            int toInt = maxNumber * (i + 1) / threadNum;
            calThreads.add(new CalThread(toInt, fromInt));
        }
        List<Future<Long>> results = service.invokeAll(calThreads);
        for (Future<Long> result : results) {
            sum += result.get();
        }
        return sum;
    }

    @Override
    public void shutdown() {
        service.shutdown();
    }
}

class CalThread implements Callable<Long> {
    private int maxNumber;
    private int minNumber;

    CalThread(int maxNumber, int minNumber) {
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
    }

    @Override
    public Long call() throws Exception {
        long sum = 0;
        while (maxNumber >= minNumber) {
            sum += maxNumber;
            --maxNumber;
        }
        return sum;
    }
}
