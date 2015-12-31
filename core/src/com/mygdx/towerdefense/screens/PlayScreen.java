package com.mygdx.towerdefense.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.towerdefense.NavigationMap;
import com.mygdx.towerdefense.Tile;
import com.mygdx.towerdefense.TiledWorld;
import com.mygdx.towerdefense.TowerDefense;
import com.mygdx.towerdefense.pathfinding.FlatTiledNode;
import com.mygdx.towerdefense.scenes.Hud;
import com.mygdx.towerdefense.sprites.Enemy;

public class PlayScreen implements Screen
{

    private TowerDefense game;

    private OrthographicCamera gamecam;
    private FitViewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Enemy enemy;

    // Pathfinding.
    private TiledWorld tiledWorld;
    private NavigationMap navigationMap;
    public Array<FlatTiledNode> currPath;

    public PlayScreen(TowerDefense game)
    {
        this.game = game;

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(TowerDefense.V_WIDTH / TowerDefense.PPM, TowerDefense.V_HEIGHT / TowerDefense.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Maps/uusi.tmx");
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Road");
        tiledWorld = new TiledWorld(layer);
        navigationMap = new NavigationMap();
        navigationMap.init(tiledWorld);
        currPath = navigationMap.findPath(new Tile(0, 2), new Tile(9, 4));

        renderer = new OrthogonalTiledMapRenderer(map, 1 / TowerDefense.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        enemy = new Enemy(world, navigationMap, currPath);

        //new WorldCreator(world, map, enemy);
    }

    @Override
    public void show()
    {

    }

    public void update(float dt)
    {
        world.step(1 / 60f, 6, 2);

        enemy.update(dt);

        gamecam.update();
        renderer.setView(gamecam);

    }

    @Override
    public void render(float delta)
    {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        game.batch.begin();
        if (!enemy.isToBeRemoved())
        {
            game.batch.draw(enemy.getTexture(), enemy.getPosition().x, enemy.getPosition().y);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width, height);
    }


    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        world.dispose();
        game.dispose();
    }
}
