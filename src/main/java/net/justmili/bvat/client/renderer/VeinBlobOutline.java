package net.justmili.bvat.client.renderer;

//? if >= 1.21.11 {
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
//?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
 *///?}
import net.justmili.bvat.content.mechanics.logic.Veinminer;
import net.justmili.bvat.core.util.TagUtil;
import net.justmili.bvat.core.util.client.GameUtil;
import net.justmili.bvat.core.util.client.OutlineShade;

public class VeinBlobOutline {

    // Render white outline around the whole ore vein when the player looks at it with a pickaxe in hand
    public static boolean onBlockOutline(WorldRenderContext context, /*? if >= 1.21.11 {*/BlockOutlineRenderState/*?} else {*//*WorldRenderContext.BlockOutlineContext*//*?}*/ outline) {
        var player = GameUtil.player();
        var level = GameUtil.level();
        if (player == null || level == null) return true;
        var state = level.getBlockState(outline./*? if >= 1.21.11 {*/pos()/*?} else {*//*blockPos()*//*?}*/);
        if (!Veinminer.canSeeVein(player, state)) return true;
        float rgb = OutlineShade.get(player);
        return BlockGroupOutliner.render(context, outline, player, TagUtil.matchOre(state), Veinminer.MAX_RADIUS, Veinminer.MAX_VEIN, 0.6f, rgb, rgb, rgb, true);
    }
}