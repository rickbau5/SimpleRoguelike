package com.rickbau5.roguelike;

import com.rickbau5.roguelike.tiles.HidableTile;
import com.rickbau5.roguelike.tiles.TileTemplate;
import com.rickbau5.roguelike.tiles.WorldTile;
import me.vrekt.lunar.entity.Entity;
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
    private ArrayList<TileTemplate> worldTiles;
    private ArrayList<Entity> entityRemovalList;

    public static final int WORLD_OFFSET_X = 2;
    public static final int WORLD_OFFSET_Y = 26;
    private Player player;

    /**
     * Initialize the world.
     *
     * @param name   Name of the world
     * @param width  Width of the world
     * @param height Height of the world
     */
    public SimpleWorld(String name, int width, int height, ArrayList<TileTemplate> tiles) {
        super(name, width, height);

        worldTiles = tiles;
        entityRemovalList = new ArrayList<>();

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
                Optional<TileTemplate> tile = worldTiles.stream().filter(t -> t.getID() == id).findFirst();
                if (tile.isPresent()) {
                    WorldTile worldTile = (WorldTile)tile.get().createTile();
                    worldTile.setX(loc.getX());
                    worldTile.setY(loc.getY());
                    this.addTile(loc.getX(), loc.getY(), worldTile);
                } else {
                    System.out.println("No tile found for id: " + id + " and char: " + str.charAt(row * width + col));
                }
            }
         }

         addEntity(new Monster(this, SpriteManager.load("monster1.png"), 10, 10, 32, 32, 1, 100f, 1.0));
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        for (Entity entity : worldEntities) {
            double dx = entity.getX() - player.getX();
            double dy = entity.getY() - player.getY();
            double dist = Math.sqrt(dx*dx + dy*dy);
            if (dist <= player.getViewDistance()) {
                entity.drawEntity(graphics);
            }
        }
    }

    public void markEntityForRemoval(Entity entity) {
        entityRemovalList.add(entity);
    }

    @Override
    public void onTick() {
        entityRemovalList.forEach(this::removeEntity);
        worldEntities.forEach(Entity::updateEntity);
    }
}
