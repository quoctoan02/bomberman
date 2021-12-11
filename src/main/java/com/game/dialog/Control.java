package com.game.dialog;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Control {
    private double x;
    private double y;
    private Canvas camera;

    public Canvas getCamera() {
        return camera;
    }

    public void setCamera(Canvas camera) {
        this.camera = camera;
    }

    public double getX() {
        return x - camera.getLayoutX();
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public abstract void draw(GraphicsContext render);
}
