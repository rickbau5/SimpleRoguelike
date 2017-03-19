package com.rickbau5.roguelike.tiles;

import com.rickbau5.roguelike.entities.RogueLikePlayer;
import me.vrekt.lunar.raycast.RayCast;
import me.vrekt.lunar.entity.Entity;
import me.vrekt.lunar.location.Location;
import me.vrekt.lunar.utilities.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A HidableTile is one that is aware of a RogueLikePlayer. If the rogueLikePlayer is within view distance,
 * the tile and all contents will be rendered. If the rogueLikePlayer is outside of view distance,
 * the tile will either be rendered with "shade" over it if the rogueLikePlayer has been there, or
 * as a black space if the rogueLikePlayer has not previously seen the tile.
 *
 * Note that this tile is responsible for rendering it's contents, such as entities. This
 * consolidates the calculation of LOS to one location.
 *
 * Created by Rick on 3/15/2017.
 */
public class HidableTile extends WorldTile {
    private RogueLikePlayer rogueLikePlayer;

    private boolean visited = false;
    private Color hiddenColor = new Color(0, 0, 0, 125);

    public HidableTile(BufferedImage texture, int ID, String name, int width, int height, boolean isSolid) {
        super(texture, ID, name, width, height, isSolid, false);
    }

    @Override
    public void drawTile(Graphics graphics, int screenX, int screenY) {
        int x = getX();
        int y = getY();
        int oX = rogueLikePlayer.getX();
        int oY = rogueLikePlayer.getY();
        double dist = Utilities.distance(x, y, oX, oY);

        if (dist <= rogueLikePlayer.getViewDistance()) {
            RayCast.RayCastResult res = LOSCheck(oX, oY);
            if (!res.didCollide() || res.getCollidedTile() == this) {
                super.drawTile(graphics, screenX, screenY);
                visited = true;
                Entity entity = world.getEntityAt(x, y);
                if (entity != null) {
                    entity.drawEntity(graphics);
                }

                return;
            }
        }

        if (visited) {
            drawVisited(graphics, screenX, screenY);
        } else {
            drawHidden(graphics, screenX, screenY);
        }
    }

    private void drawVisited(Graphics graphics, int screenX, int screenY) {
        super.drawTile(graphics, screenX, screenY);
        graphics.setColor(hiddenColor);
        graphics.fillRect(screenX, screenY, getWidth(), getHeight());
    }

    private void drawHidden(Graphics graphics, int screenX, int screenY) {
        graphics.setColor(Color.black);
        graphics.fillRect(screenX, screenY, getWidth(), getHeight());
    }

    private RayCast.RayCastResult LOSCheck(int x, int y) {
        Location other = world.worldToScreenLocation(x, y);
        Location tile = world.worldToScreenLocation(getX(), getY());

        RayCast rayCast = new RayCast(world);

        return rayCast
            .doRayCast(other.getX() + 16, other.getY() + 16, tile.getX() + 16, tile.getY() + 16);
    }

    public void setRogueLikePlayer(RogueLikePlayer rogueLikePlayer) {
        this.rogueLikePlayer = rogueLikePlayer;
    }
}
