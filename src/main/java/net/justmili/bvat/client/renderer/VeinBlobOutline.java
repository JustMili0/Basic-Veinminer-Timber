package net.justmili.bvat.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.justmili.bvat.content.mechanics.logic.Veinminer;
import net.justmili.bvat.core.util.TagUtil;
import net.minecraft.client.Minecraft;

public class VeinBlobOutline {

    // Render white outline around the whole ore vein when the player looks at it with a pickaxe in hand
    public static boolean onBlockOutline(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline) {
        var player = Minecraft.getInstance().player;
        if (player == null || !Veinminer.canSeeVein(player, outline.blockState())) return true;
        float rgb = OutlineShade.get(player);
        return BlockGroupOutliner.render(context, outline, player, TagUtil.matchOre(outline.blockState()), Veinminer.MAX_RADIUS, Veinminer.MAX_VEIN, 0.6f, rgb, rgb, rgb, true);
    }
}