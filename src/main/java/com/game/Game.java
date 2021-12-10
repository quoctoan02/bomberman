package com.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


public class Game extends Application {
    GraphicsContext render;
    Player player;
    Map map;
    public static Scene SCREEN;

    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();
        Canvas canvas = new Canvas(1488, 624);
        render = canvas.getGraphicsContext2D();
        render.setImageSmoothing(false);
        group.getChildren().add(canvas);
        Scene scene = new Scene(group, 748, 624);
        SCREEN = scene;
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        addLoop();
        map = new Map();
        player = new Player(map.getMapHash(), canvas);
        player.addEventListener(scene);
    }

    void addLoop() {
        (new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                update();
            }
        }).start();
    }

    void update() {
        render.clearRect(0, 0, 1000, 1000);
        map.draw(render);
        player.draw(render);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
