package com.rickbau5.roguelike;

import com.rickbau5.roguelike.tiles.HidableTile;
import me.vrekt.lunar.location.Location;
import me.vrekt.lunar.sprite.SpriteManager;
import me.vrekt.lunar.tile.Tile;
import me.vrekt.lunar.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Rick on 3/14/2017.
 */
public class SimpleWorld extends World {
    private ArrayList<HidableTile> worldTiles;

    public static final int WORLD_OFFSET_X = 2;
    public static final int WORLD_OFFSET_Y = 26;

    /**
     * Initialize the world.
     *
     * @param name   Name of the world
     * @param width  Width of the world
     * @param height Height of the world
     */
    public SimpleWorld(String name, int width, int height, ArrayList<HidableTile> tiles) {
        super(name, width, height);

        worldTiles = tiles;

        buildMap();
    }

    private void buildMap() {
        String str = "";
        str += "32";
        for (int j = 0; j < width - 2; j++) {
            str += " 51";
        }
        str += " 33";
        for (int i = 0; i < height - 2; i++) {
            str += " 17";
            for (int j = 0; j < width - 2; j++) {
                str += " 0";
            }
            str += " 16";
        }
        str += " 64";
        for (int j = 0; j < width - 2; j++) {
            str += " 3";
        }
        str += " 65";
        Integer[] integers = Arrays.stream(str.split(" "))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        Location loc;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                loc = new Location(col, row);
                int id = integers[row * width + col];
                Optional<HidableTile> tile = worldTiles.stream().filter(t -> t.getID() == id).findFirst();
                if (tile.isPresent()) {
                    HidableTile copy = tile.get().copy();
                    copy.setX(loc.getX());
                    copy.setY(loc.getY());
                    this.addTile(loc.getX(), loc.getY(), copy);
                } else {
                    System.out.println("No tile found for id: " + id + " and char: " + str.charAt(row * width + col));
                }
            }
         }
    }

    public Location worldToScreenLocation(int x, int y, int width, int height) {
        return new Location(WORLD_OFFSET_X + x * width, WORLD_OFFSET_Y + y * height);
    }

    @Override
    public void onDraw(Graphics graphics) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile t = getTileAt(col, row);
                if (t != null) {
                    t.drawTile(graphics, WORLD_OFFSET_X + col * t.getWidth(), WORLD_OFFSET_Y + row * t.getHeight());

                    //graphics.setColor(Color.white);
                    //graphics.drawRect(WORLD_OFFSET_X + col * t.getWidth(), WORLD_OFFSET_Y + row * t.getHeight(), t.getWidth(), t.getHeight());
                }
            }
        }
    }

    @Override
    public void onTick() {

    }
}
