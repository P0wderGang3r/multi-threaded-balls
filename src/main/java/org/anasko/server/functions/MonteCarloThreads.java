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
        result.add("Monte-Carlo with threads");
        result.add("" + threadCount);
        result.add("" + time);
        result.add("" + dotsInCircles);
        result.add("" + square);
        Globals.setResults(result);
    }

    /**
     * Оконечные вычисления - суммарное количество точек в окружностях и площадь фигуры
     */
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

    /**
     * Проверка, находится ли точка внутри одной из окружностей на плоскости
     * @param threadIndex Номер потока
     * @param dotIndex Номер точки
     */
    private void checkIfDotInCircles(int threadIndex, int dotIndex) {
        Dot dot = Globals.getScene().getDots().get(dotIndex);

        //Проходимся по всем окружностям
        for (Circle circle : Globals.getScene().getCircles()) {
            //Если длина отрезка от центра данной окружности до переданной точки меньше, чем радиус данной окружности...
            if (Math.sqrt(
                    (dot.coordX() - circle.coordX()) * (dot.coordX() - circle.coordX()) +
                            (dot.coordY() - circle.coordY()) * (dot.coordY() - circle.coordY()))
                    < circle.radius()) {
                //...то подсчитываем эту точку и выходим из цикла
                dotsCountByThread[threadIndex] += 1;
                return;
            }
        }
    }

    /**
     * Реализация потока для вычисления принадлежности точек окружностям
     * @param threadIndex Номер потока
     */
    Runnable dotsWorker(int threadIndex, CyclicBarrier BARRIER) {
        double maxCoordX = Globals.getScene().getMaxCoordX();
        double minCoordX = Globals.getScene().getMinCoordX();
        double maxCoordY = Globals.getScene().getMaxCoordY();
        double minCoordY = Globals.getScene().getMinCoordY();
        double maxRadius = Globals.getScene().getMaxRadius();
        int dotsCount = Globals.getScene().getDots().size();

        return () -> {
            //Сдвиг указателя на точку на количество потоков
            int dotIndex = threadIndex;

            //Ищем количество попавших в окружности точки
            while (dotIndex < Globals.getScene().getDots().size()) {
                checkIfDotInCircles(threadIndex, dotIndex);
                dotIndex += threadCount;
            }

            //Вычисляем площадь полученной фигуры
            squaresCountByThread[threadIndex] =
                    (maxCoordX - minCoordX + 2 * maxRadius) *
                            (maxCoordY - minCoordY + 2 * maxRadius) *
                            dotsCountByThread[threadIndex] / dotsCount;

            //Ожидаем, пока все потоки выполнят алгоритм
            try {
                BARRIER.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public void run() {
        //Засекаем время начала
        long startTime = System.currentTimeMillis();

        //Определяем количество переменных для хранения данных потоков
        dotsCountByThread = new int[threadCount];
        squaresCountByThread = new double[threadCount];

        //Создаём барьер, который должен быть достигнут всеми потоками
        //При достижении всеми потоками барьера выполнятся оконечные вычисления
        CyclicBarrier BARRIER = new CyclicBarrier(threadCount + 1, postBarrierCalculations());

        //Запускаем потоки
        for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
            new Thread(dotsWorker(threadIndex, BARRIER)).start();
        }

        //Ожидаем, пока все потоки выполнят алгоритм
        //Это нужно для корректного подсчёта времени и составления корректного отчёта
        try {
            BARRIER.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        //Отмечаем затраченное время
        time = System.currentTimeMillis() - startTime;

        //Формируем отчёт
        generateReport();
        //Разблокируем сервер
        Globals.setWorkingStatus(false);
    }

    public MonteCarloThreads(int threadCount) {
        this.threadCount = threadCount;
    }
}
