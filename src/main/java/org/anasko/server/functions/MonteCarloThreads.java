package org.anasko.server.functions;

import org.anasko.server.objects.Circle;
import org.anasko.server.objects.Dot;
import org.anasko.server.vars.Globals;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MonteCarloThreads implements Runnable {
    private final int threadCount;

    private double time = 0;

    private int[] dotsCountByThread;

    private int dotsInCircles = 0;

    private double square;

    private double[] squaresCountByThread;

    private void generateReport() {
        ArrayList<String> result = new ArrayList<>();
        result.add("" + threadCount);
        result.add("" + time);
        result.add("" + dotsInCircles);
        result.add("" + square);
        Globals.setResults(result);
    }

    Runnable postBarrierCalculations() {
        return () -> {
            for (int i : dotsCountByThread) {
                dotsInCircles += i;
            }

            for (double d : squaresCountByThread) {
                square += d;
            }
        };
    }

    private void checkIfDotInCircles(int threadIndex, int dotIndex) {
        Dot dot = Globals.getScene().getDots().get(dotIndex);

        for (Circle circle : Globals.getScene().getCircles()) {
            if (Math.sqrt(
                    (dot.getCoordX() - circle.getCoordX()) * (dot.getCoordX() - circle.getCoordX()) +
                            (dot.getCoordY() - circle.getCoordY()) * (dot.getCoordY() - circle.getCoordY()))
                    < circle.getRadius()) {
                dotsCountByThread[threadIndex] += 1;
                return;
            }
        }
    }


    Runnable dotsWorker(int threadIndex, CyclicBarrier cyclicBarrier) {
        double maxCoordX = Globals.getScene().getMaxCoordX();
        double minCoordX = Globals.getScene().getMinCoordX();
        double maxCoordY = Globals.getScene().getMaxCoordY();
        double minCoordY = Globals.getScene().getMinCoordY();
        double maxRadius = Globals.getScene().getMaxRadius();
        int dotsCount = Globals.getScene().getDots().size();

        return () -> {
            int dotIndex = threadIndex;

            while (dotIndex < Globals.getScene().getDots().size()) {
                checkIfDotInCircles(threadIndex, dotIndex);
                dotIndex += threadCount;
            }

            squaresCountByThread[threadIndex] =
                    ((maxCoordX - minCoordX + 2 * maxRadius) + minCoordX - maxRadius) *
                    ((maxCoordY - minCoordY + 2 * maxRadius) + minCoordY - maxRadius) *
                     dotsCountByThread[threadIndex] / dotsCount;

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        dotsCountByThread = new int[threadCount];
        squaresCountByThread = new double[threadCount];

        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount + 1, postBarrierCalculations());

        for (int index = 0; index < threadCount; index++) {
            new Thread(dotsWorker(index, cyclicBarrier)).start();
        }

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        time = System.currentTimeMillis() - startTime;

        generateReport();
        Globals.setWorkingStatus(false);
    }

    public MonteCarloThreads(int threadCount) {
        this.threadCount = threadCount;
    }
}
