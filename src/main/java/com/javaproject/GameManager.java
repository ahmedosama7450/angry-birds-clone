package com.javaproject;

import java.util.ArrayList;

import javafx.animation.Timeline;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import static com.javaproject.App.SCENE_WIDTH;
import static com.javaproject.App.SCENE_HEIGHT;

public class GameManager {

    public static final int INITIAL_BIRDS_LEFT = 8, BIRDS_INCREASE_PER_LEVEL = 3;
    public static final int FIRST_LEVEL_PIGS_COUNT = 4, PIG_INCREASE_PER_LEVEL = 1;

    private Pane pane;
    private Scene scene;
    private ArrayList<Entity> entities;

    private ArrayList<Entity> toRemoveEntities, toAddEntities;

    private LevelCreator levelCreator;

    private Bird selectedBird;

    private int birdsLeftCount = INITIAL_BIRDS_LEFT;
    private Label birdsLeftLabel;

    private int score = 0;
    private Label scoreLabel;

    private int highestScore = 0;
    private Label highestScoreLabel;

    private long lastFrameTime = 0;

    private int leftPigsCount = 0;

    public GameManager() {
        pane = new Pane();
        scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);
        entities = new ArrayList<>();

        toRemoveEntities = new ArrayList<>();
        toAddEntities = new ArrayList<>();

        levelCreator = new LevelCreator(this);

        selectedBird = new Bird("red");

        // Add game entities
        addEntity(selectedBird, 100);
        addEntity(new Entity("bg", 0, 0, SCENE_WIDTH, SCENE_HEIGHT));
        addEntity(new Slingshot());

        nextLevel(FIRST_LEVEL_PIGS_COUNT);

        // Create header
        createHeaderMenu();

        // Create game loop
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.seconds(1d / 60d), event -> {
            long currentFrameTime = System.currentTimeMillis();
            long deltaTime = currentFrameTime - lastFrameTime;
            lastFrameTime = currentFrameTime;

            for (Entity entity : entities) {
                entity.update(Duration.millis(deltaTime).toSeconds(), this);
            }

            // Remove entities safely
            for (Entity toRemoveEntity : toRemoveEntities) {
                entities.remove(toRemoveEntity);
                pane.getChildren().remove(toRemoveEntity.getImageView());
            }
            toRemoveEntities.clear();

            // Add entities safely
            for (Entity toAddEntity : toAddEntities) {
                int n = entities.size();
                int insertedIndex = 0;

                for (int i = 0; i <= n; i++) {
                    if (i == n || toAddEntity.getOrder() < entities.get(i).getOrder()) {
                        entities.add(i, toAddEntity);
                        insertedIndex = i;
                        break;
                    }
                }

                pane.getChildren().add(insertedIndex, toAddEntity.getImageView());
            }
            toAddEntities.clear();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

    private void createHeaderMenu() {
        // Bird choices
        HBox birdChoicesLayout = new HBox();
        birdChoicesLayout.setSpacing(20);
        String[] birdChoices = { "red", "chuck", "terence" };
        for (String birdChoice : birdChoices) {
            ImageView birdChoiceImageView = new ImageView(AssetsManager.instance.getImage(birdChoice));
            birdChoiceImageView.setFitWidth(40);
            birdChoiceImageView.setFitHeight(40);
            birdChoiceImageView.setCursor(Cursor.HAND);
            birdChoiceImageView.setOnMouseClicked(event -> {
                selectedBird.resetBird(birdChoice);
            });

            birdChoicesLayout.getChildren().add(birdChoiceImageView);
        }

        // Birds left
        birdsLeftLabel = new Label();
        birdsLeftLabel.setFont(new Font(18));
        setBirdsLeftCount(birdsLeftCount);

        // Score
        scoreLabel = new Label("Score: " + score);
        scoreLabel.setFont(new Font(18));
        setScore(score);

        // Highest score
        highestScoreLabel = new Label("Highest score: " + highestScore);
        highestScoreLabel.setFont(new Font(18));
        setHighestScore(highestScore);

        // Restart button
        Button restartButton = new Button("Restart");
        restartButton.setFont(new Font(14));
        restartButton.setOnAction(event -> {
            restart();
        });

        HBox headerLayout = new HBox();
        headerLayout.setSpacing(150);
        headerLayout.setPadding(new Insets(15, 40, 15, 40));
        headerLayout.setAlignment(Pos.CENTER);
        headerLayout.getChildren().addAll(birdChoicesLayout, birdsLeftLabel, scoreLabel, highestScoreLabel,
                restartButton);
        pane.getChildren().add(headerLayout);
    }

    private void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score: " + score);
    }

    private void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
        highestScoreLabel.setText("Highest score: " + highestScore);
    }

    private void setBirdsLeftCount(int birdsLeftCount) {
        this.birdsLeftCount = birdsLeftCount;
        birdsLeftLabel.setText("Birds left: " + birdsLeftCount);
    }

    public void killBird() {
        setBirdsLeftCount(birdsLeftCount - 1);

        if (birdsLeftCount == 0) {
            // Game is over

            Label gameOverLabel = new Label("Game over");
            gameOverLabel.setFont(new Font(48));

            // Score
            Label scoreLabel = new Label("Score: " + score);
            scoreLabel.setFont(new Font(18));

            // Highest score
            Label highestScoreLabel = new Label("Highest score: " + Math.max(highestScore, score));
            highestScoreLabel.setFont(new Font(18));

            // Play again button
            Button playAgainButton = new Button("Restart");
            playAgainButton.setFont(new Font(14));
            playAgainButton.setOnAction(event -> {
                setHighestScore(Math.max(highestScore, score));

                restart();
                scene.setRoot(pane);
            });

            FlowPane gameOverPane = new FlowPane(Orientation.VERTICAL);
            gameOverPane.setPadding(new Insets(11, 12, 13, 14));
            gameOverPane.setHgap(5);
            gameOverPane.getChildren().addAll(gameOverLabel, scoreLabel, highestScoreLabel, playAgainButton);

            scene.setRoot(gameOverPane);

        } else {
            selectedBird.resetBird();
        }
    }

    public void killPig() {
        leftPigsCount--;

        if (leftPigsCount == 0) {
            nextLevel(FIRST_LEVEL_PIGS_COUNT + PIG_INCREASE_PER_LEVEL);
            setBirdsLeftCount(birdsLeftCount + BIRDS_INCREASE_PER_LEVEL);
        }

    }

    private void restart() {
        setBirdsLeftCount(INITIAL_BIRDS_LEFT);
        setScore(0);
        selectedBird.resetBird();

        nextLevel(FIRST_LEVEL_PIGS_COUNT);
    }

    private void nextLevel(int pigsCount) {
        leftPigsCount = pigsCount;
        levelCreator.nextLevel(leftPigsCount);
    }

    public int getBirdsLeftCount() {
        return birdsLeftCount;
    }

    public Bird getSelectedBird() {
        return selectedBird;
    }

    public Pane getPane() {
        return pane;
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Safely add entities at the end of the for loop. (So we don't get
     * ConcurrentModificationException) accounting for its order
     * 
     * Entities with higher order gets placed at the end of the list
     * which means they are rendered last (drawn on top of other entities) and
     * updated last.
     * 
     * If two entities have the same order, the recently added one gets higher order
     */
    public void addEntity(Entity entity, int order) {
        entity.setOrder(order);
        toAddEntities.add(entity);
    }

    public void addEntity(Entity entity) {
        addEntity(entity, 0);
    }

    /**
     * Safely remove entities at the end of the for loop. (So we don't get
     * ConcurrentModificationException)
     */
    public void removeEntity(Entity entity) {
        toRemoveEntities.add(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void increaseScore(int increase) {
        setScore(score + increase);
    }

}
