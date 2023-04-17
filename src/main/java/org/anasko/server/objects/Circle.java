package org.anasko.server.objects;

import lombok.Getter;

public class Circle {
    @Getter
    private final double coordX;

    @Getter
    private final double coordY;

    @Getter
    private final double radius;

    public Circle(double coordX, double coordY, double radius) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.radius = radius;
    }
}
