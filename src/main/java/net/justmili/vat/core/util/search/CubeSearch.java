package net.justmili.vat.core.util.search;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class CubeSearch implements SearchAlgorithm {

    @Override
    public List<BlockPos> search(Level level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        var found = new ArrayList<BlockPos>();

        for (var pos : withinClosed(origin, maxRadius)) {
            if (stateMatch.test(level.getBlockState(pos))) found.add(pos.immutable());
        }

        found.sort(Comparator.comparingDouble(pos -> pos.distSqr(origin)));
        return found.size() > maxSize? new ArrayList<>(found.subList(0, maxSize)) : found;
    }
}