package org.anasko.server.vars;

import lombok.Getter;
import lombok.Setter;
import org.anasko.server.objects.Scene;

import java.util.ArrayList;

public class Globals {
    /**
     * Если сервер занят, то true, иначе false
     */
    @Getter @Setter
    private static volatile boolean isWorkingStatus = false;

    /**
     * Множество всех сцен
     */
    @Getter
    private final static Scene scene = new Scene();

    /**
     * Результаты и оценка скорости работы алгоритма
     */
    @Getter @Setter
    private static ArrayList<String> results = new ArrayList<>();
}
