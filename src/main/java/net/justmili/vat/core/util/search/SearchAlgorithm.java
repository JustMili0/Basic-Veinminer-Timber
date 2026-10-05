package net.justmili.vat.core.util.search;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Predicate;

@FunctionalInterface
public interface SearchAlgorithm {

    /**
     * Iterates a cube around center. The returned pos is reused between iterations; call .immutable() before storing it.
     */
    default Iterable<BlockPos> withinClosed(BlockPos center, int radius) {
        return BlockPos.betweenClosed(center.offset(-radius, -radius, -radius), center.offset(radius, radius, radius));
    }

    List<BlockPos> search(Level level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize);
}