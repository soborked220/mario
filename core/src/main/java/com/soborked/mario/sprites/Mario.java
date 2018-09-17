package com.soborked.mario.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.soborked.mario.MarioGame;
import com.soborked.mario.screens.PlayScreen;

public class Mario extends Sprite {

    public enum State{ FALLING, JUMPING, STANDING, RUNNING } ;
    public State currentState;
    public State previousState;

    private World world;
    public Body b2body;

    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean isRunningRight;

    public Mario(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario")); //Name is based on the original name of the file before it is packed
        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(), 1, 11, 16, 16); //Need to look inside the image to see where standing image is located
        setBounds(0, 0, 16 / MarioGame.PPM, 16 / MarioGame.PPM);
        setRegion(marioStand);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++){
            frames.add(new TextureRegion(getTexture(), 1 + i * 16, 11, 16, 16));
        }

        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 4; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), 1 + i * 16, 11, 16, 16));
        }

        marioJump = new Animation<TextureRegion>(0.1f, frames);

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        isRunningRight = true;

    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getHeight() / 2);

        setRegion(getFrame(delta));
    }

    private TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }



        //Instead of using region flip, I could use setScale(-1f, 1f) to face opposite direction
        //If I do this, I need to make sure to call setOriginCenter after setting up the sprite's bounds in defineMario
        if( (b2body.getLinearVelocity().x < 0 || !isRunningRight) && !region.isFlipX()){ //running left, but region isnt facing to the left
            region.flip(true, false);
            isRunningRight = false;
        } else if( (b2body.getLinearVelocity().x > 0 || isRunningRight) && region.isFlipX()){ //running to right, but currently facing left
            region.flip(true, false);
            isRunningRight = true;
        }





        stateTimer = currentState == previousState ? stateTimer + delta : 0; //reset timer if I switched states
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState.equals(State.JUMPING))){ //Must be jumping, or falling while in a jump
            return State.JUMPING;
        }
         else if(b2body.getLinearVelocity().y < 0) { //Must be falling
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0){
             return State.RUNNING;
        } else {
             return State.STANDING;
        }
    }

    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MarioGame.PPM, 32 / MarioGame.PPM); //start position in world
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MarioGame.PPM);

        //TODO to avoid the awkward bumps in animation when he runs over blocks, make him a square
        //PolygonShape shape = new PolygonShape;
        //shape.setAsBox(16 /2 / MarioGame.PPM, 16 / 2 / Mario.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        shape.dispose();

     /*   FixtureDef fdef2 = new FixtureDef();
        fdef2.isSensor = true;
        PolygonShape sense = new PolygonShape();
        sense.setRadius(1 / MarioGame.PPM);
        fdef2.shape = sense;
        b2body.createFixture(fdef2);
        sense.dispose();*/
    }

}
