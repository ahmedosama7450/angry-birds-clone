package com.javaproject;

import javafx.scene.Cursor;

import static com.javaproject.App.SCENE_HEIGHT;

public class Bird extends Entity {

    public static final double SHOOTING_POINT_X = 175;
    public static final double SHOOTING_POINT_Y = SCENE_HEIGHT - 165;
    public static final double SHOOTING_RADIUS = 80;

    public static final double SHOOTING_VELOCITY = 1100;
    public static final double GRAVITY = 1800;

    private boolean isReleased;
    private double velocityX, velocityY;

    private boolean isDragged;

    public Bird(String birdName) {
        resetBird(birdName);

        imageView.setCursor(Cursor.HAND);
        imageView.setOnMouseDragged(e -> {
            double distanceSquared = Math.pow(SHOOTING_POINT_X - e.getX(), 2)
                    + Math.pow(SHOOTING_POINT_Y - e.getY(), 2);
            if (distanceSquared > Math.pow(SHOOTING_RADIUS, 2)) {
                double a = SHOOTING_POINT_X;
                double b = SHOOTING_POINT_Y;
                double r = SHOOTING_RADIUS;
                double c = e.getX();
                double d = e.getY();
                double m = Math.sqrt((Math.pow(r, 2) * Math.pow(c - a, 2)) / (Math.pow(c - a, 2) + Math.pow(b - d, 2)));

                double newX = c > a ? a + m : a - m;
                double newY = b - ((newX - a) * (b - d)) / (c - a);
                imageView.setX(newX - imageView.getFitWidth() / 2);
                imageView.setY(newY - imageView.getFitHeight() / 2);
            } else {
                imageView.setX(e.getX() - imageView.getFitWidth() / 2);
                imageView.setY(e.getY() - imageView.getFitHeight() / 2);
            }

            isDragged = true;

        });
        imageView.setOnMouseReleased(e -> {
            /*
             * birdImageView.setX(SHOOTING_POINT_X - birdImageView.getFitWidth() / 2 - 4);
             * birdImageView
             * .setY(SHOOTING_POINT_Y - birdImageView.getFitWidth() / 2);
             */
            if (isDragged) {
                double deltaY = SHOOTING_POINT_Y - imageView.getY();
                double deltaX = SHOOTING_POINT_X - imageView.getX();
                double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

                isReleased = true;
                velocityX = (SHOOTING_VELOCITY * deltaX / distance) + 20;
                velocityY = (-SHOOTING_VELOCITY * deltaY / distance) * 1.3 + 200;

            }

            isDragged = false;
        });

    }

    @Override
    public void update(double deltaTime, GameManager gameManager) {
        if (isReleased) {
            // Apply velocity
            double newX = imageView.getX() + velocityX * deltaTime;
            double newY = imageView.getY() - velocityY * deltaTime;

            imageView.setX(newX);
            imageView.setY(newY);

            // Apply gravity
            velocityY += -GRAVITY * deltaTime;

            // Kill bird when it's out of screen
            if (newX < 0 || newX > App.SCENE_WIDTH + 20 || newY < -100 || newY > App.SCENE_HEIGHT + 20) {
                gameManager.killBird();

            }

        }

    }

    public void resetBird(String birdName) {
        this.isReleased = false;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isDragged = false;

        if (birdName != null) {
            imageView.setImage(AssetsManager.instance.getImage(birdName));

            float size;
            switch (birdName) {
                case "chuck":
                    size = 40;
                    break;
                case "terence":
                    size = 80;
                    break;
                case "red":
                default:
                    size = 50;
                    break;
            }

            imageView.setFitWidth(size);
            imageView.setFitHeight(size);
        }

        double initialX = SHOOTING_POINT_X - imageView.getFitWidth() / 2 - 4;
        double initialY = SHOOTING_POINT_Y - imageView.getFitHeight() / 2;
        imageView.setX(initialX);
        imageView.setY(initialY);
    }

    public void resetBird() {
        resetBird(null);
    }

}
