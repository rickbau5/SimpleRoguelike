package com.rickbau5.roguelike;

import com.rickbau5.roguelike.entities.RogueLikePlayer;
import com.rickbau5.roguelike.tiles.HidableTile;
import me.vrekt.lunar.Game;
import me.vrekt.lunar.state.GameState;

import java.awt.*;

/**
 * Created by Rick on 3/14/2017.
 */
public class MainState extends GameState {
    private SimpleWorld world;
    private RogueLikePlayer rogueLikePlayer;

    private boolean initialized = false;

    /**
     * Initializes the GameState.
     *
     * @param priority
     */
    public MainState(Game game, SimpleWorld world, RogueLikePlayer player, int priority) {
        super(priority);
        this.world = world;
        this.rogueLikePlayer = player;
        world.setRogueLikePlayer(rogueLikePlayer);
        game.clearKeyListeners();
        game.addKeyListener(new PlayerInputListener(rogueLikePlayer));
    }

    @Override
    public void onDraw(Graphics graphics) {
        if (!initialized) {
            for (int row = 0; row < world.getHeight(); row++) {
                for (int col = 0; col < world.getWidth(); col++) {
                    HidableTile tile = ((HidableTile) world.getTileAt(col, row));
                    tile.setX(col);
                    tile.setY(row);
                    tile.setRogueLikePlayer(rogueLikePlayer);
                    tile.setWorld(world);
                }
            }
            initialized = true;
        }
        world.onDraw(graphics);
        rogueLikePlayer.drawEntity(graphics);
    }

    @Override
    public void onTick() {
        rogueLikePlayer.updateEntity();
        world.onTick();
    }
}
