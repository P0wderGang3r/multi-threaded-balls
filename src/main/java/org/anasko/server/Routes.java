package org.anasko.server;

import org.anasko.server.functions.MonteCarloThreads;
import org.anasko.server.vars.Globals;

import java.util.ArrayList;
import java.util.List;

public class Routes {

    ////---GETTER-ы-----------------------------------------------------------------------------------------------------

    public static ArrayList<String> getScene() {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        ArrayList<String> result = new ArrayList<>();
        result.add("true");
        result.addAll(Globals.getScene().getScene());

        return result;
    }

    public static ArrayList<String> getWorkingStatus() {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("true"));

        return new ArrayList<>(List.of("false"));
    }

    public static ArrayList<String> getResults() {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        ArrayList<String> result = new ArrayList<>();
        result.add("true");
        result.addAll(Globals.getResults());

        return result;
    }

    ////---SETTER-ы-----------------------------------------------------------------------------------------------------

    public static ArrayList<String> refreshCircles(int numOfCircles) {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        Globals.getScene().refreshCircles(numOfCircles);

        return new ArrayList<>(List.of("true"));
    }

    public static ArrayList<String> refreshDots(int numOfDots) {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        Globals.getScene().refreshDots(numOfDots);

        return new ArrayList<>(List.of("true"));
    }

    public static ArrayList<String> startMCThreads(int threadCount) {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        new Thread(new MonteCarloThreads(threadCount)).start();

        return new ArrayList<>(List.of("true"));
    }
}
