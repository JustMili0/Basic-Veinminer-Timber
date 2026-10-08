package net.justmili.bvat.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.justmili.bvat.core.util.client.GameUtil;
import net.justmili.bvat.core.util.search.SearchAlgorithms;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

//? if < 26.2 {
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
//?}
//? if >= 26.1 {
//?} else if >= 1.21.11 {
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
//?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
*///?}
//? if >= 1.21.11 {
import net.minecraft.util.ARGB;
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?} else {
/*import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
*///?}
//? if >= 1.21.11 && < 26.2 {
import net.minecraft.client.renderer.ShapeRenderer;
 //?}

/**
 * Outlines a whole group of connected blocks instead of just the one the player is looking at.
 * Does not outline every single block individually in group, only the group as a whole.
 * Call {@link #render} from the block outline event and return what it gives back.
 * <p>
 * The group is cached and refreshed every 10 ticks. The cache only looks at the targeted block, not stateMatch,
 * so two outliners on the same block would end up sharing a shape.
 */
@Environment(EnvType.CLIENT)
public class BlockGroupOutlineRenderer {
    private static BlockPos originCache;
    private static long tickBucketCache;
    private static VoxelShape shapeCache;

    /**
     * Outlines every block connected to the targeted one that passes stateMatch.
     * Callers decide if it should show at all, this only draws.
     * <p>
     * maxRadius and maxSize are passed to the search, color and opacity go from 0 to 1.
     * If the targeted block is on its own there's nothing to group, so vanilla's outline is left alone.
     *
     * @return true to let vanilla draw its own outline, false to cancel it.
     */
    public static boolean render(BlockGroupOutlineContext group, Player player, Predicate<BlockState> stateMatch, int maxRadius, int maxSize,
                                 float a, float r, float g, float b, boolean renderVanillaOutline) {
        var level = GameUtil.level();
        var stack = group.poseStack();
        //? if >= 26.2 {
        /*var collector = group.collector();
         *///?} else {
        var buffers = group.buffers();
        //?}
        if (level == null/*? if < 1.21.11 {*//*|| stack == null || buffers == null*//*?}*/) return true;

        var pos = group.blockPos();
        var shape = getCachedShape(level, player, pos, level.getBlockState(pos), stateMatch, maxRadius, maxSize);
        if (shape == null) return true; // single block, vanilla outline is fine

        var camera = group.cameraPos();

        //? if >= 26.2 {
        /*stack.pushPose();
        stack.translate(-camera.x, -camera.y, -camera.z);
        collector.submitShapeOutline(stack, shape, RenderTypes.lines(), ARGB.colorFromFloat(a, r, g, b), GameUtil.window().getAppropriateLineWidth(), false);
        stack.popPose();
        *///?} else if >= 1.21.11 {
        var buffer = buffers.getBuffer(RenderTypes.lines());
        renderOutlineShape(stack, buffer, shape, -camera.x, -camera.y, -camera.z, a, r, g, b);
        //?} else {
        /*var buffer = buffers.getBuffer(RenderType.lines());
        renderOutlineShape(stack, buffer, shape, -camera.x, -camera.y, -camera.z, a, r, g, b);
        *///?}
        return renderVanillaOutline;
    }

    public static boolean render(BlockGroupOutlineContext group, Player player, Predicate<BlockState> stateMatch, int maxRadius, int maxSize, boolean renderVanillaOutline) {
        // Render with default outline ARGB values
        return render(group, player, stateMatch, maxRadius, maxSize, 0.4f, 0f, 0f, 0f, renderVanillaOutline);
    }

    // Get cached group shape, rebuild it every 10 ticks or if the targeted block changed
    private static VoxelShape getCachedShape(ClientLevel level, Player player, BlockPos origin, BlockState state, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        long tickBucket = level.getGameTime() / 10; // refresh at most every 10 ticks
        if (!origin.equals(originCache) || tickBucket != tickBucketCache) {
            originCache = origin.immutable();
            tickBucketCache = tickBucket;
            shapeCache = buildOutlineShape(level, player, origin, state, stateMatch, maxRadius, maxSize);
        }
        return shapeCache;
    }

    // Get shape of block group
    private static VoxelShape buildOutlineShape(ClientLevel level, Player player, BlockPos origin, BlockState state, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        var found = SearchAlgorithms.BREADTH_FIRST_SEARCH.search(level, origin, stateMatch, maxRadius, maxSize);
        if (found.isEmpty()) return null;

        var collision = CollisionContext.of(player);
        var shape = state.getShape(level, origin, collision).move(origin.getX(), origin.getY(), origin.getZ());
        for (var pos : found) {
            shape = Shapes.or(shape, level.getBlockState(pos).getShape(level, pos, collision).move(pos.getX(), pos.getY(), pos.getZ()));
        }
        return shape;
    }

    // Draw group outlines (pre-26.2 only, 26.2+ submits straight from render)
    //? if >= 1.21.11 && < 26.2 {
    private static void renderOutlineShape(PoseStack stack, VertexConsumer buffer, VoxelShape shape, double xOffset, double yOffset, double zOffset, float a, float r, float g, float b) {
        ShapeRenderer.renderShape(stack, buffer, shape, xOffset, yOffset, zOffset, ARGB.colorFromFloat(a, r, g, b), GameUtil.window().getAppropriateLineWidth());
    }
    //?} else if < 1.21.11 {
    /*private static void renderOutlineShape(PoseStack stack, VertexConsumer buffer, VoxelShape shape, double xOffset, double yOffset, double zOffset, float a, float r, float g, float b) {
        LevelRenderer.renderShape(stack, buffer, shape, xOffset, yOffset, zOffset, r, g, b, a);
    }
    *///?}
}