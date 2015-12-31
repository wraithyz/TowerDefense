package com.mygdx.towerdefense.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public interface Enemies
{
    void update(float dt);

    Texture getTexture();

    Sprite getEnemy();

    Vector2 getPosition();

    void dispose();
}
