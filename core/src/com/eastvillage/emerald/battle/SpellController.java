package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.*;
import com.eastvillage.emerald.battle.turn.TurnController;
import com.eastvillage.emerald.battle.turn.TurnQueueListener;
import com.eastvillage.emerald.battle.turn.TurnState;
import com.eastvillage.emerald.spells.Ability;
import com.eastvillage.emerald.spells.Spell;
import com.eastvillage.emerald.spells.TargetedSpell;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** The SpellController keeps track of which spells the current unit can cast as well as selecting, deselecting, and casting. */
public class SpellController implements TurnQueueListener {

    private static final int NONE = -1;

    private BattleController controller;
    private TurnController turnController;
    private Battlefield battlefield;
    private ClickableHighlightController clickableHighlightController;

    private BattleUnit currentUnit;
    private List<Ability> currentAbilities;
    private int selectedIndex = NONE;
    private TargetedSpell selectedSpell;
    private ClickableHighlightRequest targetRequest;

    public SpellController(BattleController controller) {
        this.controller = controller;
        this.turnController = controller.getTurnController();
        this.battlefield = controller.getBattlefield();
        this.clickableHighlightController = controller.getClickableHighlightController();
    }

    @Override
    public void onQueueCycle(TurnController turnController) {
        deselect();
        currentUnit = turnController.current().getUnit();
        currentAbilities = currentUnit.getAbilities();
    }

    @Override
    public void onQueueModified(TurnController turnController) {
        
    }

    public List<Ability> getAbilities() {
        return new ArrayList<>(currentAbilities);
    }

    /** Activate an ability given an index. This will either do nothing, cast the spell, or prompt for targeting
     * depending on what type of spell is selected. If the activated index is currently selected, it will be
     * deselected. */
    public void activate(int index) {
        if (index < 0 || currentAbilities.size() <= index) throw new IndexOutOfBoundsException();

        if (selectedIndex == index) {
            deselect();
        } else {
            Ability activated = currentAbilities.get(index);
            if (activated instanceof TargetedSpell) {
                selectSpellAndStartTargeting(index, (TargetedSpell) activated);
            } else if (activated instanceof Spell) {
                // instant spell // TODO
            } else {
                // passive or aura // TODO
            }
        }
    }

    private void selectSpellAndStartTargeting(int index, TargetedSpell spell) {
        selectedIndex = index;
        selectedSpell = spell;
        turnController.current().changeState(TurnState.SELECTING_SPECIAL_TARGET);
        Set<Hex> possibleTargetHexes = spell.findTargets(currentUnit, battlefield);
        targetRequest = clickableHighlightController.request(possibleTargetHexes, HighlightType.VALID_ATTACK, this::tryCastSelectSpell);
    }

    /** Deselect any currently selected TargetedSpell. */
    public void deselect() {
        // Any "selected" spell are waiting for at target request
        if (selectedSpell != null) clickableHighlightController.remove(targetRequest);
        selectedIndex = NONE;
        selectedSpell = null;
        turnController.current().changeState(TurnState.IDLE);
    }

    private boolean tryCastSelectSpell(Tile targetTile, int button) {
        if (button == 0) {
            BattleUnit unit = battlefield.getUnitAt(targetTile.hex);
            selectedSpell.resolve(unit, targetTile);

            selectedIndex = NONE;
            selectedSpell = null;
            turnController.current().changeState(TurnState.ENDED);
            return true;
        }
        return false;
    }
}
