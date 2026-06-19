package net.atatat.s01_basictypes.ss02_fp_types;

public class Main {
    public static void main(String[] args) {

        // FP are architecture independent
        float f = 0.0f;
        double d = 0.0;
        double nan = Double.NaN;
        double inf = Double.POSITIVE_INFINITY;
        System.out.printf("Float: %f", f);
        System.out.printf("Double: %f", d);
        System.out.printf("NaN: %f", nan);
        System.out.printf("Inf: %f", inf);

        int meters_in_mile = 1609;
        double speed_km_h = 120.0;
        double speed_m_s = (speed_km_h * 1000) / 3600;
        double speed_ml_h = (120.0 * 1000) / 1609;
        System.out.println(
            "Speed %.1f km/h is %.1f m/s; and %.1f miles/h".formatted(speed_km_h, speed_m_s, speed_ml_h)
        );

        // Special form for hex floating point literals
        float fLiter1 = 0x1.0p-3f; // 0.125
        double fLiter2 = 0x1.0p-3F; // 0.125

        System.out.println("Java is strongly typed language");
        float minFloat = Float.MIN_VALUE;
        float maxFloat = Float.MAX_VALUE;
        double minDouble= Double.MIN_VALUE;
        double maxDouble= Double.MAX_VALUE;
        System.out.println(
            "Java represent float values from %f to %f and double from %f to %f".formatted(
                minFloat, maxFloat, minDouble, maxDouble
            )
        );

        float minNormal = Float.MIN_NORMAL;
        float positiveInf = Float.POSITIVE_INFINITY;
        float negativeInf = Float.NEGATIVE_INFINITY;
        System.out.println(
            "Java minimal normal float is %f, negative infinity is %f, positive is %f".formatted(
                minNormal, negativeInf, positiveInf
            )
        );
    }
}
