package org.anasko.server.functions;

import org.anasko.server.objects.Circle;
import org.anasko.server.objects.Dot;
import org.anasko.server.vars.Globals;

import java.util.ArrayList;
import java.util.concurrent.*;

public class MonteCarloTasks implements Runnable {
    private final int threadCount;

    private double time = 0;

    private int[] dotsCountByThread;

    private int dotsInCircles = 0;

    private double square;

    private void generateReport() {
        ArrayList<String> result = new ArrayList<>();
        result.add("Monte-Carlo with tasks");
        result.add("" + threadCount);
        result.add("" + time);
        result.add("" + dotsInCircles);
        result.add("" + square);
        Globals.setResults(result);
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


    Callable<Double> dotsWorker(int threadIndex) {
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

            return ((maxCoordX - minCoordX + 2 * maxRadius) + minCoordX - maxRadius) *
                    ((maxCoordY - minCoordY + 2 * maxRadius) + minCoordY - maxRadius) *
                    dotsCountByThread[threadIndex] / dotsCount;
        };
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        double[] squaresCountByThread = new double[threadCount];

        dotsCountByThread = new int[threadCount];

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        ArrayList<Future<Double>> futureTasks = new ArrayList<>();

        for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
            futureTasks.add(executorService.submit(dotsWorker(threadIndex)));
        }

        for (int index = 0; index < threadCount; index++) {
            try {
                squaresCountByThread[index] = futureTasks.get(index).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i : dotsCountByThread) {
            dotsInCircles += i;
        }

        for (double d : squaresCountByThread) {
            square += d;
        }

        time = System.currentTimeMillis() - startTime;

        generateReport();
        Globals.setWorkingStatus(false);
    }

    public MonteCarloTasks(int threadCount) {
        this.threadCount = threadCount;
    }
}
