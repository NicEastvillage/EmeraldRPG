package com.eastvillage.emerald.spells;

public interface SpellListener {

    void onCastStart();
    void onCastFinished();
    //void onCooldownStart(); //TODO
    //void onCooldownOver(); //TODO
}
