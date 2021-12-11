package com.game.text;


import com.game.Map;
import com.game.dialog.Control;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class StatusBar extends Control {
    private Map map;
    private int time;
    private int timing;
    private Text text;
    GraphicsContext render;

    public StatusBar(Canvas camera, Map map) {
        setCamera(camera);
        setX(0);
        setY(0);
        this.map = map;
        time = 200;
        timing = 0;
        text = new Text(map.getPoint() + "");
        text.setFont(FontGame.MC);
        render = camera.getGraphicsContext2D();
    }

    public void draw() {
        render.clearRect(0, 0, 748, 48);
        render.setFill(Color.BLACK);
        render.fillRect(0, 0, 748, 48);
        render.setFill(Color.WHITE);
        render.setFont(FontGame.MC);
        render.setTextAlign(TextAlignment.LEFT);
        render.setTextBaseline(VPos.TOP);
        render.fillText("LIFE: " + map.getLife(), getX() + 48, getY() + 8);
        text.setText(map.getPoint() + "");
        render.fillText(map.getPoint() + "",
                getX() + 374 - text.getBoundsInLocal().getWidth() / 2, getY() + 8);
        render.fillText("TIME: " + time, getX() + 620, getY() + 8);
        runTime();
    }

    private void runTime() {
        timing ++;
        if (timing >= 90) {
            time --;
            timing = 0;
        }
    }

    public boolean isEndTime() {
        return time <= 0;
    }

}
