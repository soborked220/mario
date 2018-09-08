package com.soborked.mario;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soborked.mario.screens.PlayScreen;

public class MarioGame extends Game {

	/**
	 * Want to only have one of these up at a time.
	 */
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render(); //Delegates rendering to Game class, which in turn delegates to currently active Screen.
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
