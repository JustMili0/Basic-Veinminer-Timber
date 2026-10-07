package net.justmili.bvat.client.renderer;

//? if = 1.21.1 {
/*import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
*///?} else if >= 1.21.11 {
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
//?}
import net.justmili.bvat.content.mechanics.logic.Veinminer;
import net.justmili.bvat.core.util.TagUtil;
import net.justmili.bvat.core.util.client.GameUtil;
import net.justmili.bvat.core.util.client.OutlineShade;

public class VeinBlobOutline {

    // Render white outline around the whole ore vein when the player looks at it with a pickaxe in hand
    //? if = 1.21.1 {
    /*public static boolean onBlockOutline(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline) {
     *///?} else if >= 1.21.11 {
    public static boolean onBlockOutline(WorldRenderContext context, BlockOutlineRenderState outline) {
        //?}
        var player = GameUtil.player();
        //? if = 1.21.1 {
        /*if (player == null || !Veinminer.canSeeVein(player, outline.blockState())) return true;*/
        //?} else if >= 1.21.11 {
        var level = GameUtil.level();
        if (player == null || level == null) return true;
        var state = level.getBlockState(outline.pos());
        if (!Veinminer.canSeeVein(player, state)) return true;
        //?}
        float rgb = OutlineShade.get(player);
        return BlockGroupOutliner.render(context, outline, player, TagUtil.matchOre(state), Veinminer.MAX_RADIUS, Veinminer.MAX_VEIN, 0.6f, rgb, rgb, rgb, true);
    }
}