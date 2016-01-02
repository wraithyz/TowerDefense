package com.mygdx.towerdefense.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
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
import com.mygdx.towerdefense.sprites.SpawnSystem;

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
    private int startPosX;
    private int startPosY;
    private int endPosX;
    private int endPosY;

    private SpawnSystem spawnSystem;

    // Sounds.
    Music music;

    public static final float TILESIZE = 32f;

    public PlayScreen(TowerDefense game)
    {
        this.game = game;

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(TowerDefense.V_WIDTH / TowerDefense.PPM, TowerDefense.V_HEIGHT / TowerDefense.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Maps/uusi.tmx");
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Road");
        findPoints(map.getLayers().get("Point").getObjects());
        tiledWorld = new TiledWorld(layer);
        navigationMap = new NavigationMap();
        navigationMap.init(tiledWorld);
        currPath = navigationMap.findPath(new Tile(startPosX, startPosY), new Tile(endPosX, endPosY));

        renderer = new OrthogonalTiledMapRenderer(map, 1 / TowerDefense.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        spawnSystem = new SpawnSystem(currPath, startPosX, startPosY);

        game.manager.load("music/sanic.mp3", Music.class);
        game.manager.finishLoading();

        music = game.manager.get("music/sanic.mp3", Music.class);
        music.setLooping(true);
        music.play();

        //new WorldCreator(world, map, enemy);
    }

    public void findPoints(MapObjects objects)
    {
        for (MapObject object : objects)
        {
            Rectangle pos = ((RectangleMapObject) object).getRectangle();

            if (object.getName().equals("Start"))
            {
                if (pos.getX() == 0)
                {
                    startPosX = 0;
                }
                else
                {
                    startPosX = (int) pos.getX() / 128;
                }
                if (pos.getY() == 0)
                {
                    startPosY = 0;
                }
                else
                {
                    startPosY = (int) pos.getY() / 128;
                }
            }
            else if (object.getName().equals("End"))
            {
                if (pos.getX() == 0)
                {
                    endPosX = 0;
                }
                else
                {
                    endPosX = (int) pos.getX() / 128;
                }
                if (pos.getY() == 0)
                {
                    endPosY = 0;
                }
                else
                {
                    endPosY = (int) pos.getY() / 128;
                }
            }
        }
    }

    @Override
    public void show()
    {

    }

    public void update(float dt)
    {
        world.step(1 / 60f, 6, 2);
        spawnSystem.update(dt);
        if (spawnSystem.getEnemies() != null)
        {
            for (Enemy e : spawnSystem.getEnemies())
            {
                e.update(dt);
            }
        }

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
        if (spawnSystem.getEnemies() != null)
        {
            for (int i = 0; i < spawnSystem.getEnemies().size(); i++)
            {
                Enemy e = spawnSystem.getEnemies().get(i);
                if (!e.isToBeRemoved())
                {
                    game.batch.draw(e.getTexture(), e.getPosition().x, e.getPosition().y);
                }
                else
                {
                    spawnSystem.removeItem(i);
                }
            }
        }
        game.batch.end();
    }

    public int getStartPosX()
    {
        return startPosX;
    }

    public int getStartPosY()
    {
        return startPosY;
    }

    public int getEndPosX()
    {
        return endPosX;
    }

    public int getEndPosY()
    {
        return endPosY;
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
