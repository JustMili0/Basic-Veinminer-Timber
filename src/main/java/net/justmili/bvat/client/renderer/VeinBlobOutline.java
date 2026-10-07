package net.justmili.bvat.client.renderer;

import net.justmili.bvat.content.mechanics.logic.Veinminer;
import net.justmili.bvat.core.util.TagUtil;
import net.justmili.bvat.core.util.client.GameUtil;
import net.justmili.bvat.core.util.client.OutlineRGB;

public class VeinBlobOutline {

    // Render white outline around the whole ore vein when the player looks at it with a pickaxe in hand
    public static boolean onBlockOutline(BlockGroupOutlineContext group) {
        var player = GameUtil.player();
        var level = GameUtil.level();
        if (player == null || level == null) return true;
        var state = level.getBlockState(group.outline()./*? if >= 1.21.11 {*//*pos()*//*?} else {*/blockPos()/*?}*/);
        if (!Veinminer.canSeeVein(player, state)) return true;
        float rgb = OutlineRGB.get();
        return BlockGroupOutlineRenderer.render(group, player, TagUtil.matchOre(state), Veinminer.MAX_RADIUS, Veinminer.MAX_VEIN, 0.6f, rgb, rgb, rgb, true);
    }
}