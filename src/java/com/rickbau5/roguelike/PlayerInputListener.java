package com.rickbau5.roguelike;

import com.rickbau5.roguelike.entities.Monster;
import me.vrekt.lunar.entity.Entity;
import me.vrekt.lunar.entity.living.LivingEntity;
import me.vrekt.lunar.entity.living.player.PlayerEntity;
import me.vrekt.lunar.server.Networking;
import me.vrekt.lunar.server.packets.EntityMovementPacket;
import me.vrekt.lunar.sprite.SpriteManager;
import me.vrekt.lunar.tile.Tile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Rick on 3/15/2017.
 */
public class PlayerInputListener implements KeyListener {
    private final PlayerEntity playerEntity;

    public PlayerInputListener(PlayerEntity rogueLikePlayer) {
        this.playerEntity = rogueLikePlayer;
    }

    public boolean attemptMove(PlayerEntity player, int xChange, int yChange) {
        int x = player.getX();
        int y = player.getY();
        Tile t = player.world.getTileAt(x + xChange, y + yChange);
        if (t != null && !t.isSolid()) {
            Entity entity = player.world.getEntityAt(x + xChange, y + yChange);
            if (entity instanceof LivingEntity) {
                LivingEntity living = ((LivingEntity) entity);
                // living.damageEntity(damage);
                System.out.println(living.getHealth());
                return false;
            } else {
                player.setX(x + xChange);
                player.setY(y + yChange);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        boolean moved = false;
        switch (e.getKeyChar()) {
            case 'a':
                moved = attemptMove(playerEntity, -1, 0);
                break;
            case 'd':
                moved = attemptMove(playerEntity, 1, 0);
                break;
            case 's':
                moved = attemptMove(playerEntity, 0, 1);
                break;
            case 'w':
                moved = attemptMove(playerEntity, 0, -1);
                break;
            case 'q':
                if (Networking.GAME_INSTANCE != null) {
                    Networking.GAME_INSTANCE.disconnect();
                }
                break;
            case 'p':
                if (Networking.SERVER != null) {
                    Networking.SERVER.spawnEntity(new Monster((SimpleWorld)playerEntity.world, SpriteManager.load("monster1.png"), playerEntity.getX() + 1, playerEntity.getY() + 1, 32, 32, ((SimpleWorld) playerEntity.world).getNextEntityIdAndInc(), 100, 1));
                }
            default:
                break;
        }

        if (moved) {
            Networking.sendToAll(new EntityMovementPacket(playerEntity));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
