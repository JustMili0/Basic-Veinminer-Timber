package net.justmili.bvat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;

//? if = 1.21.1 {
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
//?} else if >= 1.21.11 && < 26.1 {
/*import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
*///?} else if >= 26.1 {
/*import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
*///?}

/**
 * Idk something to lesser the stonecutter template mess in BlockGroupOutlineRenderer
 */
public record BlockGroupOutlineContext(/*? if >= 26.1 {*//*LevelRenderContext*//*?} else {*/WorldRenderContext/*?}*/ context, /*? if >= 1.21.11 {*//*BlockOutlineRenderState*//*?} else {*/WorldRenderContext.BlockOutlineContext/*?}*/ outline) {

    public PoseStack poseStack() {
        return context./*? if >= 26.1 {*//*poseStack()*//*?} else if >= 1.21.11 {*//*matrices()*//*?} else {*/matrixStack()/*?}*/;
    }

    public Vec3 cameraPos() {
        return /*? if >= 26.1 {*//*context.levelState().cameraRenderState.pos*//*?} else if >= 1.21.11 {*//*context.worldState().cameraRenderState.pos*//*?} else {*/new Vec3(outline.cameraX(), outline.cameraY(), outline.cameraZ())/*?}*/;
    }
}