package net.justmili.bvat.core.util;

import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

// Stripped down tag util from Millie's Core Libraries. Temporary until I switch this to actually make this use the library
public class TagUtil {

    // This will have proper documentation in Core Libs, not here
    public static Predicate<BlockState> matchOre(BlockState state) {
        var tags = state.getTags().filter(tag -> tag.location().getNamespace().equals("c") && tag.location().getPath().startsWith("ores/")).toList();
        if (tags.isEmpty()) return s -> s.is(state.getBlock());
        return s -> tags.stream().anyMatch(s::is);
    }
}