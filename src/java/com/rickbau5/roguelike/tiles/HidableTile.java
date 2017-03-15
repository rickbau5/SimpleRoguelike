package com.rickbau5.roguelike.tiles;

import com.rickbau5.roguelike.SimpleWorld;
import me.vrekt.lunar.entity.Entity;
import me.vrekt.lunar.location.Location;
import me.vrekt.lunar.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Rick on 3/15/2017.
 */
public class HidableTile extends Tile {
    private final String name;
    private Entity referenceEntity;
    private Location location;
    private SimpleWorld world;

    private boolean visited = false;
    private Color alphaColor = new Color(0, 0, 0, 125);

    public HidableTile(BufferedImage texture, int ID, String name, int width, int height, boolean isSolid) {
        super(texture, ID, width, height, isSolid);
        this.name = name;
    }

    public void setReferenceEntity(Entity entity) {
        this.referenceEntity = entity;
    }

    public void setWorld(SimpleWorld world) {
        this.world = world;
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
            graphics.setColor(alphaColor);
            graphics.fillRect(x, y, getWidth(), getHeight());
        } else {
            graphics.setColor(Color.black);
            graphics.fillRect(x, y, getWidth(), getHeight());
        }
    }

    public HidableTile copy() {
        return new HidableTile(getTexture(), getID(), name, getWidth(), getHeight(), isSolid());
    }
}
