package net.justmili.vat.core.registries.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.justmili.vat.client.renderer.VeinBlobHighlighter;

@Environment(EnvType.CLIENT)
public class ClientEventRegistry {

    public static void init() {
        WorldRenderEvents.BLOCK_OUTLINE.register(VeinBlobHighlighter::onBlockOutline);
    }
}
