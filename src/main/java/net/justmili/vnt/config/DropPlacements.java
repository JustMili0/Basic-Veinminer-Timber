package net.justmili.vnt.config;

import net.minecraft.core.BlockPos;

// Enum prepared for later 1.1 update that will add a config
public enum DropPlacements {
    WHERE_BROKEN("default"),
    ORIGIN_POS("origin"),
    PLAYER_POS("player"),
    PLAYER_INV("inventory");

    DropPlacements(String entry) {
    }

    // Return
    public BlockPos posByPlace() {
        return BlockPos.ZERO;
    }
}