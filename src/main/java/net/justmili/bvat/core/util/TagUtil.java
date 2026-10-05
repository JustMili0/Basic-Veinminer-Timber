package net.justmili.bvat.core.util;

import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

// Stripped down tag util from Millie's Core Libraries. Temporary until I switch this to actually make this use the library
public class TagUtil {

    /**
     * Creates a predicate that matches blocks with the same ore tags as the origin.
     * Falls back to matching the same block if no ore tags are found.
     *
     * @param state the block state to match against
     * @return a predicate that matches blocks with the same ore tags or block
     */
    public static Predicate<BlockState> match(BlockState state) {
        var tags = state.getTags().filter(tag -> tag.location().getNamespace().equals("c") && tag.location().getPath().startsWith("ores/")).toList();
        if (tags.isEmpty()) return s -> s.is(state.getBlock());
        return s -> tags.stream().anyMatch(s::is);
    }
}