package com.soborked.mario.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.soborked.mario.MarioGame;

/**
 * Creates a static object in the world
 */
public abstract class StaticTile {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    /**
     * Instantiates object and adds it to the world.
     *
     * @param world physical world of the game
     * @param map the visual tiles of the game world
     * @param bounds the dimensions of the object
     */
    public StaticTile(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set( (bounds.getX() + bounds.getWidth() / 2) / MarioGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MarioGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / MarioGame.PPM, (bounds.getHeight() / 2) / MarioGame.PPM);
        fdef.shape = shape;
        body.createFixture(fdef);
    }


}
