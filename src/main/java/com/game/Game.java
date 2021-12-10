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
    Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();
        canvas = new Canvas(1488, 624);
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
                    Thread.sleep(11);
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
        for (int i = 0; i < player.getBombs().size();) {
            player.getBombs().get(i).draw(render);
            if (!player.getBombs().get(i).isSurvival()) {
                player.getBombs().remove(i);
                continue;
            }
            i++;
        }
        player.draw(render);
        if (!player.isSurvival()) {
            map = new Map();
            player = new Player(map.getMapHash(), canvas);
            player.addEventListener(SCREEN);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
