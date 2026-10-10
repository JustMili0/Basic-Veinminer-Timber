package net.justmili.vnt.config;

import net.justmili.vnt.core.util.Maths;

public record FloatColor(float a, float r, float g, float b) {

    public FloatColor {
        a = Maths.clamp(a, 0f, 1f);
        r = Maths.clamp(r, 0f, 1f);
        g = Maths.clamp(g, 0f, 1f);
        b = Maths.clamp(b, 0f, 1f);
    }

    public FloatColor(float r, float g, float b) {
        this(1f, r, g, b);
    }

    public FloatColor(float a, float rgb) {
        this(a, rgb, rgb, rgb);
    }

    public FloatColor(float rgb) {
        this(1f, rgb, rgb, rgb);
    }
}