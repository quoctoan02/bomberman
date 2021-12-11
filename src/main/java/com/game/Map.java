package com.game;

import com.game.enemy.Balloom;
import com.game.items.Item;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Scanner;

public class Map {
    private char[][] tileMap;
    private LinkedList<Image> mapImg = null;
    private LinkedList<Mob> enemy;
    private int life;
    private int point;
    private boolean win;
    private boolean lose;
    private int level;
    private final int limitLv = 1;
    private ObjectBlock portal;
    private LinkedList<Item> listItems;


    public Map() {
        level = 1;
        mapImg = new LinkedList<Image>();
        enemy = new LinkedList<>();
        portal = new ObjectBlock(0, 0, 0, 0);
        listItems = new LinkedList<>();
        loadMap(level);
        try {
            mapImg.add(new Image(getClass().getResource("/image/map/wall1.png").toURI().toString()));
            mapImg.add(new Image(getClass().getResource("/image/map/wall2.png").toURI().toString()));
            mapImg.add(new Image(getClass().getResource("/image/map/wall3.png").toURI().toString()));
            mapImg.add(new Image(getClass().getResource("/image/map/port.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        lose = false;
        win = false;
        point = 0;
        life = 3;
    }

    public void newMap() {
        enemy = new LinkedList<>();
        listItems = new LinkedList<>();
        lose = false;
        win = false;
        loadMap(level);
    }

    public void draw(GraphicsContext render) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                render.drawImage(mapImg.get(1), j * 48, i * 48, 48, 48);
            }
        }
        for (Item i : listItems) {
            i.draw(render);
        }

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                if (tileMap[i][j] == '#') {
                    render.drawImage(mapImg.get(0), j * 48, i * 48, 48, 48);
                }
                if (portal.equalLocation(j * 48 + 1, i * 48 + 1)) {
                    if (tileMap[i][j] == ' ') {
                        tileMap[i][j] = 'p';
                    }
                    render.drawImage(mapImg.get(3), j * 48, i * 48, 48, 48);
                }
                if (tileMap[i][j] == '*') {
                    render.drawImage(mapImg.get(2), j * 48, i * 48, 48, 48);
                }
            }
        }
    }

    private void loadMap(int level) {
        tileMap = new char[13][31];
        try {
            File file = new File(getClass().getResource("/map/map" + level + ".txt").toURI());
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < 13; i++) {
                String hash = (scanner.nextLine());
                for (int j = 0; j < 31; j++) {
                    tileMap[i][j] = hash.charAt(j);
                    if (tileMap[i][j] == '1') {
                        tileMap[i][j] = ' ';
                        enemy.add(new Balloom(j * 48, i * 48, this));
                    }
                    if (tileMap[i][j] == 'p') {
                        tileMap[i][j] = '*';
                        portal.update(j * 48 + 1, i * 48 + 1, 46, 46);
                    }

                    if (tileMap[i][j] == 'x') {
                        tileMap[i][j] = '*';
                        listItems.add(new Item(Item.COUNTUP, j * 48, i * 48));
                    }

                    if (tileMap[i][j] == 'y') {
                        tileMap[i][j] = '*';
                        listItems.add(new Item(Item.BOMBUP, j * 48, i * 48));
                    }

                    if (tileMap[i][j] == 'z') {
                        tileMap[i][j] = '*';
                        listItems.add(new Item(Item.SPEEDUP, j * 48, i * 48));
                    }
                }
            }
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    System.out.print(tileMap[i][j]);
                }
                System.out.println();
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Mob> getEnemy() {
        return enemy;
    }

    public char[][] getTileMap() {
        return tileMap;
    }

    public boolean isEndGame() {
        return level > limitLv;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
        if (life == 0) {
            lose = true;
        }
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ObjectBlock getPortal() {
        return portal;
    }

    public boolean canMoveToPortal() {
        return tileMap[(int) portal.getY() / 48][(int) portal.getX() / 48] == 'p';
    }

    public LinkedList<Item> getListItems() {
        return listItems;
    }
}
