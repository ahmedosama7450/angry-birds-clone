package com.javaproject;

public class Bomb extends Entity {

    public static final double BOMB_SIZE = 70;
    public static final int BOMB_FRAMES_COUNT = 4;
    public static final double BOMB_ANIMATION_TIME = 0.5d / BOMB_FRAMES_COUNT;

    private boolean isBlack;

    private int lastAnimationFrame = 0;
    private double elapsedAnimationTime = 0;

    public Bomb(double x, double y, boolean isBlack) {
        this.isBlack = isBlack;

        imageView.setX(x);
        imageView.setY(y);

        imageView.setFitWidth(BOMB_SIZE);
        imageView.setFitHeight(BOMB_SIZE);
    }

    @Override
    public void update(double delta, GameManager gameManager) {
        elapsedAnimationTime += delta;

        int currentAnimationFrame = ((int) (elapsedAnimationTime / BOMB_ANIMATION_TIME)) + 1;

        if (currentAnimationFrame > BOMB_FRAMES_COUNT) {
            // Remove automatically when animation finishes
            gameManager.removeEntity(this);
        }

        if (currentAnimationFrame != lastAnimationFrame) {
            imageView.setImage(
                    AssetsManager.instance.getImage((isBlack ? "bomb-black" : "bomb-white") + currentAnimationFrame));
        }

        lastAnimationFrame = currentAnimationFrame;
    }

}
