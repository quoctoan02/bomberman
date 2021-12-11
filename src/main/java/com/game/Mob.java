package com.game;

import javafx.scene.canvas.GraphicsContext;

public abstract class Mob {
    private double x;
    private double y;
    private boolean survival;
    private boolean killing;
    private ObjectBlock objectBlock;

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

    public ObjectBlock getObjectBlock() {
        return objectBlock;
    }

    public void setObjectBlock(ObjectBlock objectBlock) {
        this.objectBlock = objectBlock;
    }
}
