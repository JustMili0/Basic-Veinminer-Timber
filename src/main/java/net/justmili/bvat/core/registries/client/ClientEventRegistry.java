package net.justmili.bvat.core.registries.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//? if >= 26.1 {
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
//?} else if >= 1.21.11 {
/*import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
 *///?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
 *///?}
import net.justmili.bvat.client.renderer.TreeTrunkOutline;
import net.justmili.bvat.client.renderer.VeinBlobOutline;

@Environment(EnvType.CLIENT)
public class ClientEventRegistry {

    public static void init() {
        //? if >= 26.1 {
        var event = LevelRenderEvents.BEFORE_BLOCK_OUTLINE;
        //?} else if >= 1.21.11 {
        /*var event = WorldRenderEvents.BEFORE_BLOCK_OUTLINE;
         *///?} else {
        /*var event = WorldRenderEvents.BLOCK_OUTLINE;
         *///?}
        event.register(VeinBlobOutline::onBlockOutline);
        event.register(TreeTrunkOutline::onBlockOutline);
    }
}