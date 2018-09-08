package com.soborked.mario.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.soborked.mario.MarioGame;
import com.sun.prism.image.ViewPort;


public class PlayScreen implements Screen {

    private MarioGame game;
    Texture texture;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    public PlayScreen(MarioGame game){
        this.game = game;
        this.texture = new Texture("badlogic.jpg"); //TODO inject
        this.gameCam = new OrthographicCamera();
        //this.gamePort = new StretchViewport(800, 480, gameCam);
        //this.gamePort = new ScreenViewport(gameCam);
        this.gamePort = new FitViewport(800, 480, gameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1); //Sets the background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Clears the screen.

        game.batch.setProjectionMatrix(gameCam.combined); //Tells gamePort to only render what the camera can see
        game.batch.begin(); //Open the box.
        game.batch.draw(texture, 100, 100); //Draw box: origin is bottom left of screen
        game.batch.end(); //Close box
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
        texture.dispose();
    }
}
