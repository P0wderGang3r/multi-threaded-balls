package org.anasko.server.objects;

import lombok.Getter;

public class Dot {
    @Getter
    private final double coordX;

    @Getter
    private final double coordY;

    public Dot(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }
}
