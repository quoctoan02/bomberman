package com.game;

import com.game.items.Item;
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
    public static int BOMBNUMBER = 1;
    public static double SPEED = 2;
    private final HashMap<KeyCode, Double> key;
    private boolean canMoveL;
    private boolean canMoveR;
    private boolean canMoveT;
    private boolean canMoveB;
    private final Map map;
    private final Canvas camera;
    private final LinkedList<Bomb> bombs;
    private int timeAni;
    private int status;
    private Scene screen;


    public Player(Map map, Canvas camera) {
        playerImg = new LinkedList<>();
        try {
            playerImg.add(new Image(getClass().getResource("/image/classic.png").toURI().toString()));
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
        this.map = map;
        this.camera = camera;
        bombs = new LinkedList<>();
        setObjectBlock(new ObjectBlock(0, 0, 0, 0));
        timeAni = 0;
        status = 0;
        setKilling(false);
        super.setSurvival(true);
    }



    @Override
    public void draw(GraphicsContext render) {
        if (!isKilling()){
            getObjectBlock().update(getX() + 1, getY() + 12, 34, 34);

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
        if (getX() + 18 > screen.getWidth() / 2
                && getX() + 18 < camera.getWidth() - screen.getWidth() / 2) {
            camera.setLayoutX(- getX() - 18 + screen.getWidth() / 2);
        } else if (getX() + 18 <= screen.getWidth() / 2) {
            camera.setLayoutX(0);
        } else {
            camera.setLayoutX(screen.getWidth() - camera.getWidth());
        }
    }

    void addEventListener(Scene scene) {
        screen = scene;
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> {
            if (e.getCode().equals(KeyCode.D)
                    || e.getCode().equals(KeyCode.A)
                    || e.getCode().equals(KeyCode.W)
                    || e.getCode().equals(KeyCode.S)) {
                key.put(e.getCode(), SPEED);
            } else {
                if (e.getCode().equals(KeyCode.SPACE) && bombs.size() < BOMBNUMBER) {
                    int i = (int) (getY() + 24)/ 48;
                    int j = (int) (getX() + 18) / 48;
                    if (map.getTileMap()[i][j] == ' ') {
                        System.out.println("bomb");
                        Bomb bomb = new Bomb(j * 48, i * 48, map.getTileMap());
                        bomb.setEnemys(map.getEnemy());
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
        for (Mob i : map.getEnemy()) {
            if (i.getObjectBlock().insideBlock(getObjectBlock())) {
                setSurvival(false);
                map.setLife(map.getLife() - 1);
                break;
            }
        }
        for (int i = 0; i < map.getListItems().size();) {
            if (map.getListItems().get(i).getObjectBlock().insideBlock(getObjectBlock())) {
                if (map.getListItems().get(i).getEffect() == Item.COUNTUP) {
                    Bomb.BOMBCOUNT ++;
                }
                if (map.getListItems().get(i).getEffect() == Item.BOMBUP) {
                    BOMBNUMBER ++;
                }
                if (map.getListItems().get(i).getEffect() == Item.SPEEDUP) {
                    SPEED += 0.5;
                }
                map.getListItems().remove(i);
                continue;
            }
            i++;
        }
        canMoveR = checkPlayerMove(getX() + SPEED, getY());
        canMoveL = checkPlayerMove(getX() - SPEED, getY());
        canMoveT = checkPlayerMove(getX(), getY() - SPEED);
        canMoveB = checkPlayerMove(getX(), getY() + SPEED);
    }

    private boolean checkPlayerMove(double x, double y) {
        int mobi1 = (int) (y + 12) / 48;
        int mobj1 = (int) (x + 1) / 48;
        int mobi2 = (int) (y + 12) / 48;
        int mobj2 = (int) (x + 34) / 48;

        int mobi3 = (int) (y + 46) / 48;
        int mobj3 = (int) (x + 1) / 48;

        int mobi4 = (int) (y + 46) / 48;
        int mobj4 = (int) (x + 34) / 48;

        return checkMap(map.getTileMap()[mobi1][mobj1]) && checkMap(map.getTileMap()[mobi2][mobj2])
                && checkMap(map.getTileMap()[mobi3][mobj3]) && checkMap(map.getTileMap()[mobi4][mobj4]);
    }

    private boolean checkMap(char maphash) {
        return maphash == ' ' || maphash == 'b'
                || (map.getEnemy().size() == 0 && maphash == 'p');
    }

    public LinkedList<Bomb> getBombs() {
        return bombs;
    }

    @Override
    public void setSurvival(boolean survival) {
        if (!isKilling()) {
            timeAni = 0;
            status = 0;
            setKilling(!survival);
        }
    }

    public boolean isWinThisLevel() {
        return map.getEnemy().size() == 0
                && map.getPortal().insideBlock(getObjectBlock()) && map.canMoveToPortal();
    }
}
