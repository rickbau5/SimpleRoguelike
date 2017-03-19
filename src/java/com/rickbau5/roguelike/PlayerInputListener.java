package com.rickbau5.roguelike;

import com.rickbau5.roguelike.entities.RogueLikePlayer;
import me.vrekt.lunar.server.Networking;
import me.vrekt.lunar.server.packets.EntityMovementPacket;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Rick on 3/15/2017.
 */
public class PlayerInputListener implements KeyListener {
    private final RogueLikePlayer rogueLikePlayer;

    public PlayerInputListener(RogueLikePlayer rogueLikePlayer) {
        this.rogueLikePlayer = rogueLikePlayer;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        boolean moved = false;
        switch (e.getKeyChar()) {
            case 'a':
                moved = rogueLikePlayer.attemptMove(-1, 0);
                break;
            case 'd':
                moved = rogueLikePlayer.attemptMove(1, 0);
                break;
            case 's':
                moved = rogueLikePlayer.attemptMove(0, 1);
                break;
            case 'w':
                moved = rogueLikePlayer.attemptMove(0, -1);
                break;
            default:
                break;
        }

        if (moved) {
            Networking.sendToAllClients(new EntityMovementPacket(rogueLikePlayer));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
