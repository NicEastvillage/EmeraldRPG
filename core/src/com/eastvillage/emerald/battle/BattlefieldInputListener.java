package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.Hex;
import com.eastvillage.emerald.battle.battlefield.Tile;

public interface BattlefieldInputListener {

    void onTileHoverBegin(Tile tile);
    void onTileHoverEnd(Tile tile);
    void onTileClicked(Tile tile, int button);
}
