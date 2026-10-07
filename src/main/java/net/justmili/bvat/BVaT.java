package net.justmili.bvat;

import net.fabricmc.api.ModInitializer;
import net.justmili.bvat.core.registries.EventRegistry;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BVaT implements ModInitializer {
    public static final String ID = "bvat";
    public static final String NAME = "Basic Veinminer & Timber";
    public static final String BUILD = "1.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {} ({}) version {}", NAME, ID, BUILD);
        EventRegistry.init();
    }
}