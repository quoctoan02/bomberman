package com.game.text;

import javafx.scene.text.Font;

import java.net.URISyntaxException;

public class FontGame {
    public static Font MC;
    public static Font MCHIGHER;

    public static void load() {
        try {
            MC = Font.loadFont(
                FontGame.class.getResource("/font/mc.ttf").toURI().toString(), 18
            );
            MCHIGHER = Font.loadFont(
                    FontGame.class.getResource("/font/mc.ttf").toURI().toString(), 25
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
