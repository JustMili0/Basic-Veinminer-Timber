package net.justmili.bvat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.justmili.bvat.core.util.Maths;
import net.justmili.bvat.core.util.search.SearchAlgorithms;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public class BlockGroupHighlighter {
    private static BlockPos originCache;
    private static long bucketCache;
    private static VoxelShape shapeCache;

    /**
     * Outlines every block connected to the targeted one that passes stateMatch.
     * Callers decide if it should show at all, this only draws.
     *
     * @return true to let vanilla draw its own outline, false to cancel it.
     */
    public static boolean render(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline, Player player,
                                 Predicate<BlockState> stateMatch, int maxRadius, int maxSize, float red, float green, float blue, float opacity) {
        var level = context.world();
        var matrix = context.matrixStack();
        var consumer = context.consumers();
        if (level == null || matrix == null || consumer == null) return true;

        var shape = getGroupShape(level, player, outline.blockPos(), outline.blockState(), stateMatch, maxRadius, maxSize);
        if (shape == null) return true; // single block, vanilla outline is fine

        renderOutlineShape(matrix, consumer.getBuffer(RenderType.lines()), shape, -outline.cameraX(), -outline.cameraY(), -outline.cameraZ(), red, green, blue, opacity);
        return false;
    }

    public static boolean render(WorldRenderContext context, WorldRenderContext.BlockOutlineContext outline, Player player,
                                 Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        // Render with default outline color
        return render(context, outline, player, stateMatch, maxRadius, maxSize, 0f, 0f, 0f, 0.4f);
    }

    private static VoxelShape getGroupShape(ClientLevel level, Player player, BlockPos origin, BlockState state,
                                            Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        long bucket = level.getGameTime() / 10; // refresh at most every 10 ticks
        if (!origin.equals(originCache) || bucket != bucketCache) {
            originCache = origin.immutable();
            bucketCache = bucket;
            shapeCache = buildOutlineShape(level, player, origin, state, stateMatch, maxRadius, maxSize);
        }
        return shapeCache;
    }

    private static VoxelShape buildOutlineShape(ClientLevel level, Player player, BlockPos origin, BlockState state,
                                                Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        var found = SearchAlgorithms.BREADTH_FIRST_SEARCH.search(level, origin, stateMatch, maxRadius, maxSize);
        if (found.isEmpty()) return null;

        var collision = CollisionContext.of(player);
        var shape = state.getShape(level, origin, collision).move(origin.getX(), origin.getY(), origin.getZ());
        for (var pos : found) {
            shape = Shapes.or(shape, level.getBlockState(pos).getShape(level, pos, collision).move(pos.getX(), pos.getY(), pos.getZ()));
        }
        return shape;
    }

    private static void renderOutlineShape(PoseStack stack, VertexConsumer buffer, VoxelShape shape, double xOffset, double yOffset, double zOffset,
                                           float red, float green, float blue, float opacity) {
        var pose = stack.last();
        shape.forAllEdges((x1, y1, z1, x2, y2, z2) -> {
            float nx = (float) (x2 - x1);
            float ny = (float) (y2 - y1);
            float nz = (float) (z2 - z1);
            float length = (float) Maths.length(nx, ny, nz);
            nx /= length;
            ny /= length;
            nz /= length;

            buffer.addVertex(pose, (float) (x1 + xOffset), (float) (y1 + yOffset), (float) (z1 + zOffset))
                .setColor(red, green, blue, opacity).setNormal(pose, nx, ny, nz);
            buffer.addVertex(pose, (float) (x2 + xOffset), (float) (y2 + yOffset), (float) (z2 + zOffset))
                .setColor(red, green, blue, opacity).setNormal(pose, nx, ny, nz);
        });
    }
}