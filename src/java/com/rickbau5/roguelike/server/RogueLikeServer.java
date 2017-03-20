package com.rickbau5.roguelike.server;

import com.rickbau5.roguelike.MainState;
import com.rickbau5.roguelike.SimpleWorld;
import com.rickbau5.roguelike.SpriteSheetReader;
import com.rickbau5.roguelike.tiles.TileTemplate;
import me.vrekt.lunar.Lunar;
import me.vrekt.lunar.entity.living.player.LocalPlayer;
import me.vrekt.lunar.server.NetworkedGame;
import me.vrekt.lunar.server.Networking;
import me.vrekt.lunar.server.SimpleServer;
import me.vrekt.lunar.sprite.SpriteManager;
import me.vrekt.lunar.world.World;

import java.util.ArrayList;

/**
 * Created by Rick on 3/14/2017.
 */
public class RogueLikeServer extends NetworkedGame {
    public Lunar lunar;
    public SimpleWorld world;
    public LocalPlayer player;

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

        ArrayList<TileTemplate> tiles = SpriteSheetReader.loadSpritesAsTiles("tilesheet.png", 0);

        world = new SimpleWorld("first", 40, 22, 32, 32, tiles);
        player = new LocalPlayer(world, SpriteManager.load("player.png"), 20, 11, 32, 32, 0, 100, 1);
        lunar.getGame().addToStack(new MainState(lunar.getGame(), world, player, 1));
    }

    @Override
    public synchronized World getWorld() {
        return world;
    }

    @Override
    public LocalPlayer getPlayer() {
        return player;
    }

    @Override
    public void disconnect() {
        Networking.SERVER.stop();
        System.exit(0);
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
