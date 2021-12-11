package com.game.dialog;

import com.game.text.FontGame;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DiaLogGame extends Control {
    private Text text;
    private boolean showing;
    private int time;
    private Runnable runnable;

    public DiaLogGame(Canvas camera, String text) {
        setCamera(camera);
        setX(0);
        setY(0);
        this.text = new Text(text);
        this.text.setFont(FontGame.MCHIGHER);
        showing = false;
        runnable = () -> {};
    }

    @Override
    public void draw(GraphicsContext render) {
        render.setFill(Color.BLACK);
        render.fillRect(getX(), getY(), 1000, 1000);
        render.setFill(Color.WHITE);
        render.setFont(FontGame.MCHIGHER);
        render.fillText(text.getText(),
                getX() + 374 - text.getBoundsInLocal().getWidth() / 2,
                getY() + 312 - text.getBoundsInLocal().getHeight() / 2);
        time ++;
        if (time > 240) {
            showing = false;
            runnable.run();
        }
    }

    public void setOnEndOfDialog(Runnable e) {
        runnable = e;
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text = new Text(text);
    }

    public boolean isShowing() {
        return showing;
    }

    public void hide() {
        this.showing = false;
    }

    public void show() {
        this.showing = true;
        time = 0;
    }
}
