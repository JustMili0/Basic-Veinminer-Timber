package net.justmili.bvat.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.justmili.bvat.content.mechanics.logic.Timber;
import net.minecraft.client.Minecraft;

public class TreeTrunkOutline {

    // Render white outline around the whole tree trunk when the player looks at a log with an axe in hand
    public static boolean onBlockOutline(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline) {
        var player = Minecraft.getInstance().player;
        if (player == null || !Timber.canSeeTree(player, outline.blockState())) return true;
        float rgb = OutlineShade.get(player);
        return BlockGroupOutliner.render(context, outline, player, Timber::isLog, Timber.MAX_RADIUS, Timber.MAX_TRUNK, 0.6f, rgb, rgb, rgb, true);
    }
}