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
            result.remove(0);

            System.out.println(Locale.getProgressReportHeader());
            for (var line: result) {
                System.out.println(line);
            }
        } else {
            System.out.println(Locale.getServerIsBusyError());
        }
        System.out.println();
    }

    //Многопоточный алгоритм Монте-Карло
    private void route5() {

    }

    //Многопоточный алгоритм Монте-Карло
    private void route4() {

    }

    //Однопоточный алгоритм Монте-Карло
    private void route3() {

    }

    //Получение содержимого новой сцены
    private void route2() {
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

    //Создание новой сцены
    private void route1() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.print(Locale.getGenSceneCircles());
        input = scanner.nextLine();
        if (parseIntCheck(input)) return;
        int numOfCircles = Integer.parseInt(input);

        System.out.print(Locale.getGenSceneDots());
        input = scanner.nextLine();
        if (parseIntCheck(input)) return;
        int numOfDots = Integer.parseInt(input);

        ArrayList<String> result = Routes.refreshScene(numOfCircles, numOfDots);
        if (result.get(0).equals("false")) {
            System.out.println(Locale.getServerIsBusyError());
        }
        System.out.println();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input = "1";

        while(!input.equals("0")) {
            System.out.print(Locale.getHelpMessage());
            input = scanner.nextLine();

            if (parseIntCheck(input)) continue;

            //Создание новой сцены
            if (Integer.parseInt(input) == 1) {
                route1();
            }

            //Получение содержимого новой сцены
            if (Integer.parseInt(input) == 2) {
                route2();
            }

            //Однопоточный алгоритм Монте-Карло
            if (Integer.parseInt(input) == 3) {
                route3();
            }

            //Многопоточный алгоритм Монте-Карло
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
