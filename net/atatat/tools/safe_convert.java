import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;

/**
 * Small internal utility for explicit, fail-fast, safe conversions.
 *
 * Design rules:
 * - Primitive narrowing must be exact or fail.
 * - Floating-point to integral conversion must be finite, in range, and mathematically integral.
 * - Runtime object conversion must be checked with Class#isInstance / Class#cast.
 * - Generic collections must validate every element because Java erases generic type arguments.
 * - Parsing helpers return Optional* variants for expected user/input failures.
 *
 * Recommended usage:
 * - Use this at boundaries: config, JSON-like maps, reflection, JDBC-ish dynamic values, legacy APIs.
 * - Inside domain code, prefer strong types, records, sealed interfaces, and typed APIs.
 */
public final class SafeConvert {
    private SafeConvert() {
        throw new AssertionError("No instances");
    }

    // ---------------------------------------------------------------------
    // Runtime object casts
    // ---------------------------------------------------------------------

    /**
     * Safely view value as the requested runtime type.
     *
     * Unlike {@code (T) value}, this does not throw on mismatch and does not
     * require an unchecked cast at the call site.
     */
    public static <T> Optional<T> as(Object value, Class<T> type) {
        Objects.requireNonNull(type, "type");
        return type.isInstance(value) ? Optional.of(type.cast(value)) : Optional.empty();
    }

    /**
     * Runtime-checked cast with a clearer error message than a scattered raw cast.
     */
    public static <T> T cast(Object value, Class<T> type) {
        Objects.requireNonNull(type, "type");
        if (!type.isInstance(value)) {
            throw new ClassCastException(
                    "Cannot cast " + describeType(value) + " to " + type.getName()
            );
        }
        return type.cast(value);
    }

    /**
     * Runtime-checked nullable cast. Null remains null.
     */
    public static <T> T castNullable(Object value, Class<T> type) {
        Objects.requireNonNull(type, "type");
        if (value == null) {
            return null;
        }
        return cast(value, type);
    }

    /**
     * Runtime-checked non-null cast. Null is rejected explicitly.
     */
    public static <T> T castNonNull(Object value, Class<T> type) {
        return cast(Objects.requireNonNull(value, "value"), type);
    }

    // ---------------------------------------------------------------------
    // Exact primitive narrowing
    // ---------------------------------------------------------------------

    public static int toIntExact(long value) {
        return Math.toIntExact(value);
    }

    public static short toShortExact(long value) {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
            throw new ArithmeticException("short overflow: " + value);
        }
        return (short) value;
    }

    public static byte toByteExact(long value) {
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
            throw new ArithmeticException("byte overflow: " + value);
        }
        return (byte) value;
    }

    /**
     * Converts an integer to a Java char code unit only if it fits into 0..65535.
     *
     * Note: this validates a UTF-16 code unit, not a full Unicode scalar value.
     * For Unicode code points, prefer {@link #codePointToStringExact(int)}.
     */
    public static char toCharCodeUnitExact(int value) {
        if (value < Character.MIN_VALUE || value > Character.MAX_VALUE) {
            throw new ArithmeticException("char code unit overflow: " + value);
        }
        return (char) value;
    }

    /**
     * Converts an integer to a non-surrogate BMP char.
     *
     * This rejects surrogate code units because a standalone surrogate is not a
     * valid Unicode scalar value.
     */
    public static char toBmpCharExact(int codePoint) {
        if (!Character.isValidCodePoint(codePoint)) {
            throw new IllegalArgumentException("Invalid Unicode code point: " + codePoint);
        }
        if (Character.isSupplementaryCodePoint(codePoint)) {
            throw new IllegalArgumentException("Code point requires a surrogate pair: " + codePoint);
        }
        char c = (char) codePoint;
        if (Character.isSurrogate(c)) {
            throw new IllegalArgumentException("Surrogate code point is not a standalone char: " + codePoint);
        }
        return c;
    }

    /**
     * Converts a Unicode code point to a String, supporting supplementary characters.
     */
    public static String codePointToStringExact(int codePoint) {
        if (!Character.isValidCodePoint(codePoint)) {
            throw new IllegalArgumentException("Invalid Unicode code point: " + codePoint);
        }
        return new String(Character.toChars(codePoint));
    }

    // ---------------------------------------------------------------------
    // Exact BigInteger / BigDecimal conversions
    // ---------------------------------------------------------------------

    public static int toIntExact(BigInteger value) {
        return Objects.requireNonNull(value, "value").intValueExact();
    }

    public static long toLongExact(BigInteger value) {
        return Objects.requireNonNull(value, "value").longValueExact();
    }

    public static short toShortExact(BigInteger value) {
        return Objects.requireNonNull(value, "value").shortValueExact();
    }

    public static byte toByteExact(BigInteger value) {
        return Objects.requireNonNull(value, "value").byteValueExact();
    }

    public static int toIntExact(BigDecimal value) {
        return Objects.requireNonNull(value, "value").intValueExact();
    }

    public static long toLongExact(BigDecimal value) {
        return Objects.requireNonNull(value, "value").longValueExact();
    }

    public static short toShortExact(BigDecimal value) {
        return Objects.requireNonNull(value, "value").shortValueExact();
    }

    public static byte toByteExact(BigDecimal value) {
        return Objects.requireNonNull(value, "value").byteValueExact();
    }

    // ---------------------------------------------------------------------
    // Floating-point to integral conversions
    // ---------------------------------------------------------------------

    public static int doubleToIntExact(double value) {
        requireFinite(value);
        requireIntegral(value);
        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
            throw new ArithmeticException("int overflow: " + value);
        }
        return (int) value;
    }

    public static long doubleToLongExact(double value) {
        requireFinite(value);
        requireIntegral(value);

        /*
         * double cannot represent every long exactly near Long.MIN/MAX_VALUE.
         * BigDecimal.valueOf(double) uses Double.toString(value), which preserves
         * the decimal value normally expected from the double.
         */
        return BigDecimal.valueOf(value).longValueExact();
    }

    public static short doubleToShortExact(double value) {
        int asInt = doubleToIntExact(value);
        return toShortExact(asInt);
    }

    public static byte doubleToByteExact(double value) {
        int asInt = doubleToIntExact(value);
        return toByteExact(asInt);
    }

    public static int floatToIntExact(float value) {
        return doubleToIntExact(value);
    }

    public static long floatToLongExact(float value) {
        return doubleToLongExact(value);
    }

    public static short floatToShortExact(float value) {
        return doubleToShortExact(value);
    }

    public static byte floatToByteExact(float value) {
        return doubleToByteExact(value);
    }

    // ---------------------------------------------------------------------
    // Generic Number conversions
    // ---------------------------------------------------------------------

    public static int numberToIntExact(Number value) {
        Objects.requireNonNull(value, "value");

        if (value instanceof Integer v) {
            return v;
        }
        if (value instanceof Long v) {
            return Math.toIntExact(v);
        }
        if (value instanceof Short v) {
            return v;
        }
        if (value instanceof Byte v) {
            return v;
        }
        if (value instanceof BigInteger v) {
            return v.intValueExact();
        }
        if (value instanceof BigDecimal v) {
            return v.intValueExact();
        }
        if (value instanceof Double v) {
            return doubleToIntExact(v);
        }
        if (value instanceof Float v) {
            return floatToIntExact(v);
        }

        throw unsupportedNumber(value, "int");
    }

    public static long numberToLongExact(Number value) {
        Objects.requireNonNull(value, "value");

        if (value instanceof Long v) {
            return v;
        }
        if (value instanceof Integer v) {
            return v.longValue();
        }
        if (value instanceof Short v) {
            return v.longValue();
        }
        if (value instanceof Byte v) {
            return v.longValue();
        }
        if (value instanceof BigInteger v) {
            return v.longValueExact();
        }
        if (value instanceof BigDecimal v) {
            return v.longValueExact();
        }
        if (value instanceof Double v) {
            return doubleToLongExact(v);
        }
        if (value instanceof Float v) {
            return floatToLongExact(v);
        }

        throw unsupportedNumber(value, "long");
    }

    public static short numberToShortExact(Number value) {
        return toShortExact(numberToLongExact(value));
    }

    public static byte numberToByteExact(Number value) {
        return toByteExact(numberToLongExact(value));
    }

    public static double numberToFiniteDouble(Number value) {
        Objects.requireNonNull(value, "value");

        double result = value.doubleValue();
        requireFinite(result);
        return result;
    }

    public static float numberToFiniteFloat(Number value) {
        double result = numberToFiniteDouble(value);
        if (result < -Float.MAX_VALUE || result > Float.MAX_VALUE) {
            throw new ArithmeticException("float overflow: " + result);
        }
        return (float) result;
    }

    // ---------------------------------------------------------------------
    // String parsing helpers
    // ---------------------------------------------------------------------

    public static OptionalInt parseInt(String value) {
        try {
            return OptionalInt.of(Integer.parseInt(requireTrimmedNonEmpty(value)));
        } catch (NumberFormatException | NullPointerException | IllegalArgumentException e) {
            return OptionalInt.empty();
        }
    }

    public static OptionalLong parseLong(String value) {
        try {
            return OptionalLong.of(Long.parseLong(requireTrimmedNonEmpty(value)));
        } catch (NumberFormatException | NullPointerException | IllegalArgumentException e) {
            return OptionalLong.empty();
        }
    }

    public static OptionalDouble parseDouble(String value) {
        try {
            double result = Double.parseDouble(requireTrimmedNonEmpty(value));
            return Double.isFinite(result) ? OptionalDouble.of(result) : OptionalDouble.empty();
        } catch (NumberFormatException | NullPointerException | IllegalArgumentException e) {
            return OptionalDouble.empty();
        }
    }

    public static Optional<BigInteger> parseBigInteger(String value) {
        try {
            return Optional.of(new BigInteger(requireTrimmedNonEmpty(value)));
        } catch (NumberFormatException | NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static Optional<BigDecimal> parseBigDecimal(String value) {
        try {
            return Optional.of(new BigDecimal(requireTrimmedNonEmpty(value)));
        } catch (NumberFormatException | NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    // ---------------------------------------------------------------------
    // Enum conversion
    // ---------------------------------------------------------------------

    public static <E extends Enum<E>> Optional<E> enumValueOf(Class<E> enumType, String name) {
        Objects.requireNonNull(enumType, "enumType");
        if (name == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Enum.valueOf(enumType, name.trim()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static <E extends Enum<E>> Optional<E> enumValueOfIgnoreCase(Class<E> enumType, String name) {
        Objects.requireNonNull(enumType, "enumType");
        if (name == null) {
            return Optional.empty();
        }

        String normalized = name.trim();
        if (normalized.isEmpty()) {
            return Optional.empty();
        }

        for (E constant : enumType.getEnumConstants()) {
            if (constant.name().equalsIgnoreCase(normalized)) {
                return Optional.of(constant);
            }
        }
        return Optional.empty();
    }

    // ---------------------------------------------------------------------
    // Generic collection validation
    // ---------------------------------------------------------------------

    /**
     * Validates that an object is a List and that every element has the requested type.
     *
     * This is the safe replacement for unchecked casts like:
     * {@code (List<String>) value}
     */
    public static <T> List<T> toListOf(Object value, Class<T> elementType) {
        Objects.requireNonNull(elementType, "elementType");
        if (!(value instanceof List<?> list)) {
            throw new ClassCastException("Cannot cast " + describeType(value) + " to List<" + elementType.getName() + ">");
        }
        return validateCollectionElements(list, elementType, ArrayList::new);
    }

    /**
     * Optional-returning version of {@link #toListOf(Object, Class)}.
     */
    public static <T> Optional<List<T>> asListOf(Object value, Class<T> elementType) {
        try {
            return Optional.of(toListOf(value, elementType));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    /**
     * Validates a collection's elements and returns a defensive copy as a List.
     */
    public static <T> List<T> collectionToListOf(Collection<?> collection, Class<T> elementType) {
        Objects.requireNonNull(collection, "collection");
        Objects.requireNonNull(elementType, "elementType");
        return validateCollectionElements(collection, elementType, ArrayList::new);
    }

    // ---------------------------------------------------------------------
    // Boolean parsing
    // ---------------------------------------------------------------------

    /**
     * Strict boolean parser.
     *
     * Unlike Boolean.parseBoolean, this rejects invalid values instead of silently
     * returning false.
     */
    public static Optional<Boolean> parseBooleanStrict(String value) {
        if (value == null) {
            return Optional.empty();
        }

        String normalized = value.trim();
        if (normalized.equalsIgnoreCase("true")) {
            return Optional.of(Boolean.TRUE);
        }
        if (normalized.equalsIgnoreCase("false")) {
            return Optional.of(Boolean.FALSE);
        }
        return Optional.empty();
    }

    // ---------------------------------------------------------------------
    // Private helpers
    // ---------------------------------------------------------------------

    private static void requireFinite(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Not finite: " + value);
        }
    }

    private static void requireIntegral(double value) {
        if (value % 1.0d != 0.0d) {
            throw new IllegalArgumentException("Not an integer: " + value);
        }
    }

    private static String requireTrimmedNonEmpty(String value) {
        String result = Objects.requireNonNull(value, "value").trim();
        if (result.isEmpty()) {
            throw new IllegalArgumentException("value is blank");
        }
        return result;
    }

    private static ArithmeticException unsupportedNumber(Number value, String targetType) {
        return new ArithmeticException(
                "Unsupported Number implementation for exact " + targetType + " conversion: "
                        + value.getClass().getName()
        );
    }

    private static String describeType(Object value) {
        return value == null ? "null" : value.getClass().getName();
    }

    private static <T, C extends Collection<T>> C validateCollectionElements(
            Collection<?> source,
            Class<T> elementType,
            Function<Integer, C> collectionFactory
    ) {
        C result = collectionFactory.apply(source.size());

        int index = 0;
        for (Object item : source) {
            if (!elementType.isInstance(item)) {
                throw new ClassCastException(
                        "Element at index " + index + " is " + describeType(item)
                                + ", expected " + elementType.getName()
                );
            }
            result.add(elementType.cast(item));
            index++;
        }

        return result;
    }
}
