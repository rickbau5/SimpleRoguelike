package com.rickbau5.roguelike.entities;

import com.rickbau5.roguelike.SimpleWorld;
import me.vrekt.lunar.entity.living.LivingEntity;
import me.vrekt.lunar.location.Location;
import me.vrekt.lunar.server.annotations.Deserialize;
import me.vrekt.lunar.server.annotations.Serializable;
import me.vrekt.lunar.server.annotations.Serialize;
import me.vrekt.lunar.server.packets.PacketManager;
import me.vrekt.lunar.sprite.SpriteManager;
import me.vrekt.lunar.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Rick Boss on 3/15/2017.
 */
@Serializable
public class Monster extends LivingEntity {
    private float maxHealth = 100f;
    private boolean removed = false;

    public Monster() {
        this.texture = SpriteManager.load("monster1.png");
        this.width = 32;
        this.height = 32;
        this.entityID = -1;
        this.health = 100;
        this.speed = 1;
    }

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
            /*
            world.queueEntityForRemoval(this);
            world.queueEntityForAdd(new Monster(world, texture,
                    world.getRandom().nextInt(world.getWidth()),
                    world.getRandom().nextInt(world.getHeight()),
                    width, height, entityID + 1, 100, speed));
            removed = true;
            */
        }
    }

    @Deserialize
    public void deserialize(DataInputStream dis) throws IOException {
        setX(dis.readInt());
        setY(dis.readInt());
    }

    @Serialize
    public byte[] serialize() throws IOException {
        return PacketManager.withOutStreams((bos, dos) -> {
            dos.writeInt(getX());
            dos.writeInt(getY());
        });
    }
}
