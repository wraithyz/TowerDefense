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

    private int minionCount = 50;
    private int currMinions;

    private Texture texture;
    private Sprite sprite;
    private Enemy enemy;

    private int startPosX;
    private int startPosY;

    public Array<FlatTiledNode> currPath;

    private ArrayList<Enemy> enemies;

    private Array<String> minionList = new Array<String>();

    public SpawnSystem(Array<FlatTiledNode> currPath, int startPosX, int startPosY)
    {
        this.currPath = currPath;
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        enemies = new ArrayList<Enemy>();
        currMinions = minionCount;
        minionList.add("good.png");
        minionList.add("bad.png");
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
            spawnMinion(minionList.get(MathUtils.random(0, minionList.size - 1)), 112, 112);
            coolDown = 1f;
        }
    }

    private void spawnMinion(String file, int width, int height)
    {
        FileHandle fileHandle = Gdx.files.internal(file);
        texture = new Texture(fileHandle);
        sprite = new Sprite(texture, 0, 0, width, height);

        enemies.add(new Enemy(currPath, startPosX, startPosY, texture, sprite));
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
        enemies.get(i).dispose();
        enemies.remove(i);
    }

}
