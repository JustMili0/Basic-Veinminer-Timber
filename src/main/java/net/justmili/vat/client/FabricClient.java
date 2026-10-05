package net.justmili.vat.client;

import net.fabricmc.api.ClientModInitializer;
import net.justmili.vat.core.registries.client.ClientEventRegistry;

public class FabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEventRegistry.init();
    }
}
