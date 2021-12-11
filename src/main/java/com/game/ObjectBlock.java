package com.game;

public class ObjectBlock {
    private double x;
    private double y;
    private double width;
    private double height;

    public ObjectBlock(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean insideBlock(ObjectBlock obj) {
        return insideBlock(obj.x, obj.y)
                || insideBlock(obj.x + obj.width, obj.y)
                || insideBlock(obj.x, obj.y + obj.height)
                || insideBlock(obj.x + obj.width, obj.y + obj.height);
    }

    private boolean insideBlock(double x, double y) {
        return x >= this.x && x <= this.x + width
                && y >= this.y && y <= this.y + height;
    }

    public boolean equalLocation(double x, double y) {
        return this.x == x && this.y == y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
