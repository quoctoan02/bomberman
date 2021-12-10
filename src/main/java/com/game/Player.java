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
    private final HashMap<KeyCode, Double> key;
    private final double speed = 2;
    private boolean canMoveL;
    private boolean canMoveR;
    private boolean canMoveT;
    private boolean canMoveB;
    private final char[][] mapHash;
    private final Canvas camera;
    private final LinkedList<Bomb> bombs;
    private final ObjectBlock objectBlock;
    private int timeAni;
    private int status;

    public Player(char[][] mapHash, Canvas camera) {
        playerImg = new LinkedList<>();
        try {
            playerImg.add(new Image(getClass().getResource("/assets/classic.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        setX(50);
        setY(40);
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
        bombs = new LinkedList<>();
        objectBlock = new ObjectBlock(0, 0, 0, 0);
        timeAni = 0;
        status = 0;
        setKilling(false);
        super.setSurvival(true);
    }



    @Override
    public void draw(GraphicsContext render) {
        if (!isKilling()){
            objectBlock.update(getX() + 1, getY() + 12, 34, 34);

            render.drawImage(playerImg.get(0), 16, 0, 12, 16, getX(), getY(), 36, 48);
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
        } else {
            timeAni ++;
            if (timeAni > 180) {
                super.setSurvival(false);
            }
        }
    }

    private void moveWindow() {
        if (getX() + 18 > Game.SCREEN.getWidth() / 2
                && getX() + 18 < camera.getWidth() - Game.SCREEN.getWidth() / 2) {
            camera.setLayoutX(- getX() - 18 + Game.SCREEN.getWidth() / 2);
        } else if (getX() + 18 <= Game.SCREEN.getWidth() / 2) {
            camera.setLayoutX(0);
        } else {
            camera.setLayoutX(Game.SCREEN.getWidth() - camera.getWidth());
        }
    }

    void addEventListener(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {
            if (e.getCode().equals(KeyCode.D)
                    || e.getCode().equals(KeyCode.A)
                    || e.getCode().equals(KeyCode.W)
                    || e.getCode().equals(KeyCode.S)) {
                key.put(e.getCode(), speed);
            } else {
                if (e.getCode().equals(KeyCode.SPACE)) {
                    int i = (int) (getY() + 24)/ 48;
                    int j = (int) (getX() + 18) / 48;
                    if (mapHash[i][j] == ' ') {
                        System.out.println("bomb");
                        Bomb bomb = new Bomb(j * 48, i * 48, mapHash);
                        bomb.setPlayer(this);
                        bombs.add(bomb);
                    }
                }
            }
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (e) -> {
            if (e.getCode().equals(KeyCode.D)
                    || e.getCode().equals(KeyCode.A)
                    || e.getCode().equals(KeyCode.W)
                    || e.getCode().equals(KeyCode.S)) {
                key.put(e.getCode(), 0.0);
            }
        });
    }

    private void checkMove() {
        canMoveR = checkPlayerMove(getX() + speed, getY());
        canMoveL = checkPlayerMove(getX() - speed, getY());
        canMoveT = checkPlayerMove(getX(), getY() - speed);
        canMoveB = checkPlayerMove(getX(), getY() + speed);
    }

    private boolean checkPlayerMove(double x, double y) {
        int i1 = (int) (y + 12) / 48;
        int j1 = (int) (x + 1) / 48;
        int i2 = (int) (y + 12) / 48;
        int j2 = (int) (x + 34) / 48;

        int i3 = (int) (y + 46) / 48;
        int j3 = (int) (x + 1) / 48;

        int i4 = (int) (y + 46) / 48;
        int j4 = (int) (x + 34) / 48;

        return checkMap(mapHash[i1][j1]) && checkMap(mapHash[i2][j2])
                && checkMap(mapHash[i3][j3]) && checkMap(mapHash[i4][j4]);
    }

    private boolean checkMap(char maphash) {
        return maphash == ' ' || maphash == 'b';
    }

    public LinkedList<Bomb> getBombs() {
        return bombs;
    }

    public ObjectBlock getObjectBlock() {
        return objectBlock;
    }

    @Override
    public void setSurvival(boolean survival) {
        if (!isKilling()) {
            timeAni = 0;
            status = 0;
            setKilling(!survival);
        }
    }
}
