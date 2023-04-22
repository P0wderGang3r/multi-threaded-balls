package org.anasko.client.cli;

import org.anasko.client.IUserClient;
import org.anasko.server.Routes;

import java.util.ArrayList;
import java.util.Scanner;

public class UserClientCLI implements IUserClient {
    //Проверка на соответствие введённой строки числу
    private boolean parseIntCheck(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ignored) {
            System.out.println(Locale.getDefaultInputError());
            return true;
        }
        return false;
    }

    //Получение и расшифровка текущего статуса работы сервера
    private boolean getWorkingStatus() {
        ArrayList<String> result = Routes.getWorkingStatus();
        return result.get(0).equals("true");
    }

    //Получить текущее состояние занятости сервера
    private void route9() {
        if (getWorkingStatus())
            System.out.println(Locale.getServerIsBusy());
        else
            System.out.println(Locale.getServerIsReady());
        System.out.println();
    }


    //Получить результаты работы алгоритма
    private void route6() {
        ArrayList<String> result = Routes.getResults();
        if (result.get(0).equals("true")) {
            System.out.println(Locale.getProgressReportHeader());
            System.out.println(Locale.getLine1Name() + result.get(1));
            System.out.println(Locale.getLine2Threads() + result.get(2));
            System.out.println(Locale.getLine3Time() + result.get(3) + Locale.getLine3Time2());
            System.out.println(Locale.getLine4Dots() + result.get(4));
            System.out.println(Locale.getLine5Square() + result.get(5) + Locale.getLine5Square2());
        } else
        if (result.get(0).equals("empty")) {
            System.out.println(Locale.getAlgorithmDoneNothing());
        }
        else
        {
            System.out.println(Locale.getServerIsBusyError());
        }
        System.out.println();

    }

    //Многопоточный алгоритм Монте-Карло с использованием Java Tasks
    private void route5() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.print(Locale.getGenThreads());
        input = scanner.nextLine();
        if (parseIntCheck(input)) return;
        int threadCount = Integer.parseInt(input);

        ArrayList<String> result = Routes.startMCTasks(threadCount);

        if (result.get(0).equals("true")) {
            System.out.println(Locale.getAlgorithmIsStarted());
        }
        else {
            System.out.println(Locale.getAlgorithmIsNotStarted());
        }
        System.out.println();
    }

    //Многопоточный алгоритм Монте-Карло с использованием Java Threads
    private void route4() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.print(Locale.getGenThreads());
        input = scanner.nextLine();
        if (parseIntCheck(input)) return;
        int threadCount = Integer.parseInt(input);

        ArrayList<String> result = Routes.startMCThreads(threadCount);

        if (result.get(0).equals("true")) {
            System.out.println(Locale.getAlgorithmIsStarted());
        }
        else {
            System.out.println(Locale.getAlgorithmIsNotStarted());
        }
        System.out.println();
    }

    //Получение содержимого новой сцены
    private void route3() {
        ArrayList<String> result = Routes.getScene();
        if (result.get(0).equals("true")) {
            result.remove(0);

            System.out.println(Locale.getSceneReportHeader());
            for (var line: result) {
                System.out.println(line);
            }
        } else {
            System.out.println(Locale.getServerIsBusyError());
        }
        System.out.println();
    }

    //Создание нового набора точек
    private void route2() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.print(Locale.getGenSceneDots());
        input = scanner.nextLine();
        if (parseIntCheck(input)) return;
        int numOfDots = Integer.parseInt(input);

        ArrayList<String> result = Routes.refreshDots(numOfDots);
        if (result.get(0).equals("false")) {
            System.out.println(Locale.getServerIsBusyError());
        }
        System.out.println();
    }

    //Создание нового набора окружностей
    private void route1() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.print(Locale.getGenSceneCircles());
        input = scanner.nextLine();
        if (parseIntCheck(input)) return;
        int numOfCircles = Integer.parseInt(input);

        ArrayList<String> result = Routes.refreshCircles(numOfCircles);
        if (result.get(0).equals("false")) {
            System.out.println(Locale.getServerIsBusyError());
        }
        System.out.println();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input = "1";
        System.out.println(Locale.getHelpMessage());

        while(!input.equals("0")) {
            System.out.print(Locale.getInputMessage());
            input = scanner.nextLine();
            System.out.println();

            if(input.equals("help") || input.equals("?")) {
                System.out.println(Locale.getHelpMessage());
                System.out.println();
                continue;
            }

            if (parseIntCheck(input)) continue;

            //Создание нового набора окружностей
            if (Integer.parseInt(input) == 1) {
                route1();
            }

            //Создание нового набора точек
            if (Integer.parseInt(input) == 2) {
                route2();
            }

            //Получение содержимого новой сцены
            if (Integer.parseInt(input) == 3) {
                route3();
            }

            //Многопоточный алгоритм Монте-Карло с использованием Java Threads
            if (Integer.parseInt(input) == 4) {
                route4();
            }

            //Многопоточный алгоритм Монте-Карло
            if (Integer.parseInt(input) == 5) {
                route5();
            }

            //Получить результаты работы алгоритма
            if (Integer.parseInt(input) == 6) {
                route6();
            }

            //Получить текущее состояние занятости сервера
            if (Integer.parseInt(input) == 9) {
                route9();
            }
        }
    }
}
