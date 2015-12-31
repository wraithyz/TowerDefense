package com.mygdx.towerdefense.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.towerdefense.NavigationMap;
import com.mygdx.towerdefense.pathfinding.FlatTiledNode;

import static com.mygdx.towerdefense.TowerDefense.PPM;

public class Enemy implements Enemies
{
    public World world;
    private Texture texture;
    private Sprite enemy;
    public float speed = 100f;
    private Vector2 position;
    private Vector2 originalPosition;
    private Array<FlatTiledNode> currPath;
    private boolean spawned;
    private int pathindex;
    private boolean toBeRemoved = false;

    private NavigationMap navigationMap;

    private TextureRegion playerStand;

    public Enemy(World world, NavigationMap navigationMap, Array<FlatTiledNode> currPath)
    {
        this.world = world;
        this.navigationMap = navigationMap;
        this.currPath = currPath;
        originalPosition = new Vector2();
        FileHandle fileHandle = Gdx.files.internal("car.png");
        texture = new Texture(fileHandle);
        enemy = new Sprite(texture, 0, 158, 131, 70);
        spawned = false;
        pathindex = 0;
    }

    public void update(float dt)
    {

        if(!spawned)
        {
            position = new Vector2();
            position.x = 0;
            position.y = 64 * PPM + 32 * PPM / 4;

            spawned = true;
        }

        if(pathindex < currPath.size-1)
        {
            FlatTiledNode currNode = currPath.get(pathindex);
            FlatTiledNode nextNode = currPath.get(pathindex+1);

            float x = nextNode.col * 32 * PPM;
            float y = nextNode.row * 32 * PPM;

            Vector2 pointFrom = new Vector2(position.x, position.y);
            Vector2 pointTo = new Vector2(x, y + 32 * PPM/ 4) ;

            int verticalDirection = 0;
            int horizontalDirection = 0;
            if(pointFrom.x == pointTo.x && pointFrom.y > pointTo.y)
            {
                verticalDirection = -1;
            }
            if(pointFrom.x == pointTo.x && pointFrom.y < pointTo.y)
            {
                verticalDirection = 1;
            }
            if(pointFrom.y == pointTo.y && pointFrom.x > pointTo.x)
            {
                horizontalDirection = -1;
            }
            if(pointFrom.y == pointTo.y && pointFrom.x < pointTo.x)
            {
                horizontalDirection = 1;
            }


            if((horizontalDirection == 1 && position.x + speed * dt > pointTo.x) ||
                    (horizontalDirection == -1 && position.x - speed * dt < pointTo.x) ||
                    (verticalDirection == 1 && position.y + speed * dt > pointTo.y) ||
                    (verticalDirection == -1 && position.y - speed * dt < pointTo.y))
            {

                System.out.println(pointFrom.x + ", " + pointFrom.y + " to " + pointTo.x + ", " + pointTo.y);
                System.out.print(pathindex + " -> ");
                position.x = x;
                position.y = y + 32 * PPM / 4;

                pathindex++;
                System.out.println(pathindex);

            }
            else
            {
                position.x += horizontalDirection * speed * dt;
                position.y += verticalDirection * speed * dt;
            }
        }
        else
        {
            toBeRemoved = true;
        }
    }
    public Texture getTexture()
    {
        return texture;
    }

    public Sprite getEnemy()
    {
        return enemy;
    }

    @Override
    public Vector2 getPosition()
    {
        return position;
    }

    public boolean isToBeRemoved()
    {
        return toBeRemoved;
    }

    @Override
    public void dispose()
    {
        texture.dispose();
    }

}

