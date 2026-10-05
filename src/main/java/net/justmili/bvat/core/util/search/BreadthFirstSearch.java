package net.justmili.bvat.core.util.search;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Searches connected blocks using breadth-first traversal.
 * Blocks are searched starting from the origin and expanding outwards.
 */
public class BreadthFirstSearch implements SearchAlgorithm {

    @Override
    public List<BlockPos> search(Level level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        var found = new ArrayList<BlockPos>();

        // Include origin in traversal so the search can expand from it
        BlockPos.breadthFirstTraversal(origin, maxRadius, maxSize + 1, (current, queue) -> {
            for (var next : withinClosed(current, 1)) queue.accept(next.immutable());
        }, current -> {
            if (current.equals(origin)) return true;
            if (!stateMatch.test(level.getBlockState(current))) return false;
            found.add(current);
            return true;
        });

        return found;
    }
}