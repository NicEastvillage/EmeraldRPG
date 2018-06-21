package com.eastvillage.emerald.battle;

import com.eastvillage.emerald.battle.battlefield.*;
import com.eastvillage.emerald.battle.turn.TurnController;
import com.eastvillage.emerald.battle.turn.TurnQueueListener;
import com.eastvillage.emerald.battle.turn.TurnState;
import com.eastvillage.emerald.spells.Ability;
import com.eastvillage.emerald.spells.Spell;
import com.eastvillage.emerald.spells.TargetedSpell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** The SpellController keeps track of which spells the current unit can cast as well as selecting, deselecting, and casting. */
public class SpellController implements TurnQueueListener {

    private static final int NONE = -1;

    private Battlefield battlefield;
    private TurnController turnController;
    private ClickableHighlightController clickableHighlightController;

    private BattleUnit currentUnit;
    private List<Ability> currentAbilities = new ArrayList<>();
    private int selectedIndex = NONE;
    private TargetedSpell selectedSpell;
    private ClickableHighlightRequest targetRequest;

    private List<SpellControllerListener> listeners = new LinkedList<>();

    public SpellController(Battlefield battlefield, TurnController turnController, ClickableHighlightController clickableHighlightController) {
        this.battlefield = battlefield;
        this.turnController = turnController;
        this.clickableHighlightController = clickableHighlightController;
    }

    public void addListener(SpellControllerListener listener) {
        listeners.add(listener);
    }

    public boolean removeListener(SpellControllerListener listener) {
        return listeners.remove(listener);
    }

    @Override
    public void onQueueCycle(TurnController turnController) {
        deselect();
        currentUnit = turnController.current().getUnit();
        currentAbilities = currentUnit.getAbilities();
        for (SpellControllerListener listener : listeners) {
            listener.onCurrentAbilitiesChanged(this);
        }
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

        if (selectedIndex == index) { // TODO What happens if I select another while one is selected?
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
        listeners.forEach(l -> l.onSpellSelected(this, selectedIndex));
        turnController.current().changeState(TurnState.SELECTING_SPECIAL_TARGET);
        Set<Hex> possibleTargetHexes = spell.findTargets(currentUnit, battlefield);
        targetRequest = clickableHighlightController.request(possibleTargetHexes, HighlightType.VALID_ATTACK, this::tryCastSelectSpell);
    }

    /** Deselect any currently selected TargetedSpell. */
    public void deselect() {
        int index = selectedIndex;
        // Any "selected" spell are waiting for at target request
        if (selectedSpell != null) clickableHighlightController.remove(targetRequest);
        selectedIndex = NONE;
        selectedSpell = null;
        listeners.forEach(l -> l.onSpellDeselected(this, selectedIndex));
        turnController.current().changeState(TurnState.IDLE);
    }

    private boolean tryCastSelectSpell(Tile targetTile, int button) {
        if (button == 0) {
            BattleUnit unit = battlefield.getUnitAt(targetTile.hex);
            selectedSpell.resolve(unit, targetTile);

            listeners.forEach(l -> l.afterSpellCast(this, selectedIndex));

            selectedIndex = NONE;
            selectedSpell = null;
            turnController.current().changeState(TurnState.ENDED);
            return true;
        }
        return false;
    }

    /** Returns how many abilities current unit has. */
    public int abilityCount() {
        return currentAbilities.size();
    }
}
