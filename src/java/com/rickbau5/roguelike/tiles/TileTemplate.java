package com.rickbau5.roguelike.tiles;

import me.vrekt.lunar.tile.Tile;

import java.awt.image.BufferedImage;

/**
 * Created by Rick on 3/15/2017.
 */
public abstract class TileTemplate<T extends Tile> extends Tile {
    public TileTemplate(BufferedImage texture, int ID, int width, int height, boolean isSolid) {
        super(texture, ID, width, height, isSolid);
    }

    public abstract T createTile();
}
