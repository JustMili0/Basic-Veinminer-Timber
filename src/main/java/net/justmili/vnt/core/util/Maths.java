package net.justmili.vnt.core.util;

// Stripped down math util from Millie's Core Libraries. Temporary until I switch this to actually make this use the library
public class Maths {

    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float clamp(float value, float min, float max) {
        return value < min? min : Math.min(value, max);
    }
}