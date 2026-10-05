package net.justmili.bvat.core.util;
public class Maths {

    public static double lengthSqrd(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static double length(double x, double y, double z) {
        return Math.sqrt(lengthSqrd(x, y, z));
    }
}