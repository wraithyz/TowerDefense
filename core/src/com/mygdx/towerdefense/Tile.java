package com.mygdx.towerdefense;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;

public class Tile implements IndexedNode<Tile>
{
    public int col;
    public int row;

    public Tile(int col, int row)
    {
        this.col = col;
        this.row = row;
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public Array<Connection<Tile>> getConnections() {
        return null;
    }
}
