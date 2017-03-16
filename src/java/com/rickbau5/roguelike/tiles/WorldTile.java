package com.rickbau5.roguelike.tiles;

import com.rickbau5.roguelike.SimpleWorld;
import me.vrekt.lunar.tile.Tile;

import java.awt.image.BufferedImage;

/**
 * A WorldTile is simple a tile that has an association with a world.
 * <p>
 * Created by Rick Boss on 3/15/2017.
 */
public abstract class WorldTile extends Tile {
    protected final String name;
    protected SimpleWorld world;

    public WorldTile(BufferedImage texture, int ID, String name, int width, int height, boolean isSolid) {
        super(texture, ID, width, height, isSolid);

        this.name = name;
    }

    public void setWorld(SimpleWorld world) {
        this.world = world;
    }
}
