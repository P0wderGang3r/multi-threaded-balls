package org.anasko.server;

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

    public static ArrayList<String> refreshScene(int numOfCircles, int numOfDots) {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        Globals.getScene().refreshScene(numOfCircles, numOfDots);

        return new ArrayList<>(List.of("true"));
    }

    public static ArrayList<String> startLinearMC() {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("false"));

        Globals.setWorkingStatus(true);
        //SOMETHING THERE SHOULD START WORKING RN

        return new ArrayList<>(List.of("true"));
    }
}
