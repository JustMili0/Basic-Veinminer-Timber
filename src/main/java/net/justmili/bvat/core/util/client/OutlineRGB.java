package net.justmili.bvat.core.util.client;

public class OutlineRGB {

    public static float get() {
        return GameUtil.player().isShiftKeyDown()? 1f : 0.35f;
    }
}
