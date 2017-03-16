package com.rickbau5.roguelike.tiles;

import me.vrekt.lunar.tile.Tile;

import java.awt.image.BufferedImage;

/**
 * A tile template responsible for holding the definition of a tile and creating
 * them on demand. This was added to differentiate between tiles that are in the
 * game world and those that are not.
 *
 * SpriteSheetReader reads in tile definitions, instantiating instances of this
 * class, and then during map building, createTile() is invoked to make an instance
 * of the given tile template for use in the game world.
 *
 * Created by Rick on 3/15/2017.
 */
public abstract class TileTemplate<T extends Tile> extends Tile {
    public TileTemplate(BufferedImage texture, int ID, int width, int height, boolean isSolid) {
        super(texture, ID, width, height, isSolid);
    }

    public abstract T createTile();
}
