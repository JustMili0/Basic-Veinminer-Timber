package net.justmili.vat.content.mechanics.logic.search;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public enum SearchAlgorithms {
    BREADTH_FIRST_SEARCH(new BreadthFirstSearch()),
    DEPTH_FIRST_SEARCH(new DepthFirstSearch()),
    CUBE_SEARCH(new CubeSearch());

    private final SearchAlgorithm algorithm;

    SearchAlgorithms(SearchAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public List<BlockPos> search(ServerLevel level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        return algorithm.search(level, origin, stateMatch, maxRadius, maxSize);
    }

    public static Optional<SearchAlgorithms> fromString(String name) {
        for (var value : values()) if (value.name().equalsIgnoreCase(name)) return Optional.of(value);
        return Optional.empty();
    }
}