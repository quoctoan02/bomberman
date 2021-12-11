package com.game.items;

import com.game.Mob;
import com.game.ObjectBlock;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URISyntaxException;
import java.util.LinkedList;

public class Item extends Mob {
    public static final int COUNTUP = 0;
    public static final int BOMBUP = 1;
    public static final int SPEEDUP = 2;
    public static LinkedList<Image> itemImg = null;
    private int item;

    public Item(int item, int x, int y) {
        if (itemImg == null) {
            itemImg = new LinkedList<>();
            try {
                for (int i = 1; i <= 3; i++) {
                    System.out.println(i);
                    itemImg.add(new Image(getClass()
                            .getResource("/image/items/item" + i + ".png").toURI().toString()));
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        setX(x);
        setY(y);
        this.item = item;
        setSurvival(true);
        setObjectBlock(new ObjectBlock(0, 0, 0, 0));
    }

    @Override
    public void draw(GraphicsContext render) {
        getObjectBlock().update(getX() + 1, getY() + 1, 46, 46);
        render.drawImage(itemImg.get(item), getX(), getY(), 48, 48);
    }

    public int getEffect() {
        return item;
    }
}
