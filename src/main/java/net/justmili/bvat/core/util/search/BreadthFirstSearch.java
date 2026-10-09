package net.justmili.bvat.core.util.search;

import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Searches connected blocks using breadth-first traversal.
 * Blocks are searched starting from the origin and expanding outwards, so the closest ones come first.
 * <p>
 * Diagonals count as connected and origin is not included in the result.
 * Radius is counted in steps through the group, not straight line distance.
 */
public class BreadthFirstSearch implements SearchAlgorithm {

    @Override
    public List<BlockPos> search(Level level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        var found = new ArrayList<BlockPos>();
        var visited = new LongOpenHashSet();
        var queue = new LongArrayFIFOQueue();

        visited.add(origin.asLong());
        queue.enqueue(origin.asLong());

        for (var depth = 0; depth < maxRadius && !queue.isEmpty(); depth++) {
            for (var remaining = queue.size(); remaining > 0; remaining--) {
                var current = queue.dequeueLong();

                for (var next : withinClosed(BlockPos.getX(current), BlockPos.getY(current), BlockPos.getZ(current), 1)) {
                    if (found.size() >= maxSize) return found;
                    if (!visited.add(next.asLong()) || !stateMatch.test(level.getBlockState(next))) continue;
                    found.add(next.immutable());
                    queue.enqueue(next.asLong());
                }
            }
        }
        return found;
    }

    @Override
    public List<BlockPos> biSearch(Level level, Collection<BlockPos> origins, BiPredicate<BlockState, BlockState> canSpread, int maxSize) {
        return List.of();
    }
}