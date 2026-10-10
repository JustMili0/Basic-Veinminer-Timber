package net.justmili.vnt.client;

import net.fabricmc.api.ClientModInitializer;
import net.justmili.vnt.config.Config;
import net.justmili.vnt.core.registries.client.ClientEventRegistry;

public class FabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Config.initClient();
        ClientEventRegistry.init();
    }
}