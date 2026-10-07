package net.justmili.bvat.client.renderer;

//? if >= 26.1 {
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
//?} else if >= 1.21.11 {
/*import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
*///?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
 *///?}
import net.justmili.bvat.content.mechanics.logic.Timber;
import net.justmili.bvat.core.util.client.GameUtil;
import net.justmili.bvat.core.util.client.OutlineShade;

public class TreeTrunkOutline {

    // Render white outline around the whole tree trunk when the player looks at a log with an axe in hand
    public static boolean onBlockOutline(/*? if >= 26.1 {*/LevelRenderContext/*?} else {*//*WorldRenderContext*//*?}*/ context, /*? if >= 1.21.11 {*/BlockOutlineRenderState/*?} else {*//*WorldRenderContext.BlockOutlineContext*//*?}*/ outline) {
        var player = GameUtil.player();
        var level = GameUtil.level();
        if (player == null || level == null) return true;
        var state = level.getBlockState(outline./*? if >= 1.21.11 {*/pos()/*?} else {*//*blockPos()*//*?}*/);
        if (!Timber.canSeeTree(player, state)) return true;
        float rgb = OutlineShade.get(player);
        return BlockGroupOutliner.render(context, outline, player, Timber::isLog, Timber.MAX_RADIUS, Timber.MAX_TRUNK, 0.6f, rgb, rgb, rgb, true);
    }
}