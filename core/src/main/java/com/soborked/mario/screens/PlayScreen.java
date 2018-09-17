package com.soborked.mario.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.soborked.mario.sprites.Mario;
import com.soborked.mario.tools.B2WorldCreator;
import com.sun.prism.image.ViewPort;


public class PlayScreen implements Screen {

    private MarioGame game;

    //Camera stuff
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    //Visual map & objects
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TextureAtlas atlas;

    //Physics from Box2D
    private World world;
    private Box2DDebugRenderer b2dr;

    private Mario player;

    public PlayScreen(MarioGame game){
        this.atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;
        //this.texture = new Texture("badlogic.jpg"); //TODO inject
        this.gameCam = new OrthographicCamera();
        //this.gamePort = new StretchViewport(800, 480, gameCam);
        //this.gamePort = new ScreenViewport(gameCam);
        this.gamePort = new FitViewport(MarioGame.VIRTUAL_WIDTH / MarioGame.PPM, MarioGame.VIRTUAL_HEIGHT / MarioGame.PPM, gameCam); //TODO extract scaling
        this.hud = new Hud(game.batch);

        this.mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("level1.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioGame.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);


        //Create physics
        world = new World(new Vector2(0,-10), true); //Adds gravity. Really should be 9.8
        b2dr = new Box2DDebugRenderer(); //Allows me to see shapes on map

        new B2WorldCreator(world, map); //Adds map objects to world

        this.player = new Mario(this.world, this); //Creates mario
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float deltatime){
        /*if(Gdx.input.isTouched()){
            gameCam.position.x += 100 * deltatime;
        }*/

        //Do these match?
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            System.out.println("Body world center x: " + player.b2body.getWorldCenter().x); //The x coordinate of the center of the body in the game world
            System.out.println("Body world center y: " + player.b2body.getWorldCenter().y); //The y coordinate of the center of the body in the game world
            System.out.println("Game Port World Height: " + gamePort.getWorldHeight()); //2.08.
            System.out.println("Game Port World Width: " + gamePort.getWorldWidth()); //4.0.
            System.out.println("Game Port Screen Height: " + gamePort.getScreenHeight()); //333. Original game height/width ratio is 208/400 = .52. Scaled up leads to 333/640
            System.out.println("Game Port Screen Width: " + gamePort.getScreenWidth()); //640. Original game height/width ratio is 208/400 = .52. Scaled up leads to 333/640
            System.out.println("Game Port Screen X: " + gamePort.getScreenX()); //0. This is black space of x sides. Believe it is total black space, so divide by 2 to get one side
            System.out.println("Game Port Screen Y: " + gamePort.getScreenY()); //73. Adjusting screen larger lowered this. Maybe the black space at the bottom?
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            //TODO Interface method here
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float deltatime){
        handleInput(deltatime);

        world.step(1 / 60f, 6, 2); //What is this?

        player.update(deltatime);

        if(player.b2body.getPosition().x >= MarioGame.VIRTUAL_WIDTH / 2 / MarioGame.PPM){ //TODO If mario crosses the middle of the screen
            //TODO create method on mario to extract its X position
            //TODO consider creating an interface for all bodies to retrieve their X Y coordinates etc.
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

        //Draws mario
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        /*game.batch.setProjectionMatrix(gameCam.combined); //Tells gamePort to only render what the camera can see
        game.batch.begin(); //Open the box.
        game.batch.draw(texture, 100, 100); //Draw box: origin is bottom left of screen
        game.batch.end(); //Close box*/

        //Draws the hud
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
