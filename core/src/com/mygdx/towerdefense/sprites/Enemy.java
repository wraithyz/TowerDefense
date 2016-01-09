package com.mygdx.towerdefense.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.towerdefense.pathfinding.FlatTiledNode;
import com.mygdx.towerdefense.screens.PlayScreen;

import static com.mygdx.towerdefense.TowerDefense.PPM;

public class Enemy extends Sprite
{
    private float speed;
    private Vector2 position;
    private Array<FlatTiledNode> currPath;
    private boolean spawned;
    private int pathIndex;
    private boolean toBeRemoved = false;

    private int startPosX;
    private int startPosY;

    public Enemy(Array<FlatTiledNode> currPath, int startPosX, int startPosY, Texture texture, Sprite sprite, float speed)
    {
        this.currPath = currPath;
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.speed = speed;
        set(sprite);
        setTexture(texture);
        spawned = false;
        pathIndex = 0;
    }

    public void update(float dt)
    {

        if (!spawned)
        {
            position = new Vector2();
            position.x = startPosX;
            position.y = startPosY * PlayScreen.TILESIZE * PPM + PlayScreen.TILESIZE;
            //setOrigin(getWidth() / 2f, 0);
            spawned = true;

        }

        if (pathIndex < currPath.size-1)
        {
            FlatTiledNode nextNode = currPath.get(pathIndex +1);

            float x = nextNode.col * PlayScreen.TILESIZE * PPM;
            float y = nextNode.row * PlayScreen.TILESIZE * PPM;

            Vector2 pointFrom = new Vector2(position.x, position.y);
            Vector2 pointTo = new Vector2(x, y + PlayScreen.TILESIZE) ;

            int verticalDirection = 0;
            int horizontalDirection = 0;
            if (pointFrom.x == pointTo.x && pointFrom.y > pointTo.y)
            {
                verticalDirection = -1;
            }
            if (pointFrom.x == pointTo.x && pointFrom.y < pointTo.y)
            {
                verticalDirection = 1;
            }
            if (pointFrom.y == pointTo.y && pointFrom.x > pointTo.x)
            {
                horizontalDirection = -1;
            }
            if (pointFrom.y == pointTo.y && pointFrom.x < pointTo.x)
            {
                horizontalDirection = 1;
            }
/*
            if((horizontalDirection == 1 && getScaleX() < 0) || (horizontalDirection == -1 && getScaleX() > 0))
            {
                setScale(getScaleX()*-1f, getScaleY());
            }
*/
            if ((horizontalDirection == 1 && position.x + speed * dt > pointTo.x) ||
                    (horizontalDirection == -1 && position.x - speed * dt < pointTo.x) ||
                    (verticalDirection == 1 && position.y + speed * dt > pointTo.y) ||
                    (verticalDirection == -1 && position.y - speed * dt < pointTo.y))
            {

                position.x = x;
                position.y = y + PlayScreen.TILESIZE;

                pathIndex++;
            }
            else
            {
                position.x += horizontalDirection * speed * dt;
                position.y += verticalDirection * speed * dt;
            }
            setPosition(position.x, position.y);
            rotate(2f);
        }
        else
        {
            toBeRemoved = true;
        }
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public boolean isToBeRemoved()
    {
        return toBeRemoved;
    }


}

