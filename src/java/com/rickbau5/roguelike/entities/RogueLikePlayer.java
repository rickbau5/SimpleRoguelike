package com.rickbau5.roguelike.entities;

import me.vrekt.lunar.entity.living.player.PlayerEntity;
import me.vrekt.lunar.sprite.SpriteManager;
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

    public double getViewDistance() {
        return viewDistance;
    }
}
