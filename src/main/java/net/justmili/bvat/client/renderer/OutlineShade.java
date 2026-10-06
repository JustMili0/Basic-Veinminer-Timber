package net.justmili.bvat.client.renderer;

import net.minecraft.world.entity.player.Player;

public class OutlineShade {

    public static float get(Player player) {
        return player.isShiftKeyDown()? 1f : 0.35f;
    }
}
