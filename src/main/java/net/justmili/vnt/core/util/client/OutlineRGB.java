package net.justmili.vnt.core.util.client;

import net.justmili.vnt.config.Config;
import net.justmili.vnt.config.FloatColor;
import net.minecraft.world.entity.player.Player;

public class OutlineRGB {

    public static FloatColor get(Player player) {
        return player.isShiftKeyDown()? Config.activeOutlineColor : Config.inactiveOutlineColor;
    }
}