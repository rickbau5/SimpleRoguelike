package com.rickbau5.roguelike.tiles;

import java.awt.image.BufferedImage;

/**
 * Created by Rick Boss on 3/15/2017.
 */
public class HidableTileTemplate extends TileTemplate<HidableTile> {
    private final String name;

    public HidableTileTemplate(BufferedImage texture, int ID, String name, int width, int height, boolean isSolid) {
        super(texture, ID, width, height, isSolid);

        this.name = name;
    }

    @Override
    public HidableTile createTile() {
        return new HidableTile(getTexture(), getID(), name, getWidth(), getHeight(), isSolid());
    }
}
