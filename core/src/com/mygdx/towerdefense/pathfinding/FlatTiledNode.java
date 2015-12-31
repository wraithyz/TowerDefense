package com.mygdx.towerdefense.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class FlatTiledNode extends TiledNode<FlatTiledNode> {

    public int index;

    public FlatTiledNode (int col, int row, int connectionCapacity) {
        super(col, row, new Array<Connection<FlatTiledNode>>(connectionCapacity));

    }

    @Override
    public int getIndex () {
        return index;
    }

}