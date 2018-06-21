package com.eastvillage.emerald.battle;

public interface SpellControllerListener {

    void onCurrentAbilitiesChanged(SpellController controller);
    void onSpellSelected(SpellController controller, int index);
    void onSpellDeselected(SpellController controller, int index);
    void afterSpellCast(SpellController controller, int index);
}
