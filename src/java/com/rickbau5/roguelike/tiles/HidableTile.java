package com.rickbau5.roguelike.tiles;

import com.rickbau5.roguelike.entities.Player;
import me.vrekt.lunar.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A HidableTile is one that is aware of a Player. If the player is within view distance,
 * the tile and all contents will be rendered. If the player is outside of view distance,
 * the tile will either be rendered with "shade" over it if the player has been there, or
 * as a black space if the player has not previously seen the tile.
 *
 * Note that this tile is responsible for rendering it's contents, such as entities. This
 * consolidates the calculation of LOS to one location.
 *
 * Created by Rick on 3/15/2017.
 */
public class HidableTile extends WorldTile {
    private Player player;

    private boolean visited = false;
    private Color hiddenColor = new Color(0, 0, 0, 125);

    public HidableTile(BufferedImage texture, int ID, String name, int width, int height, boolean isSolid) {
        super(texture, ID, name, width, height, isSolid);
    }

    @Override
    public void drawTile(Graphics graphics, int screenX, int screenY) {
        int x = getX();
        int y = getY();
        int oX = player.getX();
        int oY = player.getY();
        double dx = oX - x;
        double dy = oY - y;
        double dist = Math.sqrt(dx*dx + dy*dy);

        if (dist <= player.getViewDistance()) {
            super.drawTile(graphics, screenX, screenY);
            visited = true;
            Entity entity = world.getEntityAt(x, y);
            if (entity != null) {
                entity.drawEntity(graphics);
            }
        } else if (visited) {
            super.drawTile(graphics, screenX, screenY);
            graphics.setColor(hiddenColor);
            graphics.fillRect(screenX, screenY, getWidth(), getHeight());
        } else {
            graphics.setColor(Color.black);
            graphics.fillRect(screenX, screenY, getWidth(), getHeight());
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HidableTile copy() {
        return new HidableTile(getTexture(), getID(), name, getWidth(), getHeight(), isSolid());
    }
}
