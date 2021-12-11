package com.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class Bomb extends Mob{
    public static LinkedList<Image> bombImg = null;
    public static LinkedList<Image> boomImg = null;
    public static int BOMBCOUNT = 1;
    private char[][] mapHash;
    private Player player;
    private LinkedList<Mob> listEnemy;
    private boolean playerOut;
    private int timeAni;
    private int status;
    private boolean boom;
    private int countR;
    private int countL;
    private int countT;
    private int countB;
    private boolean checkBomb;


    public Bomb(int x, int y, char[][] maphash) {
        if (bombImg == null) {
            bombImg = new LinkedList<>();
            boomImg = new LinkedList<>();
            try {
                bombImg.add(new Image(getClass()
                        .getResource("/image/bomb/bomb3.png").toURI().toString())
                );
                bombImg.add(new Image(getClass()
                        .getResource("/image/bomb/bomb2.png").toURI().toString())
                );
                bombImg.add(new Image(getClass()
                        .getResource("/image/bomb/bomb1.png").toURI().toString())
                );
                for (int i = 1; i <= 24; i++) {
                    boomImg.add(new Image(getClass()
                            .getResource("/image/bomb/boom" + i + ".png").toURI().toString()));
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        timeAni = 0;
        status = 0;
        setX(x);
        setY(y);
        this.mapHash = maphash;
        setObjectBlock(new ObjectBlock(0, 0, 0, 0));
        playerOut = false;
        boom = false;
        setSurvival(true);
        checkBomb = false;
        listEnemy = null;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void draw(GraphicsContext render) {
        int i = (int) getY() / 48;
        int j = (int) getX() / 48;
        if (!boom){
            getObjectBlock().update(getX(), getY(), 48, 48);
            render.drawImage(bombImg.get(status), getX(), getY(), 48, 48);
            checkPlayerOut(i, j);
            time();
        } else {
            drawBoom(render);
            timeAni++;
            if (timeAni % 30 == 0 && status == 0) {
                status++;
            } else if (timeAni % 10 == 0 && status != 0) {
                status++;
            }
            if (status >= 3) {
                status = 0;
            }
            if (timeAni >= 50) {
                setSurvival(false);
                mapHash[i][j] = ' ';
            }
        }
    }

    private void time() {
        timeAni++;
        if (timeAni % 20 == 0) {
            status++;
        }
        if (status >= 3) {
            status = 0;
        }
        if (timeAni >= 200) {
            setBoom();
        }
    }

    private void drawBoom(GraphicsContext render) {
        render.drawImage(boomImg.get(2 - status), getX(), getY(), 48, 48);
        drawRight(render);
    }

    private void checkInBoom(ObjectBlock obj) {
        if (obj.insideBlock(player.getObjectBlock())) {
            player.setSurvival(false);
        }

        if (!checkBomb) {
            for (Bomb i : player.getBombs()) {
                if (i.getObjectBlock().insideBlock(obj)) {
                    i.setBoom();
                }
            }
        }

        if (listEnemy != null) {
            for (Mob i : listEnemy) {
                if (i.getObjectBlock().insideBlock(obj)) {
                    i.setSurvival(false);
                }
            }
        }
    }

    private void drawRight(GraphicsContext render) {
        for (int k = 1; k <= countR; k++) {
            int j = (int) (getX() + k * 48) / 48;
            int i = (int) getY() / 48;
            if (k == countR) {
                if (mapHash[i][j] == 'd') {
                    render.drawImage(boomImg.get(21 + status), getX() + k * 48, getY(), 48, 48);
                    if (timeAni + 1 >= 50) {
                        mapHash[i][j] = ' ';
                    }
                } else {
                    render.drawImage(boomImg.get(20 - status), getX() + k * 48, getY(), 48, 48);
                }
            } else {
                render.drawImage(boomImg.get(17 - status), getX() + k * 48, getY(), 48, 48);
            }
            checkInBoom(new ObjectBlock(getX() + 1 + k * 48, getY() + 1, 46, 46));
        }

        for (int k = 1; k <= countL; k++) {
            int j = (int) (getX() - k * 48) / 48;
            int i = (int) getY() / 48;
            if (k == countL) {
                if (mapHash[i][j] == 'd') {
                    render.drawImage(boomImg.get(21 + status), getX() - k * 48, getY(), 48, 48);
                    if (timeAni + 1 >= 50) {
                        mapHash[i][j] = ' ';
                    }
                } else {
                    render.drawImage(boomImg.get(14 - status), getX() - k * 48, getY(), 48, 48);
                }
            } else {
                render.drawImage(boomImg.get(17 - status), getX() - k * 48, getY(), 48, 48);
            }
            checkInBoom(new ObjectBlock(getX() + 1 - k * 48, getY() + 1, 46, 46));
        }

        for (int k = 1; k <= countT; k++) {
            int i = (int) (getY() - k * 48) / 48;
            int j = (int) getX() / 48;
            if (k == countT) {
                if (mapHash[i][j] == 'd') {
                    render.drawImage(boomImg.get(21 + status), getX(), getY() - k * 48, 48, 48);
                    if (timeAni + 1 >= 50) {
                        mapHash[i][j] = ' ';
                    }
                } else {
                    render.drawImage(boomImg.get(5 - status), getX(), getY() - k * 48, 48, 48);
                }
            } else {
                render.drawImage(boomImg.get(8 - status), getX(), getY() - k * 48, 48, 48);
            }
            checkInBoom(new ObjectBlock(getX() + 1, getY() + 1 - k * 48, 46, 46));
        }

        for (int k = 1; k <= countB; k++) {
            int i = (int) (getY() + k * 48) / 48;
            int j = (int) getX() / 48;
            if (k == countB) {
                if (mapHash[i][j] == 'd') {
                    render.drawImage(boomImg.get(21 + status), getX(), getY() + k * 48, 48, 48);
                    if (timeAni + 1 >= 50) {
                        mapHash[i][j] = ' ';
                    }
                } else {
                    render.drawImage(boomImg.get(11 - status), getX(), getY() + k * 48, 48, 48);
                }
            } else {
                render.drawImage(boomImg.get(8 - status), getX(), getY() + k * 48, 48, 48);
            }
            checkInBoom(new ObjectBlock(getX() + 1, getY() + 1 + k * 48, 46, 46));
        }

    }

    private void getBombCount() {
        countR = 0;
        countB = 0;
        countL = 0;
        countT = 0;

        //check right
        for (int k = 1; k <= BOMBCOUNT; countR = k, k ++) {
            int j = (int) (getX() + k * 48) / 48;
            int i = (int) getY() / 48;
            System.out.println(i + " " + j);
            if (mapHash[i][j] == '#' || mapHash[i][j] == 'd') {
                break;
            }
            if (mapHash[i][j] == '*') {
                countR = k;
                mapHash[i][j] = 'd';
                break;
            }
        }

        //chekc left
        for (int k = 1; k <= BOMBCOUNT; countL = k, k ++) {
            int j = (int) (getX() - k * 48) / 48;
            int i = (int) getY() / 48;
            if (mapHash[i][j] == '#' || mapHash[i][j] == 'd') {
                break;
            }
            if (mapHash[i][j] == '*') {
                countL = k;
                mapHash[i][j] = 'd';
                break;
            }
        }

        //chekc top
        for (int k = 1; k <= BOMBCOUNT; countT = k, k ++) {
            int i = (int) (getY() - k * 48) / 48;
            int j = (int) getX() / 48;
            if (mapHash[i][j] == '#' || mapHash[i][j] == 'd') {
                break;
            }
            if (mapHash[i][j] == '*') {
                countT = k;
                mapHash[i][j] = 'd';
                break;
            }
        }

        //chekc top
        for (int k = 1; k <= BOMBCOUNT; countB = k, k ++) {
            int i = (int) (getY() + k * 48) / 48;
            int j = (int) getX() / 48;
            if (mapHash[i][j] == '#' || mapHash[i][j] == 'd') {
                break;
            }
            if (mapHash[i][j] == '*') {
                countB = k;
                mapHash[i][j] = 'd';
                break;
            }
        }
    }

    private void checkPlayerOut(int i, int j) {
        if (!playerOut) {
            mapHash[i][j] = 'b';
            if (!getObjectBlock().insideBlock(player.getObjectBlock())) {
                playerOut = true;
            }
        }
        if (playerOut) {
            mapHash[i][j] = 'B';
        }
    }

    public void setBoom() {
        if (!boom) {
            boom = true;
            timeAni = 0;
            status = 0;
            getBombCount();
        }
    }

    public void setEnemys(LinkedList<Mob> enemy) {
        this.listEnemy = enemy;
    }
}
