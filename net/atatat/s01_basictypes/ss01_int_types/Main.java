package net.atatat.s01_basictypes.ss01_int_types;

public class Main {    
    public static void main(String[] args) {

        // Integrals are architecture independent
        // All literals have type int by default
        // You can use suffixes to specify the type (L, F, D)
        // and prefixes to specify the base (0b, 0x)
        byte b = 0;
        short s = 0;
        int i = 0;
        long l = 0L;
        System.out.printf("Byte: %d", b);
        System.out.printf("Short: %d", s);
        System.out.printf("Int: %d", i);
        System.out.printf("Long: %d", l);

        // Literals have no identity
        int a1 = 10; // are the same
        int b1 = 10; // value

        System.out.println("Java is strongly typed language");
        int minInt = Integer.MIN_VALUE;
        int maxInt = Integer.MAX_VALUE;
        System.out.println(
            "Java represent integer values from %d to %d".formatted(minInt, maxInt)
        );

        // What else Integer.* has to offer
        int integerSize = Integer.SIZE;
        int integerBytes = Integer.BYTES;
        var integerType = Integer.TYPE;
        System.out.println(
            "Integer type is %s, has %d bytes and %d bits".formatted(integerType, integerBytes, integerSize)
        );

        int celsiusTemp = 35;
        // Implicit cast celsiusTemp to double
        double fahrenheitTemp = (celsiusTemp * 1.8) + 32.0;
        System.out.println(
            "%d degrees Celsius is %.0f degrees Fahrenheit".formatted(celsiusTemp, fahrenheitTemp)
        );

        // Separating HEX and BIN octets with underscores
        int hex1 = 0xABCD_1234; // hexadecimal
        int bin2 = 0b1010_1010; // binary

        // TODO: null is a special literal
        // TODO: Void is a reference type, mainly useful in generics and reflection

    }
}
