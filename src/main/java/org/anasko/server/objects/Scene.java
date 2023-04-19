package org.anasko.server.objects;

import lombok.Getter;
import org.anasko.server.vars.Globals;

import java.util.ArrayList;

public class Scene {
    @Getter
    private final ArrayList<Circle> circles = new ArrayList<>(0);

    @Getter
    private final ArrayList<Dot> dots = new ArrayList<>(0);
    
    @Getter
    private final double maxRadius = 3;

    @Getter
    private final double minRadius = 1;

    @Getter
    private final double maxCoordX = 100;

    @Getter
    private final double minCoordX = -100;

    @Getter
    private final double maxCoordY = 100;

    @Getter
    private final double minCoordY = -100;
    
    private int numOfCircles = 0;
    private int numOfDots = 0;
    
    private final Runnable refreshCirclesWorker = () -> {
        circles.clear();

        for (int index = 0; index < numOfCircles; index++) {
            circles.add(new Circle(
                    Math.random() * (maxCoordX - minCoordX) + minCoordX,
                    Math.random() * (maxCoordY - minCoordY) + minCoordY,
                    Math.random() * (maxRadius - minRadius) + minRadius
            ));
        }

        numOfCircles = 0;

        Globals.setWorkingStatus(false);
    };

    private final Runnable refreshDotsWorker = () -> {
        dots.clear();

        for (int index = 0; index < numOfDots; index++) {
            dots.add(new Dot(
                    Math.random() * (maxCoordX - minCoordX + 2 * maxRadius) + minCoordX - maxRadius,
                    Math.random() * (maxCoordY - minCoordY + 2 * maxRadius) + minCoordY - maxRadius
            ));
        }

        numOfDots = 0;

        Globals.setWorkingStatus(false);
    };
    
    public void refreshCircles(int numOfCircles) {
        this.numOfCircles = numOfCircles;
        
        new Thread(refreshCirclesWorker).start();
    }

    public void refreshDots(int numOfDots) {
        this.numOfDots = numOfDots;

        new Thread(refreshDotsWorker).start();
    }

    public ArrayList<String> getScene() {
        ArrayList<String> result = new ArrayList<>();
        for (Circle circle: circles) {
            result.add(circle.getCoordX() + " " + circle.getCoordY() + " " + circle.getRadius());
        }

        return result;
    }
}
