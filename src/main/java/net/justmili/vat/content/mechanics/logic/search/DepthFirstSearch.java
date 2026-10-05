package net.justmili.vat.content.mechanics.logic.search;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DepthFirstSearch implements SearchAlgorithm {

    @Override
    public List<BlockPos> search(ServerLevel level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        var found = new ArrayList<BlockPos>();
        var visited = new LongOpenHashSet();
        var stack = new ArrayDeque<BlockPos>();
        var limit = new BoundingBox(origin).inflatedBy(maxRadius);

        visited.add(origin.asLong());
        stack.push(origin);

        while (!stack.isEmpty() && found.size() < maxSize) {
            var current = stack.pop();

            for (var n : withinClosed(current, 1)) {
                if (found.size() >= maxSize) break;
                if (!visited.add(n.asLong()) || !limit.isInside(n)) continue;
                if (!stateMatch.test(level.getBlockState(n))) continue;

                var next = n.immutable();
                found.add(next);
                stack.push(next);
            }
        }

        return found;
    }
}