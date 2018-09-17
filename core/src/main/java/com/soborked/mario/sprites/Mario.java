package com.soborked.mario.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.soborked.mario.MarioGame;
import com.soborked.mario.screens.PlayScreen;

public class Mario extends Sprite {

    private World world;
    public Body b2body;

    private TextureRegion marioStand;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario")); //Name is based on the original name of the file before it is packed
        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(), 1, 11, 16, 16); //Need to look inside the image to see where standing image is located
        setBounds(0, 0, 16 / MarioGame.PPM, 16 / MarioGame.PPM);
        setRegion(marioStand);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getHeight() / 2);
    }

    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MarioGame.PPM, 32 / MarioGame.PPM); //start position in world
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MarioGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}
