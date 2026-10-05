package net.justmili.bvat.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.justmili.bvat.content.mechanics.logic.Veinminer;
import net.justmili.bvat.core.util.TagUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;

public class VeinBlobHighlighter {

    // Render white outline around block group if block group is looked at by the player
    public static boolean onBlockOutline(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline) {
        var player = Minecraft.getInstance().player;
        if (player == null || !Veinminer.canSeeVein(player, outline.blockState())) return true;
        return BlockGroupHighlighter.render(context, outline, player, TagUtil.match(outline.blockState()),
            Veinminer.MAX_RADIUS, Veinminer.MAX_VEIN_SIZE, 1f, 1f, 1f, 0.6f);
    }
}