package com.eastvillage.emerald.spells;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/** An ability of any kind, shown in a unit's spell menu. */
public interface Ability {
    String getName();
    String getDescription();
    TextureRegion getIcon();
}
