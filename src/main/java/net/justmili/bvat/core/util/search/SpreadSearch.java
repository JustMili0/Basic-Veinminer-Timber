package net.justmili.bvat.core.util.search;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


public class SpreadSearch implements SearchAlgorithm {

    @Override
    public List<BlockPos> search(Level level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        return List.of();
    }

    @Override
    public List<BlockPos> biSearch(Level level, Collection<BlockPos> origins, BiPredicate<BlockState, BlockState> canSpread, int maxSize) {
        var found = new ArrayList<BlockPos>();
        var visited = new LongOpenHashSet();
        var queue = new ArrayDeque<BlockPos>();

        // Count each pos of origins as visited so flood never walks back into them
        for (var pos : origins) {
            visited.add(pos.asLong());
            queue.add(pos);
        }

        while (!queue.isEmpty() && found.size() < maxSize) {
            var current = queue.poll();
            var state = level.getBlockState(current);

            for (var direction : Direction.values()) {
                if (found.size() >= maxSize) break;

                var next = current.relative(direction);
                if (!visited.add(next.asLong())) continue;
                if (!canSpread.test(state, level.getBlockState(next))) continue;

                found.add(next);
                queue.add(next);
            }
        }
        return found;
    }

    public List<BlockPos> biSearch(Level level, BlockPos origin, BiPredicate<BlockState, BlockState> canSpread, int maxSize) {
        return biSearch(level, List.of(origin), canSpread, maxSize);
    }
}
