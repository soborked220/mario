package com.soborked.mario.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Brick extends StaticTile {

    /**
     * Instantiates object and adds it to the world.
     *
     * @param world physical world of the game
     * @param map the visual tiles of the game world
     * @param bounds the dimensions of the object
     */
    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
