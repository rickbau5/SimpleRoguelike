package com.rickbau5.roguelike.entities;

import me.vrekt.lunar.entity.Entity;
import me.vrekt.lunar.entity.living.LivingEntity;
import me.vrekt.lunar.entity.living.player.PlayerEntity;
import me.vrekt.lunar.sprite.SpriteManager;
import me.vrekt.lunar.tile.Tile;
import me.vrekt.lunar.world.World;


/**
 * Created by Rick on 3/14/2017.
 */
public class RogueLikePlayer extends PlayerEntity {
    public World world;
    private double viewDistance;
    private float damage = 10.0f;

    public RogueLikePlayer(World world, int entityID, int x, int y, float health, double speed) {
        super(world, SpriteManager.load("player.png"), x, y, 32, 32, entityID, health, speed);
        this.world = world;

        viewDistance = 5.0;
    }

    public boolean attemptMove(int xChange, int yChange) {
        Tile t = world.getTileAt(x + xChange, y + yChange);
        if (t != null && !t.isSolid()) {
            Entity entity = world.getEntityAt(x + xChange, y + yChange);
            if (entity instanceof LivingEntity) {
                LivingEntity living = ((LivingEntity) entity);
                living.damageEntity(damage);
                System.out.println(living.getHealth());
                return false;
            } else {
                this.x = x + xChange;
                this.y = y + yChange;
                return true;
            }
        } else {
            return false;
        }
    }

    public double getViewDistance() {
        return viewDistance;
    }
}
