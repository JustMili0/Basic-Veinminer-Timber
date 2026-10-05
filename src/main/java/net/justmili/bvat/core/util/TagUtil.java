package net.justmili.bvat.core.util;

import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class TagUtil {

    public static Predicate<BlockState> match(BlockState origin) {
        var tags = origin.getTags().filter(tag -> tag.location().getNamespace().equals("c") && tag.location().getPath().startsWith("ores/")).toList();
        if (tags.isEmpty()) return state -> state.is(origin.getBlock());
        return state -> tags.stream().anyMatch(state::is);
    }
}
