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
     * @return площадь полученной алгоритмом фигуры
     */
    Callable<Double> dotsWorker(int threadIndex) {
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

            //Возвращаем площадь полученной фигуры
            return
                    (maxCoordX - minCoordX + 2 * maxRadius) *
                            (maxCoordY - minCoordY + 2 * maxRadius) *
                            dotsCountByThread[threadIndex] / dotsCount;
        };
    }

    @Override
    public void run() {
        //Засекаем время начала
        long startTime = System.currentTimeMillis();

        //Определяем количество переменных для хранения данных потоков
        double[] squaresCountByThread = new double[threadCount];
        dotsCountByThread = new int[threadCount];

        //Объявляем менеджер потоков с фиксированным количеством потоков
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        //Задачи для выполнения менеджером запуска и исполнения потоков
        ArrayList<Future<Double>> futureTasks = new ArrayList<>();

        //Запускаем потоки
        for (int threadIndex = 0; threadIndex < threadCount; threadIndex++) {
            futureTasks.add(executorService.submit(dotsWorker(threadIndex)));
        }

        //Ожидаем, пока все потоки выполнят алгоритм
        for (int index = 0; index < threadCount; index++) {
            try {
                squaresCountByThread[index] = futureTasks.get(index).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        //Выполняем оконечные вычисления - подсчитываем суммарное количество точек в окружностях
        for (int i : dotsCountByThread) {
            dotsInCircles += i;
        }

        //Выполняем оконечные вычисления - подсчитываем суммарную площадь полученной фигуры
        for (double d : squaresCountByThread) {
            square += d;
        }

        //Отмечаем затраченное время
        time = System.currentTimeMillis() - startTime;

        //Формируем отчёт
        generateReport();
        //Разблокируем сервер
        Globals.setWorkingStatus(false);
    }

    public MonteCarloTasks(int threadCount) {
        this.threadCount = threadCount;
    }
}
