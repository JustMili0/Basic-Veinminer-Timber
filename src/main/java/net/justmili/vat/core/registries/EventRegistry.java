package net.justmili.vat.core.registries;

import net.justmili.vat.content.mechanics.logic.Veinminer;
import net.justmili.vat.core.api.BlockBreakEvent;

public class EventRegistry {

    public static void init() {
        BlockBreakEvent.BLOCK_BROKEN.register(Veinminer::onBlockBroken);
    }
}
