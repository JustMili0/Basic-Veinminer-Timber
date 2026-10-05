package net.justmili.vat.content.mechanics.logic.search;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class CubeSearch implements SearchAlgorithm {

    @Override
    public List<BlockPos> search(ServerLevel level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        var found = new ArrayList<BlockPos>();

        for (var pos : withinClosed(origin, maxRadius)) {
            if (stateMatch.test(level.getBlockState(pos))) found.add(pos.immutable());
        }

        found.sort(Comparator.comparingDouble(pos -> pos.distSqr(origin)));
        return found.size() > maxSize? new ArrayList<>(found.subList(0, maxSize)) : found;
    }
}