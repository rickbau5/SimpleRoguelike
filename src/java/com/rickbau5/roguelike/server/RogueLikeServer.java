package com.rickbau5.roguelike.server;

import com.rickbau5.roguelike.*;
import com.rickbau5.roguelike.entities.RogueLikePlayer;
import com.rickbau5.roguelike.tiles.TileTemplate;
import me.vrekt.lunar.Lunar;
import me.vrekt.lunar.entity.living.player.PlayerEntity;
import me.vrekt.lunar.server.NetworkedGame;
import me.vrekt.lunar.server.Networking;
import me.vrekt.lunar.server.SimpleServer;
import me.vrekt.lunar.world.World;

import java.util.ArrayList;

/**
 * Created by Rick on 3/14/2017.
 */
public class RogueLikeServer extends NetworkedGame {
    public Lunar lunar;
    public SimpleWorld world;
    public RogueLikePlayer player;

    public static RogueLikeServer INSTANCE;

    public static void main(String[] args) {
        INSTANCE = new RogueLikeServer();
    }

    private RogueLikeServer() {
        setup();
        lunar.getGame().start();
        initServer();
    }

    private void initServer() {
        Networking.GAME_INSTANCE = this;
        SimpleServer server = new SimpleServer(this, "", 23456);
        Networking.SERVER = server;
        server.start();
    }

    private void setup() {
        lunar = new Lunar();
        lunar.initializeGame("Roguelike Server", 1280, 730, 20);
        String str = "asdf";

        ArrayList<TileTemplate> tiles = SpriteSheetReader.loadSpritesAsTiles("tilesheet.png", 0);

        world = new SimpleWorld("first", 40, 22, 32, 32, tiles);
        player = new RogueLikePlayer(world, 0, 20, 11, 100, 1);
        lunar.getGame().addToStack(new MainState(lunar.getGame(), world, player, 1));
    }

    @Override
    public synchronized World getWorld() {
        return world;
    }

    @Override
    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public boolean isServer() {
        return true;
    }
}
