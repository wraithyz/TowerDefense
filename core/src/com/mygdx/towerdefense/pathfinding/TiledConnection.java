package com.mygdx.towerdefense.pathfinding;

import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.mygdx.towerdefense.Tile;

/**
 * Created by azakhary on 8/22/2015.
 */
public class TiledConnection extends DefaultConnection<Tile>
{

    public TiledConnection(Tile fromNode, Tile toNode) {
        super(fromNode, toNode);
    }
}
