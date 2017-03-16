package com.rickbau5.roguelike.entities;

import com.rickbau5.roguelike.SimpleWorld;
import me.vrekt.lunar.entity.living.LivingEntity;
import me.vrekt.lunar.location.Location;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Rick Boss on 3/15/2017.
 */
public class Monster extends LivingEntity {
    private final SimpleWorld world;

    private float maxHealth = 100f;
    private boolean removed = false;

    public Monster(SimpleWorld world, BufferedImage sprite, int x, int y, int width, int height, int entityID, float health, double speed) {
        super(sprite, x, y, width, height, entityID, health, speed);

        this.world = world;
    }

    @Override
    public void drawEntity(Graphics graphics) {
        if (removed)
            return;
        Location loc = world.worldToScreenLocation(x, y);
        graphics.drawImage(getTexture(), loc.getX(), loc.getY(), 32, 32, null);

        if (health < maxHealth) {
            graphics.setColor(Color.red);
            graphics.fillRect(loc.getX(), loc.getY(), (int) (32 * (health / maxHealth)), 6);
        }
    }

    @Override
    public void updateEntity() {
        if (health <= 0 && !removed) {
            world.markEntityForRemoval(this);
            removed = true;
        }
    }
}
