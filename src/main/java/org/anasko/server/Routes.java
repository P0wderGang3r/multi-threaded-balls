package org.anasko.server;

import org.anasko.server.vars.Globals;

import java.util.ArrayList;
import java.util.List;

public class Routes {

    ////---GETTER-ы-----------------------------------------------------------------------------------------------------

    public static ArrayList<String> getScene() {
        return Globals.getScene().getScene();
    }

    public static ArrayList<String> getWorkingStatus() {
        if (Globals.isWorkingStatus())
            return new ArrayList<>(List.of("true"));

        return new ArrayList<>(List.of("false"));
    }

    public static ArrayList<String> getResults() {
        return Globals.getResults();
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
