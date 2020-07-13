package sample;

/**
 * @author xiaoxi666
 * @date 2020-07-13 15:28
 */
public class SinglethreadCalculator implements Calculator {

    @Override
    public long sum(int maxNumber) {
        long sum = 0;
        while (maxNumber > 0) {
            sum += maxNumber;
            --maxNumber;
        }
        return sum;
    }

    @Override
    public void shutdown() {

    }
}
