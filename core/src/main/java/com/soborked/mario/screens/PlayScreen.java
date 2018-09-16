package com.soborked.mario.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.soborked.mario.MarioGame;
import com.soborked.mario.scenes.Hud;
import com.sun.prism.image.ViewPort;
import main.java.com.soborked.mario.sprites.Mario;


public class PlayScreen implements Screen {

    private static final int GROUND_LAYER = 2;
    private static final int PIPES_LAYER = 3;
    private static final int COINS = 4;
    private static final int BRICKS_LAYER = 5;
    private MarioGame game;

    //Camera stuff
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    //Visual map & objects
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Physics from Box2D
    private World world;
    private Box2DDebugRenderer b2dr;

    private Mario player;

    public PlayScreen(MarioGame game){
        this.game = game;
        //this.texture = new Texture("badlogic.jpg"); //TODO inject
        this.gameCam = new OrthographicCamera();
        //this.gamePort = new StretchViewport(800, 480, gameCam);
        //this.gamePort = new ScreenViewport(gameCam);
        this.gamePort = new FitViewport(MarioGame.VIRTUAL_WIDTH / MarioGame.PPM, MarioGame.VIRTUAL_HEIGHT / MarioGame.PPM, gameCam);
        this.hud = new Hud(game.batch);


        this.mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("level1.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioGame.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0); //Why doesn't this need scaling by PPM?


        //Create physics
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();


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
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set( (rect.getX() + rect.getWidth() / 2) / MarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MarioGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MarioGame.PPM, (rect.getHeight() / 2) / MarioGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Loop through every object in the specified map layer, and create a physical body for it.
        for(MapObject object : map.getLayers().get(COINS).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set( (rect.getX() + rect.getWidth() / 2) / MarioGame.PPM, (rect.getY() + rect.getHeight() / 2) / MarioGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / MarioGame.PPM, (rect.getHeight() / 2) / MarioGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        this.player = new Mario(this.world);
    }

    public void handleInput(float deltatime){
        /*if(Gdx.input.isTouched()){
            gameCam.position.x += 100 * deltatime;
        }*/

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float deltatime){
        handleInput(deltatime);

        world.step(1 / 60f, 6, 2); //What is this?

        if(player.b2body.getPosition().x >= MarioGame.VIRTUAL_WIDTH / 2 / MarioGame.PPM){ //If mario crosses the middle of the screen
            gameCam.position.x = player.b2body.getPosition().x;
        }


      /*  //If you want to disable automatically updating the camera to mario's current position, and manually direct instead
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            gameCam.position.x = gamePort.getWorldWidth() / 2; //Snap back to start view
        }

        if(Gdx.input.isKeyPressed(Input.Keys.P)){
            gameCam.position.x = player.b2body.getPosition().x; //Center on mario.
        }

        if(Gdx.input.isKeyPressed(Input.Keys.B)){
            gameCam.position.x = player.b2body.getPosition().x - (100 / MarioGame.PPM); //Snap to just behind mario
        }

        if(Gdx.input.isKeyPressed(Input.Keys.L)){
            gameCam.position.x = player.b2body.getPosition().x + (100 / MarioGame.PPM); //Snap to just behind mario
        }*/

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1); //Sets the background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Clears the screen.


        renderer.render();
        b2dr.render(world, gameCam.combined);

        /*game.batch.setProjectionMatrix(gameCam.combined); //Tells gamePort to only render what the camera can see
        game.batch.begin(); //Open the box.
        game.batch.draw(texture, 100, 100); //Draw box: origin is bottom left of screen
        game.batch.end(); //Close box*/

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height); //Keep game view aligned in size with Screen size.
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //texture.dispose();
    }
}
