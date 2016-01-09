package com.mygdx.towerdefense.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.towerdefense.TowerDefense;

public class Hud implements Disposable
{
    public Stage stage;
    private FitViewport viewPort;
    private Skin skin;

    private float timeCount;
    private Integer score;
    private Integer worldTimer;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label playerLabel;

    TextureAtlas textureAtlas;
    private static final String HUDPACK = "SpaceShooterRedux/PNG/Enemies/Turrets.pack";

    public Hud(SpriteBatch sb)
    {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewPort = new FitViewport(TowerDefense.V_WIDTH, TowerDefense.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        textureAtlas = new TextureAtlas(HUDPACK);
        skin = new Skin(textureAtlas);

        Table table = new Table();
        table.setSkin(skin);
        table.setWidth(stage.getWidth());
        table.setHeight(TowerDefense.V_HEIGHT / 8);
        table.setPosition(0, TowerDefense.V_HEIGHT - TowerDefense.V_HEIGHT / 10);
        table.setBackground(new NinePatchDrawable(getNinePatch(("nine/res/drawable-hdpi/grey_panel.9.png"))));
        table.debug();

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("enemyBlack2");

        ImageButton turret = new ImageButton(style);

        table.left();
        table.add(turret);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        turret.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                System.out.println("Clicked");
            }
        });

        /*
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerLabel = new Label("PLAYER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(playerLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();
        */


    }

    private NinePatch getNinePatch(String fname)
    {

        // Get the image
        final Texture t = new Texture(Gdx.files.internal(fname));

        // create a new texture region, otherwise black pixels will show up too, we are simply cropping the image
        // last 4 numbers respresent the length of how much each corner can draw,
        // for example if your image is 50px and you set the numbers 50, your whole image will be drawn in each corner
        // so what number should be good?, well a little less than half would be nice
        return new NinePatch( new TextureRegion(t, 1, 1 , t.getWidth() - 2, t.getHeight() - 2), 10, 10, 10, 10);
    }

    @Override
    public void dispose()
    {
        textureAtlas.dispose();
        stage.dispose();
    }
}
