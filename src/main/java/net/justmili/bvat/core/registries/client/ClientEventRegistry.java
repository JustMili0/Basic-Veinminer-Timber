package net.justmili.bvat.core.registries.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.justmili.bvat.client.renderer.TreeTrunkOutline;
import net.justmili.bvat.client.renderer.VeinBlobOutline;

@Environment(EnvType.CLIENT)
public class ClientEventRegistry {

    public static void init() {
        WorldRenderEvents.BLOCK_OUTLINE.register(VeinBlobOutline::onBlockOutline);
        WorldRenderEvents.BLOCK_OUTLINE.register(TreeTrunkOutline::onBlockOutline);
    }
}