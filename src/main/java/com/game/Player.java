package com.game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;

public class Player extends Mob {
    public static LinkedList<Image> playerImg = null;
    private HashMap<KeyCode, Double> key;
    private double speed = 2;
    private boolean canMoveL;
    private boolean canMoveR;
    private boolean canMoveT;
    private boolean canMoveB;
    private char[][] mapHash;
    private Canvas camera;

    public Player(char[][] mapHash, Canvas camera) {
        playerImg = new LinkedList<Image>();
        try {
            playerImg.add(new Image(getClass().getResource("/assets/classic.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        setX(50);
        setY(50);
        key = new HashMap<>();
        key.put(KeyCode.D, 0.0);
        key.put(KeyCode.W, 0.0);
        key.put(KeyCode.A, 0.0);
        key.put(KeyCode.S, 0.0);
        canMoveT = true;
        canMoveR = true;
        canMoveB = true;
        canMoveL = true;
        this.mapHash = mapHash;
        this.camera = camera;
    }



    @Override
    public void draw(GraphicsContext render) {
        render.drawImage(playerImg.get(0),16 ,0 , 12, 16, getX(), getY(), 36, 48);
        checkMove();
        if (canMoveR) {
            setX(getX() + key.get(KeyCode.D));
        }
        if (canMoveL) {
            setX(getX() - key.get(KeyCode.A));
        }
        if (canMoveB) {
            setY(getY() + key.get(KeyCode.S));
        }
        if (canMoveT) {
            setY(getY() - key.get(KeyCode.W));
        }
        moveWindow();
    }

    private void moveWindow() {
        if (getX() + 18 > Game.SCREEN.getWidth() / 2 && getX() + 18 < camera.getWidth()) {
            camera.setLayoutX(- getX() - 18 + Game.SCREEN.getWidth() / 2);
        } else if (getX() + 18 < Game.SCREEN.getWidth() / 2) {
            camera.setLayoutX(0);
        } else {
            camera.setLayoutX(Game.SCREEN.getWidth() - camera.getWidth());
        }
    }

    void addEventListener(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {
            key.put(e.getCode(), speed);
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (e) -> {
            key.put(e.getCode(), 0.0);
        });
    }

    private void checkMove() {
        canMoveR = checkCanMove(getX() + speed, getY());
        canMoveL = checkCanMove(getX() - speed, getY());
        canMoveT = checkCanMove(getX(), getY() - speed);
        canMoveB = checkCanMove(getX(), getY() + speed);
    }

    private boolean checkCanMove(double x, double y) {
        int i1 = (int) (y + 4) / 48;
        int j1 = (int) (x + 2) / 48;

        int i2 = (int) (y + 4) / 48;
        int j2 = (int) (x + 36) / 48;

        int i3 = (int) (y + 46) / 48;
        int j3 = (int) (x + 2) / 48;

        int i4 = (int) (y + 46) / 48;
        int j4 = (int) (x + 36) / 48;

        return mapHash[i1][j1] == ' ' && mapHash[i2][j2] == ' '
                && mapHash[i3][j3] == ' ' && mapHash[i4][j4] == ' ';
    }

}
