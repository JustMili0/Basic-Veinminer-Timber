package net.justmili.bvat.core.mixin;

import net.justmili.bvat.core.api.BlockBreakEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Shadow
    protected ServerLevel level;

    @Shadow
    @Final
    protected ServerPlayer player;

    @Unique
    private BlockState bvat$stateBefore;

    // Get BlockState of the block player is breaking
    @Inject(method = "destroyBlock", at = @At("HEAD"))
    private void bvat$captureState(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        this.bvat$stateBefore = this.level.getBlockState(pos);
    }

    // Hook up event because Fabric API doesn't have a block breaking event
    @Inject(method = "destroyBlock", at = @At("RETURN"))
    private void bvat$getDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockBreakEvent.BLOCK_BROKEN.invoker().onBlockBroken(this.level, this.player, this.bvat$stateBefore, pos, cir.getReturnValueZ());
        this.bvat$stateBefore = null;
    }
}