package net.justmili.vat;

import net.fabricmc.api.ModInitializer;
import net.justmili.vat.core.registries.EventRegistry;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vat implements ModInitializer {
    public static final String ID = "vat";
    public static final String NAME = "Basic Veinminer & Timber";
    public static final String BUILD = "0.0.1a";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    @Override
    public void onInitialize() {
        EventRegistry.init();
    }

    public static ResourceLocation asId(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
