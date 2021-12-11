package com.game.enemy;

import com.game.Map;
import com.game.Mob;
import com.game.ObjectBlock;
import com.game.text.FontGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Random;

public class Doll extends Mob {
    private static LinkedList<Image> balloomImg = null;
    private int timeAni;
    private int statusAni;
    private int status;
    private final int left = 0;
    private final int right = 3;
    private boolean canGoL;
    private boolean canGoR;
    private boolean canGoT;
    private boolean canGoB;
    private Map map;
    public static double SPEED = 2;
    private double speedH;
    private double speedV;

    public Doll(int x, int y, Map map) {
        if (balloomImg == null) {
            balloomImg = new LinkedList<>();
            try {
                for (int i = 1; i <= 10; i++) {
                    balloomImg.add(new Image(getClass()
                            .getResource("/image/mobs/doll/" + i + ".png").toURI().toString()));
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        timeAni = 0;
        statusAni = 0;
        speedH = 0;
        speedV = 0;
        status = left;
        setX(x);
        setY(y);
        super.setSurvival(true);
        setKilling(false);
        setObjectBlock(new ObjectBlock(0, 0, 0, 0));
        this.map = map;
    }

    @Override
    public void draw(GraphicsContext render) {
        if (!isKilling()) {
            getObjectBlock().update(getX() + 1, getY() + 1, 46, 46);
            moveEnemy();
            render.drawImage(balloomImg.get(status + statusAni), getX(), getY(), 48, 48);
            time();
        } else {
            render.drawImage(balloomImg.get(6 + statusAni), getX(), getY(), 48, 48);
            render.setFill(Color.WHITE);
            render.setFont(FontGame.MC);
            render.fillText("+ 200", getX() + 50, getY() + 24 - timeAni / 5.0);
            timeAni++;
            if (timeAni % 40 == 0 && statusAni == 0) {
                statusAni++;
            } else if (timeAni % 20 == 0 && statusAni != 0) {
                statusAni++;
            }
            if (statusAni >= 4) {
                statusAni = 0;
            }
            if (timeAni >= 100) {
                super.setSurvival(false);
            }
        }
    }

    private void time() {
        timeAni++;
        if (timeAni % 20 == 0) {
            statusAni++;
            timeAni = 0;
        }
        if (statusAni >= 3) {
            statusAni = 0;
        }
    }

    public void moveEnemy() {
        int i = (int) getY() / 48;
        int j = (int) getX() / 48;
        if (i * 48 == getY() && j * 48 == getX()) {
            canGoR = checkEnemyMove(getX() + 24, getY());
            canGoL = checkEnemyMove(getX() - 24, getY());
            canGoT = checkEnemyMove(getX(), getY() - 24);
            canGoB = checkEnemyMove(getX(), getY() + 24);
            speedH = 0;
            speedV = 0;
            LinkedList<Integer> check = new LinkedList<>();
            if (canGoR) {
                check.add(1);
            }
            if (canGoL) {
                check.add(2);
            }
            if (canGoT) {
                check.add(3);
            }
            if (canGoB) {
                check.add(4);
            }
            Random random = new Random();
            int getWay = check.get(random.nextInt(check.size()));
            if (getWay == 1) {
                speedH = SPEED;
            }
            if (getWay == 2) {
                speedH = - SPEED;
            }
            if (getWay == 3) {
                speedV = - SPEED;
            }
            if (getWay == 4) {
                speedV = SPEED;
            }
        }
        setX(getX() + speedH);
        setY(getY() + speedV);
    }

    private boolean checkEnemyMove(double x, double y) {
        int mobi1 = (int) (y + 1) / 48;
        int mobj1 = (int) (x + 1) / 48;

        int mobi2 = (int) (y + 1) / 48;
        int mobj2 = (int) (x + 46) / 48;

        int mobi3 = (int) (y + 46) / 48;
        int mobj3 = (int) (x + 1) / 48;

        int mobi4 = (int) (y + 46) / 48;
        int mobj4 = (int) (x + 46) / 48;

        return checkMap(map.getTileMap()[mobi1][mobj1]) && checkMap(map.getTileMap()[mobi2][mobj2])
                && checkMap(map.getTileMap()[mobi3][mobj3]) && checkMap(map.getTileMap()[mobi4][mobj4]);
    }

    private boolean checkMap(char maphash) {
        return maphash == ' ';
    }

    @Override
    public void setSurvival(boolean survival) {
        if (!isKilling()) {
            timeAni = 0;
            statusAni = 0;
            map.setPoint(map.getPoint() + 200);
            setKilling(!survival);
        }
    }
}
