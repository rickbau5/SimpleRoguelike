package com.rickbau5.roguelike;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Rick on 3/15/2017.
 */
public class PlayerInputListener implements KeyListener {
    private final Player player;

    public PlayerInputListener(Player player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'a': player.attemptMove(-1, 0);
                break;
            case 'd': player.attemptMove(1, 0);
                break;
            case 's': player.attemptMove(0, 1);
                break;
            case 'w': player.attemptMove(0, -1);
                break;
            default:
                System.out.println(e.getKeyChar());
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
