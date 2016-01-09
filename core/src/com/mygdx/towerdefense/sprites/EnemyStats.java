package com.mygdx.towerdefense.sprites;

/**
 * Created by Samuli on 6.1.2016.
 */
public class EnemyStats
{
    private int width;
    private int height;
    private float speed;
    private String name;
    private String file;
    private float health;

    public EnemyStats(int width, int height, float speed, String name, String file)
    {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.name = name;
        this.file = file;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public float getSpeed()
    {
        return speed;
    }

    public String getName()
    {
        return name;
    }

    public String getFile()
    {
        return file;
    }
}
