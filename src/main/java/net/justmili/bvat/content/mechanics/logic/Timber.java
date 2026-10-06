package net.justmili.bvat.content.mechanics.logic;

import net.justmili.bvat.core.util.BlockBreaking;
import net.justmili.bvat.core.util.Maths;
import net.justmili.bvat.core.util.search.SearchAlgorithms;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class Timber {
    public static final int MAX_RADIUS = 32;
    public static final int MAX_TRUNK = 256;
    public static final int MAX_LEAVES = 256;

    public static boolean canSeeTree(Player player, BlockState state) {
        return !player.isCreative() && isLog(state) && player.getMainHandItem().is(ItemTags.AXES);
    }

    public static void chopDownTree(ServerLevel level, ServerPlayer player, BlockState state, BlockPos pos, boolean wasBroken) {
        if (!wasBroken || !(canSeeTree(player, state) && player.isShiftKeyDown())) return;

        var logs = BlockBreaking.breadthFirstSearch(level, pos, Timber::isLog, MAX_RADIUS, MAX_TRUNK);
        if (!isTree(level, pos, logs)) return;
        var leaves = getConnectedLeaves(level, pos, logs, MAX_LEAVES);

        // Break tree trunk
        var tool = player.getMainHandItem();
        for (var log : logs) {
            if (!BlockBreaking.canAfford(tool)) break; // Stop if tool durability is <= 1
            BlockBreaking.destroy(level, player, tool, level.getBlockState(log), log, true);
        }

        // A few leaf sounds instead of one per leaf to not make such a noise
        if (!leaves.isEmpty()) {
            var sounds = Maths.clamp(Math.round(leaves.size() * 0.01f), 1, leaves.size());
            for (int i = 0; i < sounds; i++) {
                var soundPos = leaves.get(i * leaves.size() / sounds);
                var sound = level.getBlockState(soundPos).getSoundType();
                level.playSound(null, soundPos, sound.getBreakSound(), SoundSource.BLOCKS, sound.getVolume(), sound.getPitch());
            }
        }
        // Break leaves, but don't damage tool
        for (var leaf : leaves) {
            BlockBreaking.destroy(level, player, tool, level.getBlockState(leaf), leaf, false, false);
        }
    }

    public static boolean isTree(Level level, BlockPos origin, List<BlockPos> logs) {
        if (isNaturalLeafConnected(level, origin)) return true;
        for (var log : logs) {
            if (isNaturalLeafConnected(level, log)) return true;
        }
        return false;
    }

    public static boolean isLog(BlockState state) {
        return state.is(BlockTags.LOGS);
    }

    public static List<BlockPos> getConnectedLeaves(Level level, BlockPos origin, List<BlockPos> logs, int maxSize) {
        var origins = new ArrayList<>(logs);
        origins.add(origin);
        return SearchAlgorithms.SPREAD_SEARCH.biSearch(level, origins, Timber::canSpread, maxSize);
    }

    public static boolean canSpread(BlockState from, BlockState to) {
        if (!isNaturalLeaf(to)) return false;
        if (!from.hasProperty(LeavesBlock.DISTANCE) || !to.hasProperty(LeavesBlock.DISTANCE)) return true;
        return to.getValue(LeavesBlock.DISTANCE) > from.getValue(LeavesBlock.DISTANCE);
    }

    private static boolean isNaturalLeafConnected(Level level, BlockPos center) {
        for (var pos : BlockPos.betweenClosed(center.offset(-1, -1, -1), center.offset(1, 1, 1))) {
            var state = level.getBlockState(pos);
            if (isNaturalLeaf(state)) return true;
        }
        return false;
    }

    // Include wart blocks to natural leaves because nether trees
    public static boolean isNaturalLeaf(BlockState state) {
        return (state.is(BlockTags.LEAVES) && state.hasProperty(LeavesBlock.PERSISTENT) && !state.getValue(LeavesBlock.PERSISTENT)) || state.is(BlockTags.WART_BLOCKS);
    }
}