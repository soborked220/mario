package com.soborked.mario.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.soborked.mario.MarioGame;
import com.soborked.mario.sprites.Brick;
import com.soborked.mario.sprites.Coin;
import com.soborked.mario.sprites.Ground;
import com.soborked.mario.sprites.Pipe;

public class B2WorldCreator {

    //These are based on the layers defined in the tmx file
    //The tmx file defines which objects exist in a given layer, and their pixel location in the tile map image
    private static final int GROUND_LAYER = 2;
    private static final int PIPES_LAYER = 3;
    private static final int COINS = 4;
    private static final int BRICKS_LAYER = 5;

    public B2WorldCreator(World world, TiledMap map){

        //TODO Can add a property to each layer to indicate if is Object, Graphic, etc.
        //TODO Could loop through all layers and simply handle rectangles, circles, etc. separately

        //Loop through every object in the specified map layer, and create a physical body for it.
        for(MapObject object : map.getLayers().get(GROUND_LAYER).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Ground(world, map, rect);
        }

        //Loop through every object in the specified map layer, and create a physical body for it.
        for(MapObject object : map.getLayers().get(PIPES_LAYER).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Pipe(world, map, rect);
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
