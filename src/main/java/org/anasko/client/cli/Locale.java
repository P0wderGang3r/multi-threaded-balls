package org.anasko.client.cli;

import lombok.Getter;

public class Locale {
    @Getter
    private final static String helpMessage = """
            0 : exit the program
            1 : Generate new set of cirles
            2 : Generate new set of dots
            3 : View generated scene
            4 : Use multi-threaded Monte-Carlo algorithm with Java threads
            5 : Use multi-threaded Monte-Carlo algorithm with Java tasks
            6 : View algorithm results
            9 : Get current server status
            Your choice:\s""";

    //----QUESTIONS-----------------------------------------------------------------------------------------------------

    @Getter
    private final static String genSceneCircles = "Enter a number of circles: ";

    @Getter
    private final static String genSceneDots = "Enter a number of dots: ";

    @Getter
    private final static String genThreads = "Enter a number of threads: ";

    //----REPORTS-------------------------------------------------------------------------------------------------------
    @Getter
    private final static String sceneReportHeader = "Scene generation report: ";

    @Getter
    private final static String progressReportHeader = "Progress report: ";

    @Getter
    private final static String serverIsReady = "Server is ready to go!";

    @Getter
    private final static String serverIsBusy = "Server is busy.";

    @Getter
    private final static String algorithmIsStarted = "Algorithm is successfully started.";

    @Getter
    private final static String algorithmIsNotStarted = "Algorithm is not started.";

    @Getter
    private final static String algorithmDoneNothing = "Algorithm was not started yet.";

    //----REPORT LINES--------------------------------------------------------------------------------------------------

    @Getter
    private final static String line1Name = "Last algorithm used: ";

    @Getter
    private final static String line2Threads = "Number of threads used: ";

    @Getter
    private final static String line3Time = "Estimated time: ";

    @Getter
    private final static String line3Time2 = " millis";

    @Getter
    private final static String line4Dots = "Counted number of dots in circles: ";

    @Getter
    private final static String line5Square = "Estimated square: ";

    @Getter
    private final static String line5Square2 = " units^2";


    //----ERRORS--------------------------------------------------------------------------------------------------------
    @Getter
    private final static String defaultInputError = "There are typo in an input string.";

    @Getter
    private final static String serverIsBusyError = "Server is busy. Try again later.";
}
