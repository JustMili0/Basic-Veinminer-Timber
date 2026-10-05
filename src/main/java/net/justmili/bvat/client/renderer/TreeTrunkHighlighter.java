package net.justmili.bvat.client.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.justmili.bvat.content.mechanics.logic.Timber;
import net.justmili.bvat.core.util.TagUtil;
import net.minecraft.client.Minecraft;

public class TreeTrunkHighlighter {

    // Render default outline around block group if block group is looked at by the player
    public static boolean onBlockOutline(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline) {
        var player = Minecraft.getInstance().player;
        if (player == null || !Timber.canSeeTree(player, outline.blockState())) return true;
        return BlockGroupHighlighter.render(context, outline, player, TagUtil.match(outline.blockState()),
            Timber.MAX_RADIUS, Timber.MAX_TREE_SIZE, 1f, 1f, 1f, 0.6f);
    }
}