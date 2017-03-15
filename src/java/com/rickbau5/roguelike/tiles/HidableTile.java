package com.rickbau5.roguelike.tiles;

import com.rickbau5.roguelike.SimpleWorld;
import me.vrekt.lunar.entity.Entity;
import me.vrekt.lunar.location.Location;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Rick on 3/15/2017.
 */
public class HidableTile extends WorldTile {
    private Entity referenceEntity;

    private boolean visited = false;
    private Color hiddenColor = new Color(0, 0, 0, 125);

    public HidableTile(BufferedImage texture, int ID, String name, int width, int height, boolean isSolid) {
        super(texture, ID, name, width, height, isSolid);
    }

    @Override
    public void drawTile(Graphics graphics, int x, int y) {
        int myX = getX();
        int myY = getY();
        int oX = referenceEntity.getX();
        int oY = referenceEntity.getY();
        double dx = oX - myX;
        double dy = oY - myY;
        double dist = Math.sqrt(dx*dx + dy*dy);

        if (dist < 4) {
            super.drawTile(graphics, x, y);
            visited = true;
        } else if (visited) {
            super.drawTile(graphics, x, y);
            graphics.setColor(hiddenColor);
            graphics.fillRect(x, y, getWidth(), getHeight());
        } else {
            graphics.setColor(Color.black);
            graphics.fillRect(x, y, getWidth(), getHeight());
        }
    }

    public void setReferenceEntity(Entity entity) {
        this.referenceEntity = entity;
    }


    public HidableTile copy() {
        return new HidableTile(getTexture(), getID(), name, getWidth(), getHeight(), isSolid());
    }
}
