package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
public class PiCalculator {
    public static class piCalculator implements Runnable {
        public piCalculator() {

        }
        public piCalculator(int n) {
            this.n = n;
            mathContext = new MathContext(1100);
        }
        public int n;
        public static MathContext mathContext;
        public static BigDecimal seriesSum = BigDecimal.ZERO;
        public static synchronized void addToSum(BigDecimal seriesNumber) {
            seriesSum = seriesSum.add(seriesNumber);
        }
        public BigDecimal factorial(int number) {
            BigDecimal result = BigDecimal.ONE;

            for (int i = 1; i <= number; i++) {
                result = result.multiply(BigDecimal.valueOf(i), mathContext);
            }

            return result;
        }
        @Override
        public void run() {
            BigDecimal nominator = (factorial(4 * n)).multiply(new BigDecimal(26390 * n + 1103), mathContext);
            BigDecimal denominator = (factorial(n).pow(4)).multiply(new BigDecimal(396).pow(4 * n), mathContext);
            BigDecimal seriesNumber = nominator.divide(denominator, mathContext);

            addToSum(seriesNumber);
        }
    }
    public static String calculate(int floatingPoint) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 200; i++) {
            piCalculator picalculator = new piCalculator(i);
            executorService.execute(picalculator);
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);

        BigDecimal big = new BigDecimal(8).sqrt(piCalculator.mathContext).divide(BigDecimal.valueOf(9801), piCalculator.mathContext);

        piCalculator.seriesSum = piCalculator.seriesSum.multiply(big, piCalculator.mathContext);

        return BigDecimal.ONE.divide(piCalculator.seriesSum, piCalculator.mathContext).setScale(floatingPoint, RoundingMode.DOWN).toString();
    }
    public static void main(String[] Args) throws InterruptedException {
        System.out.println(PiCalculator.calculate(1000));
    }
}
