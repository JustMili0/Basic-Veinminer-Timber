package net.justmili.bvat.client.renderer;

import net.justmili.bvat.content.mechanics.logic.Timber;
import net.justmili.bvat.core.util.client.GameUtil;
import net.justmili.bvat.core.util.client.OutlineRGB;

public class TreeTrunkOutline {

    // Render white outline around the whole tree trunk when the player looks at a log with an axe in hand
    public static boolean onBlockOutline(BlockGroupOutlineContext group) {
        var player = GameUtil.player();
        var level = GameUtil.level();
        if (player == null || level == null) return true;
        var state = level.getBlockState(group.outline()./*? if >= 1.21.11 {*//*pos()*//*?} else {*/blockPos()/*?}*/);
        if (!Timber.canSeeTree(player, state)) return true;
        float rgb = OutlineRGB.get();
        return BlockGroupOutlineRenderer.render(group, player, Timber::isLog, Timber.MAX_RADIUS, Timber.MAX_TRUNK, 0.6f, rgb, rgb, rgb, true);
    }
}