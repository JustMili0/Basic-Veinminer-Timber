package net.justmili.bvat.core.registries.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.justmili.bvat.client.renderer.TreeTrunkOutline;
import net.justmili.bvat.client.renderer.VeinBlobOutline;

@Environment(EnvType.CLIENT)
public class ClientEventRegistry {

    public static void init() {
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register(VeinBlobOutline::onBlockOutline);
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register(TreeTrunkOutline::onBlockOutline);
    }
}