package net.justmili.bvat.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.justmili.bvat.content.mechanics.logic.Veinminer;
import net.justmili.bvat.core.util.TagUtil;
import net.minecraft.client.Minecraft;

public class VeinBlobHighlighter {

    public static boolean onBlockOutline(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline) {
        var player = Minecraft.getInstance().player;
        if (player == null || !Veinminer.canSeeVein(player, outline.blockState())) return true;

        return BlockGroupHighlighter.render(context, outline, BlockHighlightRenderTypes.THICK_LINE, player, TagUtil.match(outline.blockState()), Veinminer.MAX_RADIUS, Veinminer.MAX_VEIN_SIZE);
    }
}
