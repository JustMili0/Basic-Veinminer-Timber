package net.justmili.bvat.core.api;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

// Temporary event class, to be replaced with Core Libs' player block break event one its made
public class BlockBreakEvent {
    private BlockBreakEvent() {
    }

    public static final Event<BreakBlock> BLOCK_BROKEN = Event.create(BreakBlock.class, callbacks -> (level, player, state, pos, wasBroken) -> {
        for (BreakBlock callback : callbacks) callback.onBlockBroken(level, player, state, pos, wasBroken);
    });

    @FunctionalInterface
    public interface BreakBlock {
        void onBlockBroken(ServerLevel level, ServerPlayer player, BlockState state, BlockPos pos, boolean wasBroken);
    }
}