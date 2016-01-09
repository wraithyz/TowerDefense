package com.mygdx.towerdefense.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Turret
{
    private Texture texture;
    private Sprite sprite;
    private Vector2 position;

    private boolean movable;
    private boolean selected;

    private int startPosX;
    private int startPosY;

    private ArrayList<TurretStats> turretList;

    public Turret()
    {
        turretList = new ArrayList<TurretStats>();
        FileHandle fileHandle = Gdx.files.internal("SpaceShooterRedux/PNG/Enemies/enemyBlack1.png");
        texture = new Texture(fileHandle);
        position = new Vector2(0, 0);
        selected = false;
        movable = false;
        sprite = new Sprite(texture, (int)position.x, (int)position.y, 104, 84);
    }

    public void setPosition(float x, float y)
    {
        position.x = x;
        position.y = y;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void dispose()
    {
        texture.dispose();
    }


    public Vector2 getPosition()
    {
        return position;
    }

    public boolean isMovable()
    {
        return movable;
    }

    public void setMovable(boolean movable)
    {
        this.movable = movable;
    }
}
