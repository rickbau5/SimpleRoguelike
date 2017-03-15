package com.rickbau5.roguelike;

import me.vrekt.lunar.entity.living.LivingEntity;
import me.vrekt.lunar.location.Location;
import me.vrekt.lunar.sprite.SpriteManager;
import me.vrekt.lunar.tile.Tile;

import java.awt.*;

/**
 * Created by Rick on 3/14/2017.
 */
public class Player extends LivingEntity {
    public SimpleWorld world;

    public Player(SimpleWorld world, int entityID, int x, int y, float health, double speed) {
        super(SpriteManager.load("player.png"), x, y, 32, 32, entityID, health, speed);
        this.world = world;
    }

    public boolean attemptMove(int xChange, int yChange) {
        Tile t = world.getTileAt(x + xChange, y + yChange);
        if (t != null && !t.isSolid()) {
            this.x = x + xChange;
            this.y = y + yChange;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void drawEntity(Graphics graphics) {
        Location loc = world.worldToScreenLocation(x, y, width, height);
        graphics.drawImage(texture, loc.getX(), loc.getY(), null);
        graphics.setColor(Color.blue);
        graphics.drawRect(loc.getX(), loc.getY(), width, height);
    }

    @Override
    public void updateEntity() {

    }
}
