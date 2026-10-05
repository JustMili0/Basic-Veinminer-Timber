package net.justmili.bvat.core.util;

// Stripped down math util from Millie's Core Libraries. Temporary until I switch this to actually make this use the library
public class Maths {

    public static double lengthSqrd(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static double length(double x, double y, double z) {
        return Math.sqrt(lengthSqrd(x, y, z));
    }
}