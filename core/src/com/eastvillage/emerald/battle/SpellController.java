package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.ClickableHighlightController;
import com.eastvillage.emerald.battle.turn.TurnController;
import com.eastvillage.emerald.battle.turn.TurnQueueListener;
import com.eastvillage.emerald.spells.Ability;

import java.util.List;

public class SpellController implements TurnQueueListener {

    private BattleController controller;
    private Battlefield battlefield;
    private ClickableHighlightController clickableHighlightController;

    private BattleUnit currentUnit;
    private List<Ability> currentAbilities;

    public SpellController(BattleController controller) {
        this.controller = controller;
        this.battlefield = controller.getBattlefield();
        this.clickableHighlightController = controller.getClickableHighlightController();
    }

    @Override
    public void onQueueCycle(TurnController turnController) {
        currentUnit = turnController.current().getUnit();
        currentAbilities = currentUnit.getAbilities();
    }

    @Override
    public void onQueueModified(TurnController turnController) {
        
    }
}
