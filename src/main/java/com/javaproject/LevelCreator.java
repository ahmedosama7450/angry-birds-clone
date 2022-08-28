package com.javaproject;

import static com.javaproject.Obstacle.OBSTACLE_THICKNESS;

import static com.javaproject.App.SCENE_WIDTH;
import static com.javaproject.App.SCENE_HEIGHT;

public class LevelCreator {
    public static final double PIGS_X_SPACING = 5;
    public static final double PIGS_Y_SPACING = 1;
    public static final double MIN_PIG_SIZE = 30, MAX_PIG_SIZE = 60;
    public static final int MAX_PIGS_Y_COUNT = 3;
    public static final int MAX_PIGS_X_COUNT = 10;

    private GameManager gameManager;

    public LevelCreator(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * This method creates a new level with randomly positioned pigs and obstacles
     */
    public void nextLevel(int pigsCount) {
        //
        // Before creating new level, remove current level entities
        //

        // We use iterator because, inside update method, we can safely remove entities
        // If we don't use iterator, we get `ConcurrentModificationException`
        // See:
        // https://stackoverflow.com/questions/1196586/calling-remove-in-foreach-loop-in-java
        for (Entity entity : gameManager.getEntities()) {
            if (entity instanceof Pig || entity instanceof Obstacle) {
                gameManager.removeEntity(entity);
            }
        }

        //
        // Let's create the level
        //
        if (pigsCount < 3) {
            pigsCount = 3;
        } else if (pigsCount > 15) {
            pigsCount = 15;
        }

        double worldStartX = SCENE_WIDTH / 2 + 50;
        double worldEndX = SCENE_WIDTH - 10;
        double worldWidth = worldEndX - worldStartX;

        int pigsLeftCount = pigsCount;
        double currentRowY = 10;// TODO this should depend on pigs count

        while (pigsLeftCount > 0) {
            double currentRowX = worldStartX;
            double currentRowLeftWidth = worldWidth;
            double maxRowHeight = 0;

            while (currentRowLeftWidth > 0 && pigsLeftCount > 0) {
                double pigSize = MIN_PIG_SIZE + Math.random() * (MAX_PIG_SIZE - MIN_PIG_SIZE);

                int xCountMax = Math.min(MAX_PIGS_X_COUNT, (int) (currentRowLeftWidth
                        / (calculatePigsTotalSize(pigSize, pigsLeftCount, 1)[0])));
                int xCountRandom = (int) (Math.random() * xCountMax) + 1;
                int xCount = xCountRandom > pigsLeftCount ? pigsLeftCount : xCountRandom;

                if (xCount <= 0) {
                    currentRowLeftWidth = 0;
                } else {
                    int configurationType = (int) (Math.random() * 7) + 1; // From 1 to 7

                    int yCountRandom = (int) (Math.random() * MAX_PIGS_Y_COUNT) + 1;
                    int yCount = yCountRandom * xCount > pigsLeftCount ? 1 : yCountRandom;

                    double size[] = createPigConfiguration(configurationType, pigSize, currentRowX, currentRowY, xCount,
                            yCount);

                    currentRowX += size[0] + 12;
                    currentRowLeftWidth -= size[0] + 12;
                    maxRowHeight = Math.max(maxRowHeight, size[1]);

                    pigsLeftCount -= yCount * xCount;
                }

            }

            currentRowY += maxRowHeight + 12;
        }

    }

    /**
     * @param configurationType defines how the configuration looks like
     *                          1 => -
     *                          2 => top + right + left
     *                          3 => top + right
     *                          4 => top + left
     *                          5 => right + left
     *                          6 => right
     *                          7 => left
     * 
     * @return array of two elements, first is width of resulting configuration,
     *         second is height
     */
    private double[] createPigConfiguration(int configurationType, double pigSize, double x, double yFromBottom,
            int xCount,
            int yCount) {

        double[] pigsTotalSize = calculatePigsTotalSize(pigSize, xCount, yCount);

        addPigs(pigSize, x + OBSTACLE_THICKNESS + PIGS_X_SPACING,
                yFromBottom + OBSTACLE_THICKNESS, xCount, yCount);

        double bottomObstacleLength = 2 * OBSTACLE_THICKNESS + 2 * PIGS_X_SPACING + pigsTotalSize[0];
        double bottomObstacleY = SCENE_HEIGHT - yFromBottom - OBSTACLE_THICKNESS;
        double leftObstacleLength = pigsTotalSize[1];
        double leftObstacleY = SCENE_HEIGHT - yFromBottom - OBSTACLE_THICKNESS - leftObstacleLength;

        // Bottom obstacle
        gameManager.addEntity(new Obstacle(x, bottomObstacleY, bottomObstacleLength, false));

        // Top obstacle
        if (configurationType == 2 || configurationType == 3 || configurationType == 4) {
            gameManager.addEntity(
                    new Obstacle(x, bottomObstacleY - leftObstacleLength - OBSTACLE_THICKNESS, bottomObstacleLength,
                            false));
        }

        // Left obstacle
        if (configurationType == 2 || configurationType == 4 || configurationType == 5 || configurationType == 7) {
            gameManager.addEntity(new Obstacle(x, leftObstacleY, leftObstacleLength, true));
        }

        // Right obstacle
        if (configurationType == 2 || configurationType == 3 || configurationType == 5 || configurationType == 6) {
            gameManager.addEntity(
                    new Obstacle(x + bottomObstacleLength - OBSTACLE_THICKNESS, leftObstacleY, leftObstacleLength,
                            true));

        }

        return new double[] { bottomObstacleLength, leftObstacleLength + 2 * OBSTACLE_THICKNESS };
    }

    private void addPigs(double pigSize, double x, double yFromBottom, int xCount, int yCount) {
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                gameManager
                        .addEntity(
                                new Pig(x + i * (pigSize + PIGS_X_SPACING),
                                        SCENE_HEIGHT - yFromBottom
                                                - ((j + 1) * (pigSize + PIGS_Y_SPACING) - PIGS_Y_SPACING),

                                        pigSize,
                                        Math.random() > 0.5 ? true : false));
            }
        }
    }

    private double[] calculatePigsTotalSize(double pigSize, int xCount, int yCount) {
        double[] pigsTotalSize = new double[2];
        pigsTotalSize[0] = xCount * (pigSize + PIGS_X_SPACING) - PIGS_X_SPACING;
        pigsTotalSize[1] = yCount * (pigSize + PIGS_Y_SPACING) - PIGS_Y_SPACING;

        return pigsTotalSize;
    }

}
