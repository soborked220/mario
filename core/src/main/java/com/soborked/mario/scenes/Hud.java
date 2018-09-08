package com.soborked.mario.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.soborked.mario.MarioGame;

public class Hud {

    public Stage stage; //Think of it as an empty box.
    private Viewport viewport; //HUD needs its own viewport so that the game world can move around separate from the HUD.


    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    //Presentation objects
    Label timeLabel;
    Label countdownLabel;

    Label worldLabel;
    Label levelLabel;

    Label marioLabel;
    Label scoreLabel;

    public Hud(SpriteBatch batch){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MarioGame.VIRTUAL_WIDTH, MarioGame.VIRTUAL_HEIGHT, new OrthographicCamera());


        stage = new Stage(viewport, batch);


        //Create table
        Table table = new Table();
        table.top(); //Defaults to appearing in center of stage, this moves to top of stage
        table.setFillParent(true); //Table will fill to match the size of the stage


        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row(); //All new things added will be on a new row.

        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }
}
