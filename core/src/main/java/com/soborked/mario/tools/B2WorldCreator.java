package com.soborked.mario.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.soborked.mario.MarioGame;
import com.soborked.mario.sprites.Brick;
import com.soborked.mario.sprites.Coin;

public class B2WorldCreator {


    private static final int GROUND_LAYER = 2;
    private static final int PIPES_LAYER = 3;
    private static final int COINS = 4;
    private static final int BRICKS_LAYER = 5;

    public B2WorldCreator(World world, TiledMap map){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Loop through every object in the specified map layer, and create a physical body for it.
        for(MapObject object : map.getLayers().get(GROUND_LAYER).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set( (rect.getX() + rect.getWidth() / 2) / MarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MarioGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MarioGame.PPM, (rect.getHeight() / 2) / MarioGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Loop through every object in the specified map layer, and create a physical body for it.
        for(MapObject object : map.getLayers().get(PIPES_LAYER).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set( (rect.getX() + rect.getWidth() / 2) / MarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MarioGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MarioGame.PPM, (rect.getHeight() / 2) / MarioGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Loop through every object in the specified map layer, and create a physical body for it.
        for(MapObject object : map.getLayers().get(BRICKS_LAYER).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(world, map, rect);
        }

        //Loop through every object in the specified map layer, and create a physical body for it.
        for(MapObject object : map.getLayers().get(COINS).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Coin(world, map, rect);
        }
    }
}
