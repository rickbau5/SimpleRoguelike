package com.rickbau5.roguelike;

import com.rickbau5.roguelike.tiles.TileTemplate;
import me.vrekt.lunar.Lunar;

import java.util.ArrayList;

/**
 * Created by Rick on 3/14/2017.
 */
public class RogueLike {
    public Lunar lunar;
    public SimpleWorld world;

    public static RogueLike INSTANCE;

    public static void main(String[] args) {
        INSTANCE = new RogueLike();
    }

    private RogueLike() {
        setup();
        lunar.getGame().start();
    }

    private void setup() {
        lunar = new Lunar();
        lunar.initializeGame("Roguelike", 1280, 730, 20);

        ArrayList<TileTemplate> tiles = SpriteSheetReader.loadSpritesAsTiles("tilesheet.png", 0);

        world = new SimpleWorld("first", 40, 22, 32, 32, tiles);

        lunar.getGame().addToStack(new MainState(lunar.getGame(), world, 1));
    }
}
