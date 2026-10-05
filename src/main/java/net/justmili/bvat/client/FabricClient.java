package net.justmili.bvat.client;

import net.fabricmc.api.ClientModInitializer;
import net.justmili.bvat.core.registries.client.ClientEventRegistry;

public class FabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEventRegistry.init();
    }
}
