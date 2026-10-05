package net.justmili.bvat.content.mechanics.logic;

import net.justmili.bvat.core.util.BlockBreaking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class Timber {
    public static final int MAX_RADIUS = 32;
    public static final int MAX_TREE_SIZE = 256;

    public static boolean canSeeTree(Player player, BlockState state) {
        return !player.isCreative() && state.is(BlockTags.LOGS) && player.getMainHandItem().is(ItemTags.AXES);
    }

    public static boolean canChopDown(Player player, BlockState state) {
        return canSeeTree(player, state) && player.isShiftKeyDown();
    }

    public static void onBlockBroken(ServerLevel level, ServerPlayer player, BlockState state, BlockPos pos, boolean wasBroken) {
        if (!wasBroken || !canChopDown(player, state)) return;

        var logs = BlockBreaking.breadthFirstSearch(level, pos, Timber::isLog, MAX_RADIUS, MAX_TREE_SIZE);
        if (!isTree(level, pos, logs)) return;

        var tool = player.getMainHandItem();
        // TODO: Add leaf breaking
        for (var target : logs) {
            if (!BlockBreaking.canAfford(tool)) break;
            BlockBreaking.block(level, player, tool, level.getBlockState(target), target, false);
        }
    }

    public static boolean isLog(BlockState state) {
        return state.is(BlockTags.LOGS);
    }

    /**
     * A tree is a group of logs touching at least one non-persistent (naturally generated) leaf.
     */
    public static boolean isTree(Level level, BlockPos origin, List<BlockPos> logs) {
        if (hasNaturalLeaves(level, origin)) return true;
        for (var log : logs) if (hasNaturalLeaves(level, log)) return true;
        return false;
    }

    private static boolean hasNaturalLeaves(Level level, BlockPos center) {
        for (var pos : BlockPos.betweenClosed(center.offset(-1, -1, -1), center.offset(1, 1, 1))) {
            var state = level.getBlockState(pos);
            if (state.is(BlockTags.LEAVES) && state.hasProperty(LeavesBlock.PERSISTENT) && !state.getValue(LeavesBlock.PERSISTENT)) return true;
        }
        return false;
    }
}