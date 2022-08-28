package com.javaproject;

import javafx.scene.image.ImageView;

public class Entity {
    protected ImageView imageView;

    // The higher the order, the more the entity is in the end of the entities list
    // (rendered last, updated last)
    private int order = 0;

    public Entity() {
        imageView = new ImageView();
    }

    public Entity(String imageName) {
        imageView = new ImageView(AssetsManager.instance.getImage(imageName));
    }

    public Entity(String imageName, double x, double y, double width, double height) {
        imageView = new ImageView(AssetsManager.instance.getImage(imageName));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public void update(double deltaTime, GameManager gameManager) {
        // It's up to game objects to define their own implementation, if they need to
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getOrder() {
        return order;
    }

    /**
     * You should not call this method directly.
     * It's used internally by {@link GameManager#addEntity(Entity, int)}.
     */
    public void setOrder(int order) {
        this.order = order;
    }

}
