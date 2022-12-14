# Angry birds clone

An angry birds clone using [JavaFX](https://openjfx.io/) framework.

![UML](./public/screenshot1.png)
![UML](./public/screenshot2.png)

## Overview

In this project, we make a very simplified version of the popular “Angry birds game”

The game includes the following features:

- Throw the bird with the mouse
  - The bird gets a linear velocity in which its vertical component is affected by gravity. That’s what make the curve in which the bird moves
  - Dragging the bird far away makes it stick on a circle. This is achieved by putting the bird on the intersection of a circle and the line from the center of the circle to the bird position
- Change the bird to your favorite angry bid: “Red”, “Chuck” or “Bomb”
- Levels are created randomly and consist of two main building blocks: “Obstacle” and “Pig”
- Track score and high score. Once all pigs are dead, a new level is created with a random configuration where the number of pigs is more than the previous level. The number of birds is also increased with every level
- Game over screen shows when the game ends. It shows both score and high score. The game ends when you no longer have birds and there are still more pigs in the level.

## UML

![UML](./public/uml.jpeg)

## How to run the game on your PC

To run the game

1. Install Extension Pack for Java as a vs code extension
2. Follow the instructions on the vs code docs [here](https://code.visualstudio.com/docs/java/java-gui#_develop-javafx-applications)
