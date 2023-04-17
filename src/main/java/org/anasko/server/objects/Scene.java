package org.anasko.server.objects;

import lombok.Getter;
import lombok.Setter;
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
    private final double maxCoordX = 10;

    @Getter
    private final double minCoordX = -10;

    @Getter
    private final double maxCoordY = 10;

    @Getter
    private final double minCoordY = -10;
    
    private int numOfCircles = 0;
    private int numOfDots = 0;
    
    private final Runnable refreshSceneWorker = () -> {
        circles.clear();
        dots.clear();

        for (int index = 0; index < numOfCircles; index++) {
            circles.add(new Circle(
                    Math.random() * (maxCoordX - minCoordX) + minCoordX,
                    Math.random() * (maxCoordY - minCoordY) + minCoordY,
                    Math.random() * (maxRadius - minRadius) + minRadius
            ));
        }

        for (int index = 0; index < numOfDots; index++) {
            dots.add(new Dot(
                    Math.random() * (maxCoordX - minCoordX + 2 * maxRadius) + minCoordX - maxRadius,
                    Math.random() * (maxCoordY - minCoordY + 2 * maxRadius) + minCoordY - maxRadius
            ));
        }

        numOfCircles = 0;
        numOfDots = 0;

        Globals.setWorkingStatus(false);
    };
    
    public void refreshScene(int numOfCircles, int numOfDots) {
        this.numOfCircles = numOfCircles;
        this.numOfDots = numOfDots;
        
        new Thread(refreshSceneWorker).start();
    }

    public ArrayList<String> getScene() {
        ArrayList<String> result = new ArrayList<>();
        for (Circle circle: circles) {
            result.add(circle.getCoordX() + " " + circle.getCoordY() + " " + circle.getRadius());
        }

        return result;
    }
}
