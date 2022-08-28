package com.javaproject;

public class Pig extends Entity {

    public Pig(double x, double y, double size, boolean withHelmet) {
        super(withHelmet ? "helmet-pig" : "pig");

        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setX(x);
        imageView.setY(y);

    }

    @Override
    public void update(double delta, GameManager gameManager) {
        Bird bird = gameManager.getSelectedBird();

        // Collision with bird
        if (imageView.getBoundsInParent()
                .intersects(bird.getImageView().getBoundsInParent())) {
            gameManager.removeEntity(this);
            gameManager.increaseScore(10);

            // Show bomb
            gameManager.addEntity(new Bomb(imageView.getX() + imageView.getFitWidth() / 2 - Bomb.BOMB_SIZE / 2,
                    imageView.getY() + imageView.getFitHeight() / 2 - Bomb.BOMB_SIZE / 2, true));

            gameManager.killPig();
            gameManager.killBird();

        }
    }
}
