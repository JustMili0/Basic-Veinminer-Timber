package net.justmili.vat.content.mechanics.logic;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.justmili.vat.core.util.TagUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

public class Veinminer {

    public static void onBlockBroken(ServerLevel level, ServerPlayer player, BlockState state, BlockPos pos, boolean wasBroken) {
        if (player.isCreative()
            || !player.isShiftKeyDown()
            || !wasBroken
            || !state.is(ConventionalBlockTags.ORES)
        ) return;

        var tool = player.getMainHandItem();
        if (tool.isEmpty()) return;

        for (var target : BlockBreaking.breadthFirstSearch(level, pos, TagUtil.match(state), 16, 64)) {
            if (!BlockBreaking.canAfford(tool)) break;
            BlockBreaking.block(level, player, tool, level.getBlockState(target), target, false);
        }
    }
}