package net.justmili.vat.content.mechanics.logic.search;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BreadthFirstSearch implements SearchAlgorithm {

    @Override
    public List<BlockPos> search(ServerLevel level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        var found = new ArrayList<BlockPos>();

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