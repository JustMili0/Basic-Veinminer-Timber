package net.justmili.vnt.core.util.client;

import net.minecraft.world.entity.player.Player;

public class OutlineRGB {

    public static float get(Player player) {
        return player.isShiftKeyDown()? 1f : 0.35f;
    }
}