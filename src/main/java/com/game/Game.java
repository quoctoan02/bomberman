package com.game;

import com.game.dialog.DiaLogGame;
import com.game.text.FontGame;
import com.game.text.StatusBar;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.LinkedList;


public class Game extends Application {
    private GraphicsContext render;
    private Player player;
    private Map map;
    private Canvas canvas;
    private Stage stage;
    private StatusBar statusBar;
    private DiaLogGame diaLogGame;

    @Override
    public void start(Stage primaryStage) {
        FontGame.load();
        Group group = new Group();
        canvas = new Canvas(1488, 624);
        render = canvas.getGraphicsContext2D();
        render.setImageSmoothing(false);
        group.getChildren().add(canvas);
        Scene scene = new Scene(group, 748, 624);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        stage = primaryStage;
        addLoop();
        map = new Map();
        player = new Player(map, canvas);
        player.addEventListener(scene);
        statusBar = new StatusBar(canvas, map);
        diaLogGame = new DiaLogGame(canvas,"LEVEL 1");
        diaLogGame.show();
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
        if (diaLogGame.isShowing()) {
            diaLogGame.draw(render);
            return;
        }
        map.draw(render);
        drawEnemy(player.getBombs());
        drawEnemy(map.getEnemy());
        player.draw(render);
        statusBar.draw(render);

        //xử lý khi thua
        if (map.isLose()) {
            diaLogGame.setText("LOSE");
            diaLogGame.setOnEndOfDialog(() -> {
                stage.close();
            });
            diaLogGame.show();
        }

        if (player.isWinThisLevel()) {
            map.setLevel(map.getLevel() + 1);
            if (map.isEndGame()) {
                diaLogGame.setText("WIN");
                diaLogGame.setOnEndOfDialog(() -> {
                    stage.close();
                });
                diaLogGame.show();
                return;
            }
            map.newMap();
            player = new Player(map, canvas);
            player.addEventListener(stage.getScene());
            statusBar = new StatusBar(canvas, map);
            diaLogGame.setText("LEVEL: " + map.getLevel());
            diaLogGame.show();
        }

        //xử lý khi mất mạng
        if (!player.isSurvival()) {
            map.newMap();
            player = new Player(map, canvas);
            player.addEventListener(stage.getScene());
            statusBar = new StatusBar(canvas, map);
            diaLogGame.setText("LEVEL: " + map.getLevel());
            diaLogGame.show();
        }
    }

    private <T> void drawEnemy(LinkedList<T> list) {
        for (int i = 0; i < list.size();) {
            ((Mob) list.get(i)).draw(render);
            if (!((Mob) list.get(i)).isSurvival()) {
                list.remove(i);
                continue;
            }
            i++;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
