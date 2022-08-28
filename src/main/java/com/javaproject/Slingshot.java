package com.javaproject;

import static com.javaproject.Bird.SHOOTING_POINT_X;
import static com.javaproject.App.SCENE_HEIGHT;

public class Slingshot extends Entity {
    public static final double SLINGSHOT_WIDTH = 90;
    public static final double SLINGSHOT_HEIGHT = 200;

    public Slingshot() {
        super("slingshot");

        imageView.setFitWidth(SLINGSHOT_WIDTH);
        imageView.setFitHeight(SLINGSHOT_HEIGHT);
        imageView.setX(SHOOTING_POINT_X - SLINGSHOT_WIDTH / 2);
        imageView.setY(SCENE_HEIGHT - SLINGSHOT_HEIGHT);

    }

}
