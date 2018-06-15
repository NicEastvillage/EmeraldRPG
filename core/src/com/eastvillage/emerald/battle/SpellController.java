package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.BattleUnit;
import com.eastvillage.emerald.battle.battlefield.Battlefield;
import com.eastvillage.emerald.battle.battlefield.ClickableHighlightController;
import com.eastvillage.emerald.battle.turn.TurnController;
import com.eastvillage.emerald.battle.turn.TurnQueueListener;
import com.eastvillage.emerald.spells.Ability;
import com.eastvillage.emerald.spells.Spell;
import com.eastvillage.emerald.spells.TargetedSpell;

import java.util.ArrayList;
import java.util.List;

/** The SpellController keeps track of which spells the current unit can cast as well as selecting, deselecting, and casting. */
public class SpellController implements TurnQueueListener {

    private static final int NONE = -1;

    private BattleController controller;
    private Battlefield battlefield;
    private ClickableHighlightController clickableHighlightController;

    private BattleUnit currentUnit;
    private List<Ability> currentAbilities;
    private int selectedIndex = NONE;
    private Ability selectedAbility;

    public SpellController(BattleController controller) {
        this.controller = controller;
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
        selectedAbility = spell;
        spell.onTargetSelectBegin(battlefield, clickableHighlightController);
    }

    /** Deselect any currently selected TargetedSpell. */
    public void deselect() {
        // Only TargetedSpells can be "selected" currently
        if (selectedAbility != null) ((TargetedSpell) selectedAbility).onTargetSelectCancel(clickableHighlightController);
        selectedIndex = NONE;
        selectedAbility = null;
    }
}
