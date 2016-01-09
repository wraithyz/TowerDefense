package com.mygdx.towerdefense.sprites;

/**
 * Created by Samuli on 6.1.2016.
 */
public class TurretStats
{
    private int width;
    private int height;
    private String name;
    private String file;
    private int damage;
    private boolean locked;

    public TurretStats(int width, int height, String name, String file, int damage, boolean locked)
    {
        this.width = width;
        this.height = height;
        this.name = name;
        this.file = file;
        this.damage = damage;
        this.locked = locked;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getName()
    {
        return name;
    }

    public String getFile()
    {
        return file;
    }

    public int getDamage()
    {
        return damage;
    }

    public boolean isLocked()
    {
        return locked;
    }
}
