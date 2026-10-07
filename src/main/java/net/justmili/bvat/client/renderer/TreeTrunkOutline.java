package net.justmili.bvat.client.renderer;

//? if = 1.21.1 {
/*import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
*///?} else if >= 1.21.11 {
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
//?}
import net.justmili.bvat.content.mechanics.logic.Timber;
import net.justmili.bvat.core.util.client.GameUtil;
import net.justmili.bvat.core.util.client.OutlineShade;

public class TreeTrunkOutline {

    // Render white outline around the whole tree trunk when the player looks at a log with an axe in hand
    //? if = 1.21.1 {
    /*public static boolean onBlockOutline(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline) {
    *///?} else if >= 1.21.11 {
    public static boolean onBlockOutline(WorldRenderContext context, BlockOutlineRenderState outline) {
        //?}
        var player = GameUtil.player();
        //? if = 1.21.1 {
        /*if (player == null || !Timber.canSeeTree(player, outline.blockState())) return true;
        *///?} else if >= 1.21.11 {
        var level = GameUtil.level();
        if (player == null || level == null || !Timber.canSeeTree(player, level.getBlockState(outline.pos()))) return true;
        //?}
        float rgb = OutlineShade.get(player);
        return BlockGroupOutliner.render(context, outline, player, Timber::isLog, Timber.MAX_RADIUS, Timber.MAX_TRUNK, 0.6f, rgb, rgb, rgb, true);
    }
}