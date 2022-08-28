package com.javaproject;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

        public static final double SCENE_WIDTH = 1200;
        public static final double SCENE_HEIGHT = 540;

        @Override
        public void start(Stage stage) throws IOException {
                stage.setScene(new GameManager().getScene());
                stage.setTitle("Angry Birds");
                stage.setResizable(false);
                stage.show();

        }

        public static void main(String[] args) {
                launch();
        }

}
