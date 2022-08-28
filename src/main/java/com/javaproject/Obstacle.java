package com.javaproject;

public class Obstacle extends Entity {

    public static final double OBSTACLE_THICKNESS = 25;

    public Obstacle(double x, double y, double length, boolean isVertical) {
        super(isVertical ? "obstacle-vertical" : "obstacle");

        imageView.setFitWidth(isVertical ? OBSTACLE_THICKNESS : length);
        imageView.setFitHeight(isVertical ? length : OBSTACLE_THICKNESS);
        imageView.setX(x);
        imageView.setY(y);
    }

    @Override
    public void update(double delta, GameManager gameManager) {
        Bird bird = gameManager.getSelectedBird();

        // Collision with bird
        if (imageView.getBoundsInParent()
                .intersects(bird.getImageView().getBoundsInParent())) {
            // Remove obstacle
            gameManager.removeEntity(this);

            gameManager.increaseScore(5);

            // Show bomb
            gameManager.addEntity(
                    new Bomb(bird.getImageView().getX() + bird.getImageView().getFitWidth(),
                            bird.getImageView().getY() + bird.getImageView().getFitHeight() / 2, false));

            // kill bird
            gameManager.killBird();

        }
    }

}
