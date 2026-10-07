package net.justmili.bvat.core.util.client;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

// Stripped down game (client) util from Millie's Core Libraries. Temporary until I switch this to actually make this use the library
public class GameUtil {
    private static final Minecraft client = Minecraft.getInstance();

    public static Minecraft client() {
        return client;
    }

    public static Window window() {
        return client.getWindow();
    }

    public static Player player() {
        return client.player;
    }

    public static ClientLevel level() {
        return client.level;
    }
}
