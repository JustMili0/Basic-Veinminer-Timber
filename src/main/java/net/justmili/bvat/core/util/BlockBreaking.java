package net.justmili.bvat.core.util;

import net.justmili.bvat.content.mechanics.logic.Timber;
import net.justmili.bvat.core.util.search.SearchAlgorithms;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BlockBreaking {

    public static boolean canAfford(ItemStack tool) {
        if (!tool.isDamageableItem()) return true;
        return tool.getMaxDamage() - tool.getDamageValue() > 1;
    }

    /**
     * Using {@code Block.dropResources(state, level, pos, level.getBlockEntity(pos), player, tool)} and
     * {@code state.spawnAfterBreak(level, pos, tool, true)} instead of {@code player.gameMode.destroyBlock(pos)}
     * to avoid recursion in destroyBlock event call
     */
    public static void destroy(ServerLevel level, ServerPlayer player, ItemStack tool, BlockState state, BlockPos pos, boolean spawnParticles, boolean damageTool) {
        if (state.isAir() || state.getDestroySpeed(level, pos) < 0) return;
        if (!player.hasCorrectToolForDrops(state)) return;

        Block.dropResources(state, level, pos, level.getBlockEntity(pos), player, tool);
        state.spawnAfterBreak(level, pos, tool, true);
        if (spawnParticles) level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));

        level.setBlock(pos, state.getFluidState().createLegacyBlock(), Block.UPDATE_ALL);
        if (damageTool) {
            tool.mineBlock(level, state, pos, player);
        } else {
            // Still award player for breaking the block even if tool damaging is disabled
            player.awardStat(Stats.ITEM_USED.get(tool.getItem()));
        }
        level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(player, state));
        player.awardStat(Stats.BLOCK_MINED.get(state.getBlock()));
    }

    public static void destroy(ServerLevel level, ServerPlayer player, ItemStack tool, BlockState state, BlockPos pos, boolean spawnParticles) {
        BlockBreaking.destroy(level, player, tool, state, pos, spawnParticles, true);
    }

    public static List<BlockPos> breadthFirstSearch(Level level, BlockPos origin, Predicate<BlockState> stateMatch, int maxRadius, int maxSize) {
        return SearchAlgorithms.BREADTH_FIRST_SEARCH.search(level, origin, stateMatch, maxRadius, maxSize);
    }

    public static List<BlockPos> spreadSearch(Level level, BlockPos origin, List<BlockPos> blocks) {
        var column = new ArrayList<>(blocks);
        column.add(origin);
        return SearchAlgorithms.SPREAD_SEARCH.biSearch(level, column, Timber::isLeafInRange, Timber.MAX_LEAVES);
    }
}