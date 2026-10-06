package net.justmili.bvat.core.registries;

import net.justmili.bvat.content.mechanics.logic.Timber;
import net.justmili.bvat.content.mechanics.logic.Veinminer;
import net.justmili.bvat.core.api.BlockBreakEvent;

public class EventRegistry {

    public static void init() {
        BlockBreakEvent.BLOCK_BROKEN.register(Veinminer::mineOreVein);
        BlockBreakEvent.BLOCK_BROKEN.register(Timber::chopDownTree);
    }
}