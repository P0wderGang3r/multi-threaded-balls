package org.anasko.client.cli;

import lombok.Getter;

public class Locale {
    @Getter
    private final static String helpMessage = """
            0 : exit the program
            1 : Generate new scene
            2 : View generated scene
            3 : Use linear Monte-Carlo algorithm
            4 : Use ... algorithm
            5 : Use ... algorithm
            6 : View algorithm results
            9 : Get current server status
            Your choice:\s""";

    @Getter
    private final static String genSceneCircles = "Enter a number of circles: ";

    @Getter
    private final static String genSceneDots = "Enter a number of dots: ";

    @Getter
    private final static String progressReportHeader = "Progress report:";

    @Getter
    private final static String serverIsBusy = "Server is busy.";

    @Getter
    private final static String serverIsReady = "Server is ready to go!";

    @Getter
    private final static String defaultInputError = "There are typo in an input string.";

    @Getter
    private final static String serverIsBusyError = "Server is busy. Try again later.";
}
