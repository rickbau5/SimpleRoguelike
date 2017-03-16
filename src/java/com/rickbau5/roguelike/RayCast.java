package com.rickbau5.roguelike;

import me.vrekt.lunar.location.Location;
import me.vrekt.lunar.tile.Tile;
import me.vrekt.lunar.utilities.Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to perform ray casting within the given world.
 * <p>
 * Adapted from this great article:
 * https://www.codeproject.com/articles/15604/ray-casting-in-a-2d-tile-based-environment
 * <p>
 * Created by Rick on 3/16/2017.
 */
public class RayCast {
    private RayCastResult raycastResult;

    public class RayCastResult {
        private boolean collided;
        private Location collidedLocation;
        private Tile collidedTile;

        public RayCastResult(Tile t, Location l, boolean c) {
            collidedTile = t;
            collidedLocation = l;
            collided = c;
        }

        /**
         * Whether the ray cast collided with a tile
         */
        public boolean didCollide() {
            return collided;
        }

        /**
         * The location of either the tile that the ray cast collided with
         * or the end of the ray.
         *
         * @return
         */
        public Location getLocation() {
            return collidedLocation;
        }

        /**
         * The tile that was collided with. Can be null in the case of no collision
         *
         * @return
         */
        public Tile getCollidedTile() {
            return collidedTile;
        }

        @Override
        public String toString() {
            return String.format("RayCastResult[collided: %b, location: (%d, %d), tile: %s]",
                    collided, collidedLocation.getX(), collidedLocation.getY(), collidedTile == null ? "null" : collidedTile.toString());
        }
    }

    private final SimpleWorld world;

    public RayCast(SimpleWorld world) {
        this.world = world;
    }

    private List<Location> BresenhamLine(int xi, int yi, int xf, int yf) {
        int dist = (int) Math.ceil(Utilities.distance(xi, yi, xf, yf));
        List<Location> list = new ArrayList<>(dist);

        boolean steep = Math.abs(yf - yi) > Math.abs(xf - xi);
        int temp;
        if (steep) {
            temp = xi;
            xi = yi;
            yi = temp;
            temp = xf;
            xf = yf;
            yf = temp;
        }
        if (xi > xf) {
            temp = xi;
            xi = xf;
            xf = temp;
            temp = yi;
            yi = yf;
            yf = temp;
        }

        int dx = xf - xi;
        int dy = Math.abs(yf - yi);
        int error = 0;
        int ystep = yi < yf ? 1 : -1;
        int y = yi;
        /*
        if (yi < yf) {
            ystep = 1;
        } else {
            ystep = -1;
        }
        */

        for (int x = xi; x < xf; x++) {
            if (steep) {
                list.add(new Location(y, x));
            } else {
                list.add(new Location(x, y));
            }
            error += dy;
            if (2 * error >= dx) {
                y += ystep;
                error -= dx;
            }
        }

        return list;
    }

    public RayCastResult doRayCast(int originX, int originY, int dirX, int dirY, float distance) {
        return doRayCast(originX, originY, originX + (int) (dirX * distance), originY + (int) (dirY * distance));
    }

    public RayCastResult doRayCast(int originX, int originY, int targetX, int targetY) {
        List<Location> ray = BresenhamLine(originX, originY, targetX, targetY);

        for (Location location : ray) {
            if (!world.isPointPassable(location.getX(), location.getY())) {
                Location worldLoc = world.screenToWorldLocation(location.getX(), location.getY());
                return new RayCastResult(world.getTileAt(worldLoc.getX(), worldLoc.getY()), worldLoc, true);
            }
        }

        raycastResult = new RayCastResult(null, world.screenToWorldLocation(targetX, targetY), false);
        return raycastResult;
    }

    public static void draw(Graphics graphics, SimpleWorld world, int originX, int originY, RayCastResult result) {
        Location worldLoc = world.worldToScreenLocation(result.getLocation());
        graphics.fillRect(originX, originY, 3, 3);
        graphics.drawLine(originX, originY, worldLoc.getX() + world.getTileWidth() / 2, worldLoc.getY() + world.getTileHeight() / 2);
    }
}
