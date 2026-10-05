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
    private BlockState vat$stateBeforeBreak;

    @Inject(method = "destroyBlock", at = @At("HEAD"))
    private void vat$captureState(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        this.vat$stateBeforeBreak = this.level.getBlockState(pos);
    }

    @Inject(method = "destroyBlock", at = @At("RETURN"))
    private void vat$getDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockBreakEvent.BLOCK_BROKEN.invoker().onBlockBroken(this.level, this.player, this.vat$stateBeforeBreak, pos, cir.getReturnValueZ());
        this.vat$stateBeforeBreak = null;
    }
}