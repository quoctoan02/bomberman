package com.game;

import javafx.scene.canvas.GraphicsContext;

public abstract class Mob {
    private double x;
    private double y;
    private boolean survival;
    private boolean killing;

    public double getX() {
        return x;
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

    public boolean isSurvival() {
        return survival;
    }

    public void setSurvival(boolean survival) {
        this.survival = survival;
    }

    public abstract void draw(GraphicsContext render);

    public boolean isKilling() {
        return killing;
    }

    public void setKilling(boolean killing) {
        this.killing = killing;
    }
}
