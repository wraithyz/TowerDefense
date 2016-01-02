package com.mygdx.towerdefense.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Turret
{
    private Texture texture;
    private Sprite turret;
    private Vector2 position;

    private int startPosX;
    private int startPosY;

    public Turret(int startPosX, int startPosY, Texture texture, Sprite sprite)
    {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.texture = texture;
        this.turret = sprite;
    }

}
