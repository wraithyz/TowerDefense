package com.mygdx.towerdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.towerdefense.screens.PlayScreen;

public class TowerDefense extends Game
{
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 768;
    public static final float PPM = 4;

	public SpriteBatch batch;
    public AssetManager manager;

    FPSLogger fps;

	@Override
	public void create()
	{
        fps = new FPSLogger();
        batch = new SpriteBatch();
        manager = new AssetManager();
        setScreen(new PlayScreen(this));
	}

	@Override
    public void render ()
    {
        fps.log();
        super.render();
    }

    public void dispose()
    {
        batch.dispose();
        manager.dispose();
    }
}
