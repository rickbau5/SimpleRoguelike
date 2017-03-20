package com.rickbau5.roguelike;

import com.rickbau5.roguelike.tiles.HidableTile;
import me.vrekt.lunar.Game;
import me.vrekt.lunar.entity.living.player.PlayerEntity;
import me.vrekt.lunar.state.GameState;

import java.awt.*;

/**
 * Created by Rick on 3/14/2017.
 */
public class MainState extends GameState {
    private SimpleWorld world;
    private PlayerEntity player;

    private boolean initialized = false;

    /**
     * Initializes the GameState.
     *
     * @param priority
     */
    public MainState(Game game, SimpleWorld world, PlayerEntity player, int priority) {
        super(priority);
        this.world = world;
        this.player = player;
        world.setPlayer(this.player);
        game.clearKeyListeners();
        game.addKeyListener(new PlayerInputListener(this.player));
    }

    @Override
    public void onDraw(Graphics graphics) {
        if (!initialized) {
            for (int row = 0; row < world.getHeight(); row++) {
                for (int col = 0; col < world.getWidth(); col++) {
                    HidableTile tile = ((HidableTile) world.getTileAt(col, row));
                    tile.setX(col);
                    tile.setY(row);
                    tile.setPlayerEntity(player);
                    tile.setWorld(world);
                }
            }
            initialized = true;
        }
        world.onDraw(graphics);
        player.drawEntity(graphics);
    }

    @Override
    public void onTick() {
        player.updateEntity();
        world.onTick();
    }
}
