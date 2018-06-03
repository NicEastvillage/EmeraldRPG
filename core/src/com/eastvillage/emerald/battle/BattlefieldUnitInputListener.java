package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Tile;

public interface BattlefieldUnitInputListener {

    void onUnitHoverBegin(Tile tile, BattleUnit unit);
    void onUnitHoverEnd(Tile tile, BattleUnit unit);
    void onUnitTouchDown(Tile tile, BattleUnit unit, int button);
    void onUnitTouchUp(Tile tile, BattleUnit unit, int button);
    void onUnitClicked(Tile tile, BattleUnit unit, int button);
}
