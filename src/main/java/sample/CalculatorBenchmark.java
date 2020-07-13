package sample;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 参考文章：https://www.xncoding.com/2018/01/07/java/jmh.html
 */

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class CalculatorBenchmark {
    @Param({"100", "1000", "10000", "100000", "1000000"})
    private int maxNumber;

    private Calculator singleThreadCalc;
    private Calculator multiThreadCalc;

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            .include(CalculatorBenchmark.class.getSimpleName())
            .output("./cal_Benchmark.log")
            .build();
        Collection<RunResult> results = new Runner(opt).run();
//        ResultExporter.exportResult("单线程与多线程求和性能", results, "length", "微秒");
    }

    @Benchmark
    public long singleThreadBench() throws ExecutionException, InterruptedException {
        long result = singleThreadCalc.sum(maxNumber);
        System.out.println("singleThreadBench :" + result);
        return result;
    }

    @Benchmark
    public long multiThreadBench() throws ExecutionException, InterruptedException {
        long result = multiThreadCalc.sum(maxNumber);
        System.out.println("multiThreadBench :" + result);
        return result;
    }

    @Setup
    public void prepare() {
        singleThreadCalc = new SinglethreadCalculator();
        multiThreadCalc = new MultithreadCalculator(Runtime.getRuntime().availableProcessors());
    }

    @TearDown
    public void shutdown() {
        singleThreadCalc.shutdown();
        multiThreadCalc.shutdown();
    }
}