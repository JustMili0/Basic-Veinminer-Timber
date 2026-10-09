package net.justmili.vnt.config;

// Enum prepared for later 1.1 update that will add a config
public enum DropPlacements {
    WHERE_BROKEN("default"), // Default for veinminer and timber
    ORIGIN_POS("origin"),
    PLAYER_POS("player"),
    PLAYER_INV("inventory"); // For timber also add a bool INCLUDE_LEAVES_DROPS for player inv drop placement

    DropPlacements(String configName) {
    }
}