package com.rickbau5.roguelike;

import com.rickbau5.roguelike.entities.Monster;
import com.rickbau5.roguelike.tiles.TileTemplate;
import com.rickbau5.roguelike.tiles.WorldTile;
import me.vrekt.lunar.entity.Entity;
import me.vrekt.lunar.entity.living.player.PlayerEntity;
import me.vrekt.lunar.location.Location;
import me.vrekt.lunar.sprite.SpriteManager;
import me.vrekt.lunar.tile.Tile;
import me.vrekt.lunar.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Rick on 3/14/2017.
 */
public class SimpleWorld extends World {
    private ArrayList<TileTemplate> worldTiles;

    private PlayerEntity player;
    private Color gridColor = new Color(255, 255, 255, 50);

    private Random random = new Random(666);

    /**
     * Initialize the world.
     *
     * @param name   Name of the world
     * @param width  Width of the world
     * @param height Height of the world
     */
    public SimpleWorld(String name, int width, int height, int tileSizeX, int tileSizeY, ArrayList<TileTemplate> tiles) {
        super(name, width, height, tileSizeX, tileSizeY);

        worldTiles = tiles;

        worldAnchorX = 2;
        worldAnchorY = 24;

        buildMap();
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public void onTick() {
        super.onTick();
        worldEntities.forEach(Entity::updateEntity);
    }

    @Override
    public void onDraw(Graphics graphics) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile t = getTileAt(col, row);
                if (t != null) {
                    t.drawTile(graphics, worldAnchorX + col * t.getWidth(), worldAnchorY + row * t.getHeight());

                    graphics.setColor(gridColor);
                    graphics.drawRect(worldAnchorX + col * t.getWidth(), worldAnchorY + row * t.getHeight(), t.getWidth(), t.getHeight());
                }
            }
        }
        // Don't draw entities here - tiles are responsible for drawing everything on them.
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
                if (random.nextDouble() > .05) {
                    str += " 0";
                } else {
                    str += " 1";
                }
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
                Optional<TileTemplate> tile = worldTiles.stream().filter(t -> t.getID() == id).findFirst();
                if (tile.isPresent()) {
                    WorldTile worldTile = (WorldTile) tile.get().createTile();
                    worldTile.setX(loc.getX());
                    worldTile.setY(loc.getY());
                    this.addTile(loc.getX(), loc.getY(), worldTile);
                } else {
                    System.out.println("No tile found for id: " + id + " and char: " + str.charAt(row * width + col));
                }
            }
        }
    }

    public Random getRandom() {
        return random;
    }
}
