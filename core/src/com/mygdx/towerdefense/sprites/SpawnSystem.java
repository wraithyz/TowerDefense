package com.mygdx.towerdefense.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.towerdefense.pathfinding.FlatTiledNode;

import java.util.ArrayList;

/**
 * Created by Samuli on 29.12.2015.
 */
public class SpawnSystem
{

    private float coolDown = 1f;

    private int minionCount = 10;
    private int currMinions;

    private Texture texture;
    private Sprite sprite;

    private int startPosX;
    private int startPosY;

    public Array<FlatTiledNode> currPath;

    private ArrayList<Enemy> enemies;

    private ArrayList<EnemyStats> minionList;

    public SpawnSystem(Array<FlatTiledNode> currPath, int startPosX, int startPosY)
    {
        this.currPath = currPath;
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        enemies = new ArrayList<Enemy>();
        minionList = new ArrayList<EnemyStats>();
        currMinions = minionCount;

        minionList.add(new EnemyStats(91, 91, 50, "Ufo Blue", "Enemies/ufoBlue.png"));
        minionList.add(new EnemyStats(91, 91, 100, "Ufo Green", "Enemies/ufoGreen.png"));
        minionList.add(new EnemyStats(91, 91, 150, "Ufo Red", "Enemies/ufoRed.png"));
        minionList.add(new EnemyStats(91, 91, 200, "Ufo Yellow", "Enemies/ufoYellow.png"));

    }

    public void update (float deltaTime)
    {
        coolDown -= deltaTime;
        if(coolDown < 0)
        {
            coolDown = 0;
        }

        if(coolDown == 0 && currMinions >= 0)
        {
            spawnMinion(minionList.get(MathUtils.random(0, minionList.size() - 1)));
            coolDown = 1f;
        }
    }

    private void spawnMinion(EnemyStats enemyStats)
    {
        FileHandle fileHandle = Gdx.files.internal(enemyStats.getFile());
        texture = new Texture(fileHandle);
        sprite = new Sprite(texture, 0, 0, enemyStats.getWidth(), enemyStats.getHeight());

        enemies.add(new Enemy(currPath, startPosX, startPosY, texture, sprite, enemyStats.getSpeed()));
        currMinions--;
    }

    public void setMinions(int minions)
    {
        currMinions = minions;
    }

    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }

    public void removeItem(int i)
    {
        // Disposing the texture.
        enemies.get(i).getTexture().dispose();
        enemies.remove(i);
    }

}
