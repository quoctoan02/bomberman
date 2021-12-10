package com.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Scanner;

public class Map {
    private char[][] mapHash;
    private LinkedList<Image> mapImg = null;

    public Map() {
        loadMap();
        mapImg = new LinkedList<Image>();
        try {
            mapImg.add(new Image(getClass().getResource("/assets/map/wall1.png").toURI().toString()));
            mapImg.add(new Image(getClass().getResource("/assets/map/wall2.png").toURI().toString()));
            mapImg.add(new Image(getClass().getResource("/assets/map/wall3.png").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext render) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                render.drawImage(mapImg.get(1), j * 48, i * 48, 48, 48);
                if (mapHash[i][j] == '#') {
                    render.drawImage(mapImg.get(0), j * 48, i * 48, 48, 48);
                }
                if (mapHash[i][j] == '*') {
                    render.drawImage(mapImg.get(2), j * 48, i * 48, 48, 48);
                }
            }
        }
    }

    private void loadMap() {
        mapHash = new char[13][31];
        try {
            File file = new File(getClass().getResource("/map/map1.txt").toURI());
            Scanner scanner = new Scanner(file);
            for (int i = 0; i < 13; i++) {
                String hash = (scanner.nextLine());
                for (int j = 0; j < 31; j++) {
                    mapHash[i][j] = hash.charAt(j);
                }
            }
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    System.out.print(mapHash[i][j]);
                }
                System.out.println();
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public char[][] getMapHash() {
        return mapHash;
    }
}
