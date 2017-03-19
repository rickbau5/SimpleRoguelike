package com.rickbau5.roguelike.client;

import com.rickbau5.roguelike.*;
import com.rickbau5.roguelike.entities.RogueLikePlayer;
import com.rickbau5.roguelike.tiles.TileTemplate;
import me.vrekt.lunar.Lunar;
import me.vrekt.lunar.entity.living.player.PlayerEntity;
import me.vrekt.lunar.server.NetworkedGame;
import me.vrekt.lunar.server.Networking;
import me.vrekt.lunar.server.RemoteClient;
import me.vrekt.lunar.server.packets.PlayerJoinPacket;
import me.vrekt.lunar.world.World;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Rick on 3/14/2017.
 */
public class RogueLikeClient extends NetworkedGame {
    public Lunar lunar;
    public SimpleWorld world;
    public RogueLikePlayer player;

    public static RogueLikeClient INSTANCE;


    public static void main(String[] args) {
        INSTANCE = new RogueLikeClient();
    }

    private RogueLikeClient() {
        setup();
        lunar.getGame().start();
        initClient();
    }

    private void initClient() {
        RemoteClient client;
        Networking.GAME_INSTANCE = this;
        try {
            Socket socket = new Socket("localhost", 23456);
            client = new RemoteClient(this, -1, socket);
            Networking.REMOTE_CLIENT = client;
            System.out.println("Client connected.");
            while (client.getId() == -1) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while sleeping...zzz");
                    e.printStackTrace();
                }
            }
            System.out.println("Handshake complete! Spawning in...");
            client.addPacketToOutbound(new PlayerJoinPacket(player));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Couldn't connect to the specified SERVER.");
        }
    }

    private void setup() {
        lunar = new Lunar();
        lunar.initializeGame("Roguelike Client", 1280, 730, 20);
        String asdf = "asdf2";

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
        return true;
    }

    @Override
    public boolean isServer() {
        return false;
    }
}

