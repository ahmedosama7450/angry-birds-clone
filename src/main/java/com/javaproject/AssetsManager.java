package com.javaproject;

import java.util.HashMap;
import javafx.scene.image.Image;

public class AssetsManager {
    public static final AssetsManager instance = new AssetsManager();

    private HashMap<String, Image> imagesMap = new HashMap<String, Image>();

    private AssetsManager() {

        String[] imagesNames = {
                "bg",
                "obstacle",
                "obstacle-vertical",
                "slingshot",
                "spark",

                "red",
                "red-flying",

                "chuck",
                "chuck-flying",

                "terence",
                "terence-flying",

                "pig",
                "pig2",
                "pig3",
                "pig-hurt",

                "helmet-pig",
                "helmet-pig2",
                "helmet-pig3",
                "helmet-pig-hurt",

                "bomb-black1",
                "bomb-black2",
                "bomb-black3",
                "bomb-black4",

                "bomb-white1",
                "bomb-white2",
                "bomb-white3",
                "bomb-white4",

        };

        for (String imageName : imagesNames) {
            imagesMap.put(imageName, new Image(getClass().getResourceAsStream(imageName + ".png")));
        }

    }

    public Image getImage(String name) {
        return imagesMap.get(name);
    }

}

/*
 * Image bgImage = new Image(getClass().getResourceAsStream("bg.png"));
 * Image obstacleImage = new
 * Image(getClass().getResourceAsStream("obstacle.png"));
 * Image slingshotImage = new
 * Image(getClass().getResourceAsStream("slingshot.png"));
 * Image sparkImage = new Image(getClass().getResourceAsStream("spark.png"));
 * 
 * // Birds
 * Image redImage = new Image(getClass().getResourceAsStream("red.png"));
 * Image redFlyingImage = new
 * Image(getClass().getResourceAsStream("red-flying.png"));
 * 
 * Image chuckImage = new Image(getClass().getResourceAsStream("chuck.png"));
 * Image chuckFlyingImage = new
 * Image(getClass().getResourceAsStream("chuck-flying.png"));
 * 
 * Image terenceImage = new
 * Image(getClass().getResourceAsStream("terence.png"));
 * Image terenceFlyingImage = new
 * Image(getClass().getResourceAsStream("terence-flying.png"));
 * 
 * // Pigs
 * Image pigImage = new Image(getClass().getResourceAsStream("pig.png"));
 * Image pig2Image = new Image(getClass().getResourceAsStream("pig2.png"));
 * Image pig3Image = new Image(getClass().getResourceAsStream("pig3.png"));
 * Image pigHurtImage = new
 * Image(getClass().getResourceAsStream("pig-hurt.png"));
 * 
 * Image bigPigImage = new
 * Image(getClass().getResourceAsStream("helmet-pig.png"));
 * Image bigPig2Image = new
 * Image(getClass().getResourceAsStream("helmet-pig2.png"));
 * Image bigPig3Image = new
 * Image(getClass().getResourceAsStream("helmet-pig3.png"));
 * Image bigPigHurtImage = new
 * Image(getClass().getResourceAsStream("helmet-pig-hurt.png"));
 * 
 * // Bombs
 * Image bombBlack1 = new
 * Image(getClass().getResourceAsStream("bomb-black1.png"));
 * Image bombBlack2 = new
 * Image(getClass().getResourceAsStream("bomb-black2.png"));
 * Image bombBlack3 = new
 * Image(getClass().getResourceAsStream("bomb-black3.png"));
 * Image bombBlack4 = new
 * Image(getClass().getResourceAsStream("bomb-black4.png"));
 * 
 * Image bombWhite1 = new
 * Image(getClass().getResourceAsStream("bomb-white1.png"));
 * Image bombWhite2 = new
 * Image(getClass().getResourceAsStream("bomb-white2.png"));
 * Image bombWhite3 = new
 * Image(getClass().getResourceAsStream("bomb-white3.png"));
 * Image bombWhite4 = new
 * Image(getClass().getResourceAsStream("bomb-white4.png"));
 */