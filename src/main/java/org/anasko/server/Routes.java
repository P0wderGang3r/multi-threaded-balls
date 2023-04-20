package org.anasko.server;

import org.anasko.server.functions.MonteCarloTasks;
import org.anasko.server.functions.MonteCarloThreads;
import org.anasko.server.vars.Globals;

import java.util.ArrayList;
import java.util.List;

/**
 * Работают по общему принципу:
 * <br>
 * Сначала проверка занятости сервера, затем выполнения соответствующего действия
 */
public class Routes {

    ////---GETTER-ы-----------------------------------------------------------------------------------------------------

    /**
     * Получить данные о кругах на сцене
     */
    public static ArrayList<String> getScene() {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        ArrayList<String> result = new ArrayList<>();
        result.add("true");
        result.addAll(Globals.getScene().getScene());

        return result;
    }

    /**
     * Получить текущий статус занятости сервера
     */
    public static ArrayList<String> getWorkingStatus() {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("true"));

        return new ArrayList<>(List.of("false"));
    }

    /**
     * Получить результаты выполнения алгоритма
     */
    public static ArrayList<String> getResults() {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        ArrayList<String> result = new ArrayList<>();

        if (Globals.getResults().size() == 0) {
            result.add("empty");
            return result;
        }

        result.add("true");
        result.addAll(Globals.getResults());

        return result;
    }

    ////---SETTER-ы-----------------------------------------------------------------------------------------------------

    /**
     * Повторно создать множество окружностей
     */
    public static ArrayList<String> refreshCircles(int numOfCircles) {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        Globals.getScene().refreshCircles(numOfCircles);

        return new ArrayList<>(List.of("true"));
    }

    /**
     * Повторно создать множество точек
     */
    public static ArrayList<String> refreshDots(int numOfDots) {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        Globals.getScene().refreshDots(numOfDots);

        return new ArrayList<>(List.of("true"));
    }

    /**
     * Многопоточный алгоритм Монте-Карло с использованием Threads
     */
    public static ArrayList<String> startMCThreads(int threadCount) {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        new Thread(new MonteCarloThreads(threadCount)).start();

        return new ArrayList<>(List.of("true"));
    }

    /**
     * Многопоточный алгоритм Монте-Карло с использованием Tasks
     */
    public static ArrayList<String> startMCTasks(int threadCount) {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        new Thread(new MonteCarloTasks(threadCount)).start();

        return new ArrayList<>(List.of("true"));
    }
}
