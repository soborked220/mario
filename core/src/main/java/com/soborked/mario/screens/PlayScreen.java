package com.soborked.mario.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.soborked.mario.MarioGame;

public class PlayScreen implements Screen {

    private MarioGame game;
    Texture texture;


    public PlayScreen(MarioGame game){
        this.game = game;
        this.texture = new Texture("badlogic.jpg"); //TODO inject
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1); //Sets the background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Clears the screen.

        game.batch.begin(); //Open the box.
        game.batch.draw(texture, 100, 100); //Draw box: origin is bottom left of screen
        game.batch.end(); //Close box
    }

    @Override
    public void resize(int width, int height) {

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
