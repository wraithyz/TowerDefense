package com.mygdx.towerdefense;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import static com.mygdx.towerdefense.TowerDefense.PPM;

public class TiledWorld
{
    public int tilesMap[][];

    public int size;
    public int cols, rows;

    public ArrayList<Tile> tiles = new ArrayList<Tile>();

    public TiledWorld (TiledMapTileLayer tiledMapTileLayer) {
        this.size = (int)tiledMapTileLayer.getTileWidth() / (int)PPM;
        this.cols = tiledMapTileLayer.getWidth();
        this.rows = tiledMapTileLayer.getHeight();
        tilesMap = new int[cols][rows];
        initData(tiledMapTileLayer);
    }

    private void initData (TiledMapTileLayer tiledMapTileLayer)
    {
        // TiledMaps 0,0 point is at bottom left corner.
        for (int i = 0; i < tiledMapTileLayer.getWidth(); i++)
        {
            for (int j = 0; j < tiledMapTileLayer.getHeight(); j++)
            {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(i, j);
                if (cell != null)
                {
                    tilesMap[i][j] = 1;
                    tiles.add(new Tile(i, j));
                }
            }
        }
    }

    public boolean isPath(int col, int row)
    {
        if (col > cols || row > rows)
        {
            return false;
        }
        if (tilesMap[col][row] == 1)
        {
            return true;
        }
        return false;
    }
}
