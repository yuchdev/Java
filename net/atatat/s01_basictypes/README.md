# Basic Java Types

This chapter is written as an interview-oriented deep dive into Java's basic types: primitive types, wrappers, `String`, arrays, literals, conversion rules, equality, overload resolution, and the small specification details that often decide whether code compiles, what it prints, or why it fails at runtime.

The goal is not to memorize puzzles. The goal is to learn how Java actually reasons about values, variables, literals, expressions, conversions, and references.

Most examples are intentionally small. They are designed to be pasted into a `main` method or a JShell session.

```java
public class Demo {
    public static void main(String[] args) {
        // paste examples here
    }
}
```

---

## 1. What "Basic Types" Actually Mean in Java

Java does not have one unified category called "basic types" in the formal sense. In interviews, people usually mean a practical cluster of language elements that every Java developer is expected to understand:

- primitive types: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`;
- wrapper classes: `Byte`, `Short`, `Integer`, `Long`, `Float`, `Double`, `Character`, `Boolean`;
- `String`, because it behaves specially despite being a normal reference type;
- arrays, especially primitive arrays;
- `null`, `void`, `Void`, and class literals such as `int.class`.

### 1.1 Primitive types vs. reference types

Primitive variables store primitive values. Reference variables store references to objects, or `null`.

```java
int x = 10;              // primitive value
Integer y = 10;          // reference to an Integer object, usually boxed via Integer.valueOf(10)
String s = "hello";      // reference to a String object
int[] a = {1, 2, 3};     // reference to an array object
```

A primitive value has no identity. Two `int` values with value `10` are simply the same value. Objects, however, have identity: two different objects can be equal by value but not identical by reference.

```java
Integer a = new Integer(10); // deprecated constructor, shown for identity demonstration
Integer b = new Integer(10);

System.out.println(a == b);      // false: different objects
System.out.println(a.equals(b)); // true: same numeric value
```

> **Comment:** In modern code, do not use wrapper constructors such as `new Integer(10)`. Prefer `Integer.valueOf(10)` or autoboxing. The constructor example is useful only to demonstrate object identity.

### 1.2 The eight primitive types

| Category               | Types                                  |
|------------------------|----------------------------------------|
| Integral numeric       | `byte`, `short`, `int`, `long`, `char` |
| Floating-point numeric | `float`, `double`                      |
| Logical                | `boolean`                              |

`char` is in the numeric family for conversion and promotion purposes, even though it is usually thought of as a text-related type.

```java
char c = 'A';
System.out.println(c + 1); // 66, because char participates in numeric promotion
```

### 1.3 Wrapper classes

Each primitive type has a corresponding wrapper class.

| Primitive | Wrapper     |
|-----------|-------------|
| `byte`    | `Byte`      |
| `short`   | `Short`     |
| `int`     | `Integer`   |
| `long`    | `Long`      |
| `float`   | `Float`     |
| `double`  | `Double`    |
| `char`    | `Character` |
| `boolean` | `Boolean`   |

Wrappers are reference types. They can be `null`, can be stored in generic collections, and can participate in method overload resolution in ways primitives cannot.

```java
Integer n = null;
// int x = n; // throws NullPointerException due to unboxing
```

### 1.4 `String` is not primitive, but it is special

`String` is a final reference type. It is special because Java has string literals, compile-time string concatenation, string interning, and dedicated runtime support for concatenation.

```java
String a = "java";
String b = "ja" + "va";
System.out.println(a == b); // true: compile-time constant concatenation, interned
```

But `String` is still an object:

```java
String x = new String("java");
System.out.println(x == "java");      // false
System.out.println(x.equals("java")); // true
```

### 1.5 Arrays are objects

All arrays are reference types, including arrays of primitives.

```java
int[] numbers = {1, 2, 3};
System.out.println(numbers instanceof Object); // true
System.out.println(numbers.getClass());        // class [I
```

Primitive arrays contain primitive elements directly. Wrapper arrays contain references.

```java
int[] primitiveArray = new int[3];       // stores int values
Integer[] wrapperArray = new Integer[3]; // stores references, initially null

System.out.println(primitiveArray[0]); // 0
System.out.println(wrapperArray[0]);   // null
```

### 1.6 `null`, `void`, and `Void`

`null` is not a primitive value. It is a special literal assignable to reference variables.

```java
String s = null;
Object o = null;
// int x = null; // does not compile
```

`void` is a keyword used for methods that return no value. `Void` is a reference type, mainly useful in generics and reflection.

```java
Class<Void> c1 = void.class;
Class<Void> c2 = Void.TYPE;
System.out.println(c1 == c2); // true
```

### 1.7 Interview summary

When an interview question says "basic types," first identify which category is involved:

1. primitive value;
2. wrapper object;
3. reference type such as `String` or array;
4. special literal or pseudo-type such as `null`, `void`, or `Void`.

Many traps are simply category mistakes.

---

## 2. Primitive Types: Small, Simple, and Full of Traps

Primitive types look simple, but Java attaches many precise rules to them: default initialization, arithmetic promotion, compile-time constants, overflow, and conversions.

### 2.1 Default values

Fields and array elements receive default values. Local variables do not.

```java
class Defaults {
    int i;          // 0
    long l;         // 0L
    float f;        // 0.0f
    double d;       // 0.0d
    char c;         // '\u0000'
    boolean b;      // false
    String s;       // null
}
```

```java
public static void main(String[] args) {
    int x;
    // System.out.println(x); // does not compile: local variable x might not have been initialized
}
```

> **Comment:** A common beginner answer is "Java initializes variables to zero." That is only true for fields and array elements, not for local variables.

### 2.2 Primitive values are not objects

You cannot call methods directly on primitive values.

```java
int x = 42;
// x.toString(); // does not compile
System.out.println(Integer.toString(x));
```

However, in some contexts autoboxing makes it look as if primitives are objects.

```java
int x = 42;
Integer boxed = x;              // boxing
System.out.println(boxed.toString());
```

### 2.3 Primitive variable vs. primitive value

A variable is a storage location. A value is what is stored in it.

```java
int a = 10;
int b = a;
b = 20;
System.out.println(a); // 10
```

Primitive assignment copies the value. It does not create aliases.

### 2.4 Ranges and overflow

Java integer types have fixed ranges. Overflow is defined by the language; it wraps around using two's-complement arithmetic semantics.

```java
int max = Integer.MAX_VALUE;
System.out.println(max);       // 2147483647
System.out.println(max + 1);   // -2147483648
```

This is different from C and C++, where signed integer overflow is an undefined behavior. In Java, it is not undefined; it has a defined result.

### 2.5 Floating-point overflow and underflow

Floating-point overflow produces infinity. Underflow may produce very small subnormal values or zero.

```java
double huge = Double.MAX_VALUE;
System.out.println(huge * 2); // Infinity

double tiny = Double.MIN_VALUE;
System.out.println(tiny / 2); // 0.0
```

### 2.6 `boolean` size is not specified

Java specifies the values of `boolean`: `true` and `false`. It does not promise a particular memory size such as one byte.

```java
boolean flag = true;
// You cannot portably ask: "how many bytes does this boolean use?"
```

JVM implementations may store booleans differently in fields, arrays, or optimized runtime structures.

### 2.7 Primitive layout is mostly not specified

The Java language specifies behavior, not exact object layout. For example, this class has an `int`, a `byte`, and a `boolean`, but the language does not say exactly how many bytes an object occupies in memory.

```java
class Example {
    int a;
    byte b;
    boolean c;
}
```

Object headers, alignment, field layout, compressed references, and JIT optimizations are JVM implementation details.

### 2.8 Interview summary

When reasoning about primitive types, ask:

- Is the variable a field, array element, or local variable?
- Is arithmetic being promoted to `int`, `long`, `float`, or `double`?
- Is overflow possible?
- Is this language-specified behavior or JVM implementation detail?

---

## 3. Integer Types: `byte`, `short`, `int`, `long`

Java's integer types are fixed-width signed types, except `char`, which is unsigned 16-bit. The most important interview fact is that integer arithmetic usually happens as `int`, not as `byte` or `short`.

### 3.1 Ranges

| Type    | Bits |              Minimum |             Maximum |
|---------|-----:|---------------------:|--------------------:|
| `byte`  |    8 |                 -128 |                 127 |
| `short` |   16 |               -32768 |               32767 |
| `int`   |   32 |          -2147483648 |          2147483647 |
| `long`  |   64 | -9223372036854775808 | 9223372036854775807 |

```java
System.out.println(Byte.MIN_VALUE);    // -128
System.out.println(Byte.MAX_VALUE);    // 127
System.out.println(Integer.MAX_VALUE); // 2147483647
```

### 3.2 `byte + byte` returns `int`

```java
byte a = 10;
byte b = 20;
// byte c = a + b; // does not compile
int c = a + b;
```

`byte`, `short`, and `char` are promoted to `int` for most arithmetic operations.

> **Interview trap:** "Both operands are byte, so result is byte" is wrong. Java does not perform arithmetic in `byte`.

### 3.3 Compound assignment performs an implicit cast

```java
byte b = 1;
// b = b + 1; // does not compile: b + 1 is int
b += 1;       // compiles: equivalent to b = (byte)(b + 1)
System.out.println(b); // 2
```

The hidden cast can lose information.

```java
byte x = 127;
x += 1;
System.out.println(x); // -128
```

### 3.4 Increment and decrement also work on small types

```java
byte b = 127;
b++;
System.out.println(b); // -128
```

`b++` is not exactly the same as `b = b + 1`; it includes an implicit cast back to the original type, much like `b += 1`.

If you write `b = b + 1` with a `byte b`, it won't compile because `b + 1` is promoted to `int`.

Step-by-step for `b++`:
1. The value of `b` is fetched and promoted to `int`.
2. The value `1` is added to that `int`.
3. The resulting `int` is explicitly cast (narrowed) back to `byte`.
4. The narrowed value is stored back into `b`.

Essentially, `b++` is equivalent to `b = (byte)(b + 1)`.

### 3.5 Integer literals

```java
int decimal = 42;
int hex = 0x2A;
int binary = 0b101010;
int octal = 052;

System.out.println(decimal); // 42
System.out.println(hex);     // 42
System.out.println(binary);  // 42
System.out.println(octal);   // 42
```

The octal syntax is a classic trap:

```java
System.out.println(010); // 8, not 10
```

#### 3.5.1 Hex literals and sign bits

Hex literals represent bit patterns. For a 32-bit `int`, `0xFFFFFFFF` means all bits are 1. In two's complement, this is `-1`.

```java
int a = 0xFFFFFFFF;
System.out.println(a); // -1

long b = 0xFFFFFFFF;   // Trap: literal is int -1, then widened to long
System.out.println(b); // -1L

long c = 0xFFFFFFFFL;  // Literal is long 4294967295
System.out.println(c); // 4294967295L
```

### 3.6 Underscores in literals

```java
int million = 1_000_000;
long card = 1234_5678_9012_3456L;
```

Invalid placements:

```java
// int a = _100;     // invalid
// int b = 100_;     // invalid
// int c = 0_xFF;    // invalid
// int d = 0x_FF;    // invalid
```

### 3.7 `long` suffix

Use uppercase `L`, not lowercase `l`, because lowercase `l` looks like `1`.

```java
long ok = 10L;
long confusing = 10l; // legal but poor style
```

#### 3.7.1 Literal type vs Variable type

A common mistake is forgetting that literals have their own type independent of the variable they are assigned to.

```java
// long a = 2147483648;   // does not compile: literal 2147483648 is out of int range
long b = 2147483648L;     // compiles

long x = 1000 * 1000 * 1000 * 3; 
System.out.println(x); // -1294967296 (int overflow before widening)
```

### 3.8 Constant expressions and implicit narrowing

```java
byte b1 = 127;      // compiles: constant int value fits into byte
// byte b2 = 128;   // does not compile

final int x = 10;
byte b3 = x;        // compiles: x is a constant variable

int y = 10;
// byte b4 = y;     // does not compile: y is not a compile-time constant
```

A `final` variable initialized with a compile-time constant may itself be a constant variable.

### 3.9 Overflow at compile time and runtime

```java
int a = 2_000_000_000;
int b = 2_000_000_000;
System.out.println(a + b); // -294967296
```

Compile-time constant expressions can also overflow if the expression type is `int`.

```java
int x = 2147483647 + 1;
System.out.println(x); // -2147483648
```

But an out-of-range literal is different:

```java
// int impossible = 2147483648; // does not compile: literal too large for int
int min = -2147483648;          // special case: parsed as unary minus applied to int literal 2147483648
```

The minimum value case is special because the positive literal is permitted only as the operand of unary `-`.

### 3.10 `Math.abs(Integer.MIN_VALUE)` trap

```java
int x = Integer.MIN_VALUE;
System.out.println(x);          // -2147483648
System.out.println(Math.abs(x)); // -2147483648
System.out.println(-x);          // -2147483648
```

Both `Math.abs(Integer.MIN_VALUE)` and `-Integer.MIN_VALUE` result in `Integer.MIN_VALUE`.

There is no positive `int` value corresponding to `-2147483648`, so the result overflows and remains negative. In two's complement arithmetic, the negation of a number is found by flipping all bits and adding 1.

For `Integer.MIN_VALUE` (`0x80000000`):
1.  Binary representation: `10000000 00000000 00000000 00000000`
2.  Flip bits: `01111111 11111111 11111111 11111111`
3.  Add 1: `10000000 00000000 00000000 00000000`

The result is exactly the same bit pattern we started with, which is `-2147483648`.

### 3.11 Division and remainder with negative numbers

Integer division truncates toward zero.

```java
System.out.println( 7 / 3); // 2
System.out.println(-7 / 3); // -2
System.out.println( 7 % 3); // 1
System.out.println(-7 % 3); // -1
```

The identity holds:

```java
(a / b) * b + (a % b) == a
```

#### 3.11.1 The `Integer.MIN_VALUE / -1` trap

Just like `Math.abs(Integer.MIN_VALUE)`, division can also overflow.

```java
int min = Integer.MIN_VALUE;
System.out.println(min / -1); // -2147483648
```

Since the positive version of `MIN_VALUE` (2147483648) does not fit in a 32-bit signed `int`, the result overflows back to `MIN_VALUE`.

### 3.12 Integer division by zero

```java
// System.out.println(1 / 0); // ArithmeticException
```

For integral types, division by zero throws `ArithmeticException`. Floating-point division by zero is different, as discussed later.

### 3.13 Interview summary

The most important integer rules:

- `byte`, `short`, and `char` arithmetic promotes to `int`.
- Compound assignment includes a hidden narrowing cast.
- Integer overflow is defined and wraps around.
- `010` is octal.
- `Math.abs(Integer.MIN_VALUE)` and `Integer.MIN_VALUE / -1` are still negative.
- Literals without `L` suffix are `int`, which can cause overflow before assignment to `long`.
- `byte` to `char` conversion is a "widening and narrowing" conversion (sign-extension happens).

---

## 4. `char`: The Most Misunderstood Primitive

`char` is often described as "a character," but that description is dangerously incomplete. In Java, `char` is a 16-bit unsigned integer representing one UTF-16 code unit.

### 4.1 `char` is unsigned 16-bit

```java
char min = '\u0000';
char max = '\uffff';

System.out.println((int) min); // 0
System.out.println((int) max); // 65535
```

#### 4.1.1 The `byte` to `char` trap

Converting a `byte` to `char` is a "widening and narrowing" conversion. The `byte` is first sign-extended to `int`, and then the `int` is narrowed to `char`.

```java
byte b = -1;
char c = (char) b;
System.out.println((int) c); // 65535, not what you might expect!
```

1.  `byte` `-1` (`11111111`) is widened to `int` `-1` (`11111111 11111111 11111111 11111111`).
2.  `int` `-1` is narrowed to `char` (`11111111 11111111`), which is `65535`.

Unlike `byte`, `short`, `int`, and `long`, `char` has no negative values.

### 4.2 `char` is not a Unicode character

Unicode has code points. UTF-16 represents code points as one or two 16-bit code units. Java `char` stores one UTF-16 code unit, not necessarily one full Unicode code point.

```java
String s = "A";
System.out.println(s.length());      // 1
System.out.println(s.codePointCount(0, s.length())); // 1
```

For many common characters, one `char` is enough. But not for all.

### 4.3 Surrogate pairs

Emoji and many historical scripts are outside the Basic Multilingual Plane and need two UTF-16 code units.

```java
String emoji = "😀";

System.out.println(emoji.length());                      // 2
System.out.println(emoji.codePointCount(0, emoji.length())); // 1
System.out.println(Integer.toHexString(emoji.charAt(0))); // d83d
System.out.println(Integer.toHexString(emoji.charAt(1))); // de00
```

The two `char` values are a high surrogate and a low surrogate.

```java
char high = emoji.charAt(0);
char low = emoji.charAt(1);

System.out.println(Character.isHighSurrogate(high)); // true
System.out.println(Character.isLowSurrogate(low));   // true
```

### 4.4 Proper Unicode iteration

Incorrect approach:

```java
String text = "A😀B";
for (int i = 0; i < text.length(); i++) {
    char ch = text.charAt(i);
    System.out.println(ch); // prints surrogate halves separately for emoji
}
```

Better approach:

```java
String text = "A😀B";
text.codePoints().forEach(cp -> {
    System.out.println(Integer.toHexString(cp));
});
```

Or:

```java
for (int i = 0; i < text.length(); ) {
    int cp = text.codePointAt(i);
    System.out.println(Character.toChars(cp));
    i += Character.charCount(cp);
}
```

### 4.5 `char` arithmetic

```java
char c = 'A';
System.out.println(c + 1);        // 66
System.out.println((char)(c + 1)); // B
```

`char + int` produces `int`. To store the result in `char`, cast explicitly.

```java
char c = 'A';
// c = c + 1; // does not compile
c += 1;       // compiles due to compound assignment
System.out.println(c); // B
```

### 4.6 Unicode escapes are processed early

Unicode escapes are processed before lexical analysis. This can produce surprising effects.

```java
// The following is dangerous if uncommented:
// String s = "hello \u000A world";
```

`\u000A` is a line feed. It is processed before the compiler sees the string literal, so it can break the source line.

Another strange example:

```java
// \u000d System.out.println("This can become active code in odd contexts");
```

> **Comment:** Do not use Unicode escapes for ordinary text unless there is a strong reason. Prefer normal UTF-8 source files and clear string literals.

### 4.7 `Character` vs `char`

```java
Character boxed = 'A';
char primitive = boxed; // unboxing
```

`Character` can be `null`; `char` cannot.

```java
Character ch = null;
// char c = ch; // NullPointerException at runtime
```

### 4.8 Interview summary

Essential `char` facts:

- `char` is unsigned 16-bit.
- `char` is one UTF-16 code unit.
- One visible character may require multiple `char` values.
- `String.length()` counts UTF-16 code units, not user-perceived characters.
- Use `String.codePointCount()` to count code points (user-perceived characters).
- Unicode escapes are processed before tokenization.

---

## 5. Floating-Point Types: `float` and `double`

Java's floating-point types follow IEEE-style binary floating-point arithmetic. They are powerful, fast, and often misunderstood.

### 5.1 `double` is the default floating-point type

```java
double d = 1.5;
// float f = 1.5;  // does not compile: 1.5 is double
float f = 1.5f;
```

Use `f` or `F` for `float` literals.

```java
float a = 1.0F;
double b = 1.0D; // D is optional
```

### 5.2 Decimal fractions are not usually exact

```java
System.out.println(0.1 + 0.2);        // 0.30000000000000004
System.out.println(0.1 + 0.2 == 0.3); // false
```

Binary floating-point cannot exactly represent most decimal fractions.

### 5.3 Positive zero and negative zero

```java
double pz = 0.0;
double nz = -0.0;

System.out.println(pz == nz);                 // true
System.out.println(1.0 / pz);                 // Infinity
System.out.println(1.0 / nz);                 // -Infinity
System.out.println(Double.compare(pz, nz));   // 1
```

Primitive equality treats `0.0` and `-0.0` as equal, but some library methods distinguish them.

### 5.4 `NaN` is not equal to itself

```java
double nan = Double.NaN;
System.out.println(nan == nan);       // false
System.out.println(nan != nan);       // true
System.out.println(Double.isNaN(nan)); // true
```

This is a standard NaN rule: use `Double.isNaN()` or `Float.isNaN()`.

### 5.5 `Double.NaN == Double.NaN` vs `Double.equals()`

```java
Double a = Double.NaN;
Double b = Double.NaN;

System.out.println(a == b);      // usually false or true? Depends on references if boxed; do not rely on it
System.out.println(a.equals(b)); // true
```

`Double.equals()` treats NaN values as equal for object equality purposes, even though primitive `==` does not.

Better demonstration:

```java
double x = Double.NaN;
double y = Double.NaN;
System.out.println(x == y); // false

Double dx = x;
Double dy = y;
System.out.println(dx.equals(dy)); // true
```

### 5.6 Floating-point division by zero

```java
System.out.println(1.0 / 0.0);   // Infinity
System.out.println(-1.0 / 0.0);  // -Infinity
System.out.println(0.0 / 0.0);   // NaN
```

Unlike integer division by zero, floating-point division by zero does not throw `ArithmeticException`.

### 5.7 Overflow and underflow

```java
System.out.println(Double.MAX_VALUE * 2); // Infinity
System.out.println(Double.MIN_VALUE / 2); // 0.0
```

`Double.MIN_VALUE` is the smallest positive nonzero `double`, not the most negative value.

```java
System.out.println(Double.MIN_VALUE);  // 4.9E-324
System.out.println(-Double.MAX_VALUE); // most negative finite double
```

### 5.8 Floating-point comparison

Never compare computed floating-point values for exact equality unless exact binary behavior is required.

```java
double expected = 0.3;
double actual = 0.1 + 0.2;

double eps = 1e-12;
System.out.println(Math.abs(expected - actual) < eps); // true
```

### 5.9 `BigDecimal` for decimal arithmetic

Bad:

```java
System.out.println(new java.math.BigDecimal(0.1));
```

This captures the binary floating-point approximation of `0.1`.

Better:

```java
System.out.println(java.math.BigDecimal.valueOf(0.1));
System.out.println(new java.math.BigDecimal("0.1"));
```

For money, use `BigDecimal` with explicit scale and rounding rules, or use integer minor units such as cents when suitable.

### 5.10 `strictfp`

Historically, `strictfp` was used to force strict floating-point semantics across platforms. Modern Java has much more predictable floating-point behavior than early Java versions, but interviewers may still ask what `strictfp` means.

```java
strictfp class StrictExample {
    double calc(double a, double b) {
        return a / b;
    }
}
```

> **Comment:** In modern Java, `strictfp` is much less practically important than it once was, but it remains part of the language history.

### 5.11 Hardware Performance: `float` vs `double`

On most modern 64-bit hardware (x86-64, ARM64):
- **Scalar Speed**: Basic operations (addition, multiplication) for `float` and `double` typically have the same latency and throughput. There is no significant "speed" difference for single operations.
- **Memory & Cache**: `float` is 32 bits (4 bytes) and `double` is 64 bits (8 bytes). Using `float` consumes half the memory and can significantly improve performance by reducing cache misses and memory bandwidth usage when processing large arrays.
- **Vectorization (SIMD)**: Modern CPUs can process multiple numbers at once. Since `float` is half the size, a single SIMD instruction can process twice as many `float` values as `double` values (e.g., 8 floats vs 4 doubles in a 256-bit AVX register).
- **Complex Operations**: Division and square root (`Math.sqrt`) are often faster for `float` than for `double` on some architectures.

**Rule of thumb**: Use `double` by default for better precision. Use `float` if you have massive arrays and need to save memory or if you are doing heavy SIMD-optimized calculations (e.g., in graphics or machine learning).

### 5.12 Interview summary

Floating-point traps:

- `0.1 + 0.2 != 0.3`.
- `NaN != NaN` for primitive equality.
- `0.0 == -0.0`, but they can behave differently in division and comparison helpers.
- `Double.MIN_VALUE` is positive, not negative.
- Floating-point division by zero does not throw.

---

## 6. `boolean`: Simple Type, Subtle Semantics

`boolean` has only two values, `true` and `false`, but it has several important differences from C-style languages.

### 6.1 `boolean` is not numeric

```java
boolean b = true;
// int x = b;       // does not compile
// boolean c = 1;   // does not compile
```

There is no implicit or explicit conversion between `boolean` and numeric primitive types.

```java
// boolean b = (boolean) 1; // does not compile
```

### 6.2 `if (x = true)` can compile

```java
boolean x = false;
if (x = true) {
    System.out.println("runs");
}
```

This compiles because assignment to a `boolean` variable produces a `boolean` value. It is usually a bug.

But this does not compile:

```java
int n = 0;
// if (n = 1) { } // assignment result is int, not boolean
```

### 6.3 Short-circuit operators

```java
int x = 0;
if (x != 0 && 10 / x > 1) {
    System.out.println("safe");
}
```

`&&` and `||` short-circuit: the right side may not be evaluated.

```java
System.out.println(false && expensive()); // expensive() not called
System.out.println(true || expensive());  // expensive() not called

static boolean expensive() {
    System.out.println("called");
    return true;
}
```

### 6.4 Non-short-circuit boolean operators

`&` and `|` can be used with booleans too, but both sides are evaluated.

**Comparison with short-circuit operators:**
- **Functionally similar:** Both `&&` and `&` perform a logical AND operation—they return `true` only if both operands are `true`.
- **Evaluation difference:** `&&` is "lazy" (short-circuits); if the left side is `false`, the right side is never touched. `&` is "eager"; it always evaluates both sides.
- **Operand types:** `&&` only works with `boolean`. `&` is overloaded: it works with `boolean` (logical AND) and integral types (bitwise AND).

```java
int x = 0;
// if (x != 0 & 10 / x > 1) { } // ArithmeticException because right side is evaluated
```

`^` is boolean XOR:

```java
System.out.println(true ^ false); // true
System.out.println(true ^ true);  // false
```

### 6.5 `Boolean` vs `boolean`

```java
Boolean boxed = Boolean.TRUE;
boolean primitive = boxed; // unboxing
```

`Boolean.TRUE` is a predefined constant reference to the canonical `true` object.
`Boolean.valueOf(true)` is a factory method call that returns that same canonical object.

So for `true`, these are effectively equivalent in result:

```java
Boolean a = Boolean.TRUE;
Boolean b = Boolean.valueOf(true);
System.out.println(a == b); // true
```

Practical difference is mostly intent:

- Use `Boolean.TRUE` when you want the explicit constant.
- Use `Boolean.valueOf(x)` when converting a `boolean` expression or parsing result into a `Boolean`.

Also avoid `new Boolean(...)` (deprecated), because it creates unnecessary distinct objects.

`Boolean` can be `null`, effectively providing a three-state type.

```java
Boolean flag = null;
// if (flag) { } // NullPointerException due to unboxing
```

Safer pattern:

```java
Boolean flag = null;
if (Boolean.TRUE.equals(flag)) {
    System.out.println("true only");
}
```

### 6.6 Boolean object identity

```java
Boolean a = Boolean.valueOf(true);
Boolean b = Boolean.valueOf(true);
System.out.println(a == b); // true: Boolean uses two canonical instances
```

But this is still bad style for logic:

```java
System.out.println(Boolean.TRUE.equals(a)); // value-oriented and null-safe
```

### 6.7 Interview summary

Important `boolean` facts:

- No numeric conversion.
- `if (x = true)` compiles if `x` is boolean.
- `&&` and `||` short-circuit.
- `&` and `|` evaluate both operands.
- Unboxing `Boolean null` throws `NullPointerException`.

---

## 7. Type Conversion Rules

Java conversions are exact, rule-driven, and central to overload resolution. Many interview puzzles are just conversion puzzles.

### 7.1 Widening primitive conversion

A widening primitive conversion goes from a smaller or less expressive numeric type to a larger or more expressive one.

```java
int i = 10;
long l = i;
float f = l;
double d = f;
```

Common widening paths:

```text
byte -> short -> int -> long -> float -> double
char -> int -> long -> float -> double
```

There is no `char -> short` widening conversion because `char` has values up to 65,535, while `short` only goes up to 32767.

```java
char c = 65000;
// short s = c; // does not compile
int i = c;      // ok
```

### 7.2 Widening may lose precision

```java
long big = 9_007_199_254_740_993L; // 2^53 + 1
double d = big;
System.out.println((long) d); // 9007199254740992, precision lost
```

Widening from `long` to `double` is allowed even though precision may be lost.

### 7.3 Narrowing primitive conversion

Narrowing requires an explicit cast unless the source is a compile-time constant that fits.

```java
int i = 130;
byte b = (byte) i;
System.out.println(b); // -126
```

Narrowing discards high-order bits for integer conversions.

### 7.4 Floating-point to integer conversion

```java
System.out.println((int) 1.9);    // 1
System.out.println((int) -1.9);   // -1
System.out.println((int) Double.NaN); // 0
System.out.println((int) Double.POSITIVE_INFINITY); // 2147483647
System.out.println((int) Double.NEGATIVE_INFINITY); // -2147483648
```

Floating-point to integer conversion truncates toward zero, with special handling for NaN and infinities.

### 7.5 Boxing conversion

```java
int x = 10;
Integer boxed = x;
```

This is equivalent to something like:

```java
Integer boxed = Integer.valueOf(x);
```

### 7.6 Unboxing conversion

```java
Integer boxed = 10;
int x = boxed;
```

If the reference is `null`, unboxing throws `NullPointerException`.

```java
Integer n = null;
// int x = n; // NullPointerException
```

### 7.7 Widening followed by boxing is not allowed

```java
int x = 10;
// Long y = x; // does not compile
```

Java does not do `int -> long -> Long` automatically in assignment.

Allowed:

```java
long l = x;      // widening primitive
Long y = 10L;    // boxing long literal
```

### 7.8 Boxing followed by widening reference is allowed

```java
int x = 10;
Object o = x; // int -> Integer -> Object
```

The primitive `int` is boxed to `Integer`, then widened as a reference to `Object`.

### 7.9 Overload conversion priority

```java
static void f(long x)    { System.out.println("long"); }
static void f(Integer x) { System.out.println("Integer"); }

public static void main(String[] args) {
    f(10); // long: widening primitive beats boxing
}
```

Another example:

```java
static void g(Integer x) { System.out.println("Integer"); }
static void g(int... x)  { System.out.println("varargs"); }

g(10); // Integer: boxing beats varargs
```

### 7.10 Interview summary

Remember these conversion priorities:

1. exact match;
2. widening primitive;
3. boxing/unboxing where applicable;
4. widening reference;
5. varargs last.

And remember the famous non-rule:

```java
// Long x = 10; // no int -> long -> Long chain
```

---

## 8. Numeric Promotion and Expressions

Numeric promotion explains why many seemingly reasonable assignments fail.

### 8.1 Unary numeric promotion

Unary numeric promotion applies to operators such as unary `+`, unary `-`, and bitwise complement `~`.

```java
byte b = 1;
int x = +b;   // b promoted to int
int y = -b;   // result is int
int z = ~b;   // result is int
```

### 8.2 Binary numeric promotion

For arithmetic operators such as `+`, `-`, `*`, `/`, `%`, Java promotes operands.

Rules simplified:

1. If either operand is `double`, result is `double`.
2. Else if either is `float`, result is `float`.
3. Else if either is `long`, result is `long`.
4. Otherwise, result is `int`.

```java
System.out.println(((Object)(1 + 2)).getClass());     // Integer after boxing
System.out.println(((Object)(1L + 2)).getClass());    // Long after boxing
System.out.println(((Object)(1.0f + 2)).getClass());  // Float after boxing
System.out.println(((Object)(1.0 + 2)).getClass());   // Double after boxing
```

### 8.3 Small integer types promote to `int`

```java
byte b = 1;
short s = 2;
char c = 3;

int result1 = b + s;
int result2 = b + c;
int result3 = s + c;
```

This fails:

```java
byte b1 = 1;
byte b2 = 2;
// byte b3 = b1 + b2; // result is int
```

### 8.4 Constant expressions can be narrowed

```java
byte b = 1 + 2; // compiles: constant int expression, value 3 fits in byte
```

But this does not:

```java
byte x = 1;
// byte y = x + 2; // x is not a compile-time constant expression here
```

Unless `x` is a constant variable:

```java
final byte x = 1;
byte y = x + 2; // compiles, expression is constant and fits
```

### 8.5 `b++` and `b += 1`

```java
byte b = 1;
b++;
b += 1;
// b = b + 1; // does not compile
```

The first two forms include implicit narrowing. The last one does not.

### 8.6 String concatenation changes the meaning of `+`

```java
System.out.println("a" + 1 + 2); // a12
System.out.println(1 + 2 + "a"); // 3a
```

Evaluation is left-to-right. Once a `String` is involved, `+` becomes string concatenation.

```java
System.out.println("sum=" + (1 + 2)); // sum=3
```

### 8.7 `char` in expressions

```java
char a = 'A';
char b = 'B';
System.out.println(a + b); // 131
```

Both `char` operands are promoted to `int`.

### 8.8 Bitwise operations and promotions

```java
byte b = 0b0000_1111;
int x = b << 2;
System.out.println(x); // 60
```

Shift operators promote small integer types to `int` too.

```java
byte b = -1;
System.out.println(b >> 1);  // -1
System.out.println(b >>> 1); // 2147483647, because b promoted to int first
```

### 8.9 Interview summary

Numeric promotion checklist:

- Any arithmetic on `byte`, `short`, or `char` usually becomes `int`.
- `float` and `double` dominate mixed expressions.
- Compound assignment hides a cast.
- String concatenation is left-associative and changes `+` semantics.

---

## 9. Wrapper Classes

Wrapper classes bridge primitive values and object-oriented APIs. They are essential for generics, collections, reflection, and nullable values, but they introduce identity, allocation, caching, and unboxing traps.

### 9.1 Wrappers are immutable

```java
Integer a = 10;
Integer b = a;
a = a + 1;

System.out.println(a); // 11
System.out.println(b); // 10
```

`a + 1` unboxes `a`, performs primitive addition, then boxes the result into another `Integer`.

### 9.2 Autoboxing is not magic-free

```java
Integer sum = 0;
for (int i = 0; i < 1_000; i++) {
    sum += i; // repeated unboxing and boxing
}
```

Conceptually:

```java
sum = Integer.valueOf(sum.intValue() + i);
```

The JIT may optimize some cases, but do not rely on wrappers for tight numeric loops.

### 9.3 Wrapper identity vs. equality

```java
Integer a = 1000;
Integer b = 1000;

System.out.println(a == b);      // false in typical cases
System.out.println(a.equals(b)); // true
```

`==` compares references when both operands are reference types.

### 9.4 Integer cache

Autoboxing uses `Integer.valueOf(int)` under the hood. To improve performance and save memory, the `Integer` class maintains an internal cache for a specific range of values.

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b); // true, normally cached

Integer c = 128;
Integer d = 128;
System.out.println(c == d); // false, typical default behavior
```

**Key details:**
- **Implementation:** The cache is implemented as a static inner class `Integer.IntegerCache`.
- **Range:** By default, it caches values from `-128` to `127` (inclusive).
- **Configurability:** The high boundary (`127`) can be adjusted using the JVM flag:
  `-XX:AutoBoxCacheMax=<size>`
  or via the system property `java.lang.Integer.IntegerCache.high`.
- **Low boundary:** The low boundary is fixed at `-128` and cannot be changed.
- **Why it exists:** Small integers are used very frequently in programs (loop counters, status codes, small constants). Reusing the same objects significantly reduces heap allocations and memory pressure.

> **Warning:** Never write business logic that depends on wrapper cache identity (using `==`). Always use `.equals()` for value comparison.

### 9.5 Wrapper caches for other types

Commonly cached:

- `Boolean`: `Boolean.TRUE`, `Boolean.FALSE`;
- `Byte`: all byte values;
- `Short`, `Integer`, `Long`: at least `-128` to `127` for `valueOf`-style caching;
- `Character`: commonly `0` to `127`.

But the only safe programming rule is: use `.equals()` for value equality.

### 9.6 `new Integer(1)` vs `Integer.valueOf(1)`

```java
Integer a = Integer.valueOf(1);
Integer b = Integer.valueOf(1);
System.out.println(a == b); // true due to cache

Integer c = new Integer(1); // deprecated, creates a new object
Integer d = new Integer(1);
System.out.println(c == d); // false
```

### 9.7 Unboxing can throw `NullPointerException`

```java
Integer n = null;

// The comparison unboxes n:
// System.out.println(n == 0); // NullPointerException
```

But this does not unbox:

```java
Integer n = null;
System.out.println(n == null); // true
```

### 9.8 Mixed primitive-wrapper comparison

```java
Integer a = 1000;
int b = 1000;
System.out.println(a == b); // true: a is unboxed
```

If one operand is primitive and the other is a wrapper, unboxing usually occurs.

### 9.9 Wrappers in collections

```java
java.util.List<Integer> list = new java.util.ArrayList<>();
list.add(1); // boxes int to Integer
int x = list.get(0); // unboxes Integer to int
```

There is no `List<int>` in current Java generics.

### 9.10 Primitive optional types

Instead of `Optional<Integer>`, prefer primitive-specialized optionals when appropriate:

```java
java.util.OptionalInt maybe = java.util.OptionalInt.of(42);
System.out.println(maybe.orElse(-1));
```

Available primitive optionals:

- `OptionalInt`
- `OptionalLong`
- `OptionalDouble`

There is no `OptionalBoolean` in the standard library.

### 9.11 Interview summary

Wrapper traps:

- `==` compares references unless unboxing happens.
- Small wrapper values may be cached.
- Autoboxing may allocate or at least create conceptual overhead.
- Unboxing `null` throws `NullPointerException`.
- Generics require reference types, so primitives become wrappers.

---

## 10. `String`: Not Primitive, But Interview-Critical

`String` is a reference type, but it has language-level support. It is immutable, final, interned for literals, and deeply connected to Unicode behavior.

### 10.1 String literals and the string pool

```java
String a = "java";
String b = "java";
System.out.println(a == b); // true: same interned literal
```

String literals are interned. Identical literals in the same runtime refer to the same canonical string object.

### 10.2 `new String("x")`

```java
String a = "x";
String b = new String("x");

System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

`new String("x")` explicitly creates a new object. It is almost never needed.

### 10.3 Compile-time concatenation

```java
String a = "ja" + "va";
String b = "java";
System.out.println(a == b); // true
```

The compiler folds constant string expressions.

```java
final String x = "ja";
String a = x + "va";
String b = "java";
System.out.println(a == b); // true, x is a compile-time constant
```

But:

```java
String x = "ja";
String a = x + "va";
String b = "java";
System.out.println(a == b); // false, runtime concatenation
```

### 10.4 `intern()`

```java
String a = new String("java");
String b = a.intern();
String c = "java";

System.out.println(a == c); // false
System.out.println(b == c); // true
```

`intern()` returns a canonical representation from the string pool.

### 10.5 String immutability

```java
String s = "abc";
s.toUpperCase();
System.out.println(s); // abc

s = s.toUpperCase();
System.out.println(s); // ABC
```

Methods that appear to modify a string return a new string.

### 10.6 String as a map key

`String` is commonly used as a map key because it is immutable and has stable `equals()` and `hashCode()` behavior.

```java
java.util.Map<String, Integer> counts = new java.util.HashMap<>();
counts.put("one", 1);
System.out.println(counts.get("one")); // 1
```

### 10.7 Concatenation in loops

```java
String s = "";
for (int i = 0; i < 10; i++) {
    s += i;
}
System.out.println(s);
```

This works, but for many iterations it can create unnecessary intermediate strings in older versions of Java. Prefer `StringBuilder` in explicit loops.

```java
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 10; i++) {
    sb.append(i);
}
String result = sb.toString();
```

In modern versions, the compiler may optimize simple cases of string concatenation in loops, but it is still good practice to use `StringBuilder` for complex or large concatenations.

### 10.8 `String.length()` and Unicode

```java
String emoji = "😀";
System.out.println(emoji.length()); // 2
```

`length()` returns the number of UTF-16 code units, not Unicode code points and not visual characters.

```java
System.out.println(emoji.codePointCount(0, emoji.length())); // 1
```

### 10.9 `char[]` vs `String` for sensitive data

Historically, `char[]` was often recommended for passwords because it can be overwritten, while `String` is immutable and may remain in memory until garbage-collected.

```java
char[] password = {'s', 'e', 'c', 'r', 'e', 't'};
java.util.Arrays.fill(password, '\0');
```

> **Comment:** This is only one part of secure handling. Modern systems require broader threat modeling, secure input APIs, and careful logging/serialization policies.

### 10.10 Text blocks

Text blocks are still strings.

```java
String json = """
    {
      "name": "Java"
    }
    """;
System.out.println(json);
```

Text blocks handle indentation and line breaks differently from ordinary string literals, but the result is still a `String`.

### 10.11 Interview summary

`String` traps:

- `String` is not primitive.
- `==` compares references.
- Literals are interned.
- Compile-time concatenation may produce interned strings.
- Runtime concatenation usually creates a new string.
- `length()` counts UTF-16 code units.

---

## 11. Arrays of Basic Types

Arrays in Java are objects with fixed length. Primitive arrays store primitive values directly; reference arrays store references.

### 11.1 Arrays are objects

```java
int[] a = {1, 2, 3};
System.out.println(a instanceof Object); // true
System.out.println(a.length);            // 3
```

`length` is a field-like property, not a method.

```java
// a.length(); // does not compile
```

### 11.2 Default values

```java
int[] ints = new int[3];
boolean[] bools = new boolean[3];
String[] strings = new String[3];

System.out.println(ints[0]);    // 0
System.out.println(bools[0]);   // false
System.out.println(strings[0]); // null
```

### 11.3 Primitive arrays vs wrapper arrays

```java
int[] primitive = new int[3];
Integer[] boxed = new Integer[3];

primitive[0] = 10;
boxed[0] = 10;

System.out.println(primitive[1]); // 0
System.out.println(boxed[1]);     // null
```

This fails:

```java
// int[] a = new Integer[3]; // incompatible types
// Integer[] b = new int[3]; // incompatible types
```

### 11.4 Array covariance

Reference arrays are covariant:

```java
String[] strings = new String[2];
Object[] objects = strings;
objects[0] = "ok";
// objects[1] = 42; // ArrayStoreException at runtime
```

The compiler allows `String[]` to be assigned to `Object[]`, but the runtime array type remains `String[]`, so storing an `Integer` fails.

Primitive arrays are not covariant with wrapper arrays or `Object[]` in the same way.

```java
int[] ints = new int[3];
Object obj = ints; // ok, int[] itself is an object
// Object[] objects = ints; // does not compile
```

### 11.5 Multidimensional arrays are arrays of arrays

```java
int[][] matrix = new int[2][3];
System.out.println(matrix.length);    // 2
System.out.println(matrix[0].length); // 3
```

Java supports jagged arrays:

```java
int[][] jagged = new int[3][];
jagged[0] = new int[1];
jagged[1] = new int[2];
jagged[2] = new int[3];
```

### 11.6 Array initialization syntax

```java
int[] a = {1, 2, 3};
int[] b = new int[] {1, 2, 3};

// a = {4, 5, 6}; // does not compile
 a = new int[] {4, 5, 6};
```

Array initializer shorthand is allowed only at declaration.

### 11.7 Varargs are arrays

```java
static void printAll(String... values) {
    System.out.println(values.length);
}

printAll("a", "b");
printAll(new String[] {"a", "b"});
```

A varargs parameter is compiled as an array parameter.

### 11.8 Varargs and primitives

```java
static void f(int... values) {
    System.out.println(values.length);
}

f(1, 2, 3); // creates/passes int[]
```

### 11.9 `Arrays.equals()` vs `==`

```java
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};

System.out.println(a == b);                  // false
System.out.println(java.util.Arrays.equals(a, b)); // true
```

For nested arrays, use `deepEquals()`.

```java
int[][] x = {{1}, {2}};
int[][] y = {{1}, {2}};
System.out.println(java.util.Arrays.equals(x, y));     // false
System.out.println(java.util.Arrays.deepEquals(x, y)); // true
```

### 11.10 Interview summary

Array traps:

- Arrays are objects.
- Primitive arrays are not wrapper arrays.
- Reference arrays are covariant, which can cause `ArrayStoreException`.
- Multidimensional arrays are arrays of arrays.
- `==` compares array references, not contents.

---

## 12. Literals and Compile-Time Constants

Literals and constant expressions determine whether narrowing conversions are allowed, whether strings are interned, and whether code compiles at all.

### 12.1 Integer literals

```java
int decimal = 100;
int hex = 0x64;
int binary = 0b0110_0100;
int octal = 0144;
```

By default, integer literals are `int` if they fit. Use `L` for `long`.

```java
long x = 100L;
```

### 12.2 Floating-point literals

```java
double d = 1.0;
float f = 1.0f;
```

Scientific notation:

```java
double avogadro = 6.022e23;
float tiny = 1e-3f;
```

Hexadecimal floating-point literals exist too:

```java
double x = 0x1.0p3; // 1.0 * 2^3 = 8.0
System.out.println(x);
```

### 12.3 Character literals

```java
char a = 'A';
char newline = '\n';
char quote = '\'';
char unicode = '\u0041';
```

A character literal must represent one UTF-16 code unit.

```java
// char emoji = '😀'; // does not compile in the usual sense: emoji needs surrogate pair
```

Use a `String` for such characters:

```java
String emoji = "😀";
```

### 12.4 String literals

```java
String s = "hello";
String escaped = "line1\nline2";
```

String literals are interned.

### 12.5 Boolean and null literals

```java
boolean ok = true;
String missing = null;
```

`null` can be assigned to reference types only.

```java
// int x = null; // does not compile
```

### 12.6 Class literals

```java
Class<Integer> wrapper = Integer.class;
Class<Integer> primitive = int.class; // type is Class<Integer> in generic form? usually use Class<?> safely
Class<Void> voidType = void.class;
```

Safer demonstration:

```java
Class<?> c1 = int.class;
Class<?> c2 = Integer.class;
Class<?> c3 = void.class;

System.out.println(c1.isPrimitive()); // true
System.out.println(c2.isPrimitive()); // false
System.out.println(c3.isPrimitive()); // true
```

### 12.7 Compile-time constants

```java
static final int A = 10;
static final int B = A + 20;
```

A compile-time constant expression can be evaluated by the compiler. This affects narrowing:

```java
final int x = 100;
byte b = x; // ok
```

But:

```java
final int x;
x = 100;
// byte b = x; // not a constant variable in the same way for this use
```

A constant variable must be `final`, of primitive type or `String`, and initialized with a constant expression.

### 12.8 Constant inlining

Public static final primitive and string constants can be inlined into other compiled classes.

```java
public class Constants {
    public static final int VERSION = 1;
}
```

If another class compiles against `VERSION = 1`, then you change it to `2` and only recompile `Constants`, the other class may still contain the old value. This is why changing public constants can require recompilation of clients.

### 12.9 Invalid underscore placements

Valid:

```java
int a = 1_000_000;
long b = 0xFF_EC_DE_5EL;
```

Invalid:

```java
// int x = _1;
// int y = 1_;
// double d = 1._0;
// long l = 0x_FF;
```

### 12.10 Interview summary

Literal traps:

- `010` is octal.
- Floating literals are `double` by default.
- `1.0f` is `float`; `1.0` is `double`.
- Compile-time constants may allow implicit narrowing.
- Public constants may be inlined into dependent class files.

---

## 13. `null`, `void`, and Special Types

Java has a few special cases that do not fit neatly into primitive or ordinary reference categories.

### 13.1 The `null` literal

`null` is assignable to any reference type.

```java
String s = null;
Object o = null;
int[] a = null;
```

But not to primitive types:

```java
// int x = null; // does not compile
```

### 13.2 The null type

Conceptually, `null` has a special null type. You cannot name this type directly.

```java
// There is no syntax like: nulltype x = null;
```

It is useful in specification reasoning because `null` must be assignable to all reference types.

### 13.3 Null unboxing traps

```java
Integer n = null;
// int x = n; // NullPointerException
```

Less obvious:

```java
Integer n = null;
// System.out.println(n + 1); // NullPointerException before addition
```

Even comparisons may unbox:

```java
Integer n = null;
// System.out.println(n == 0); // NullPointerException
System.out.println(n == null); // true, no unboxing
```

### 13.4 Overload resolution with `null`

```java
static void f(String s) { System.out.println("String"); }
static void f(Object o) { System.out.println("Object"); }

public static void main(String[] args) {
    f(null); // String: more specific overload
}
```

But this is ambiguous:

```java
static void g(String s)  { }
static void g(Integer i) { }

// g(null); // does not compile: ambiguous
```

You can disambiguate with a cast:

```java
g((String) null);
g((Integer) null);
```

### 13.5 `void` keyword

`void` means a method returns no value.

```java
static void log(String message) {
    System.out.println(message);
}
```

You cannot assign a `void` result.

```java
// Object x = log("hi"); // does not compile
```

### 13.6 `Void` class

`Void` is a reference type whose main practical uses are generics and reflection.

```java
java.util.concurrent.Callable<Void> task = () -> {
    System.out.println("work");
    return null;
};
```

`Void` values are almost always `null`.

### 13.7 `void.class` and `Void.TYPE`

```java
System.out.println(void.class == Void.TYPE); // true
System.out.println(void.class.isPrimitive()); // true
System.out.println(Void.class.isPrimitive()); // false
```

`void.class` represents the primitive pseudo-type `void`; `Void.class` represents the wrapper class `java.lang.Void`.

### 13.8 Interview summary

Special-type traps:

- `null` works only with reference types.
- Unboxing `null` throws `NullPointerException`.
- `null` can make overloads ambiguous.
- `void` is not `Void`.
- `void.class` is primitive; `Void.class` is not.

---

## 14. Overload Resolution and Basic Types

Overload resolution is one of the richest sources of Java interview puzzles. The compiler chooses the most specific applicable method according to strict rules.

### 14.1 Exact match beats everything

```java
static void f(int x)  { System.out.println("int"); }
static void f(long x) { System.out.println("long"); }

f(10); // int
```

The literal `10` is an `int`, so `f(int)` is exact.

### 14.2 Widening beats boxing

```java
static void f(long x)    { System.out.println("long"); }
static void f(Integer x) { System.out.println("Integer"); }

f(10); // long
```

The compiler prefers primitive widening (`int -> long`) over boxing (`int -> Integer`).

### 14.3 Boxing beats varargs

```java
static void f(Integer x) { System.out.println("Integer"); }
static void f(int... x)  { System.out.println("int..."); }

f(10); // Integer
```

Varargs are considered after fixed-arity methods.

### 14.4 Widening reference after boxing

```java
static void f(Object o) { System.out.println("Object"); }

f(10); // int -> Integer -> Object
```

Boxing followed by widening reference is allowed.

### 14.5 No widening primitive then boxing

```java
static void f(Long x) { System.out.println("Long"); }

// f(10); // does not compile: Java will not do int -> long -> Long
f(10L);   // ok
```

### 14.6 `char` overloads

```java
static void f(int x)  { System.out.println("int"); }
static void f(char x) { System.out.println("char"); }

f('A'); // char: exact match
```

Without `char` overload:

```java
static void g(int x) { System.out.println("int"); }
g('A'); // int: char widens to int
```

### 14.7 Ambiguous overloads

```java
static void f(Integer x, long y) { }
static void f(long x, Integer y) { }

// f(1, 1); // ambiguous
```

Both methods require one widening and one boxing. Neither is strictly better.

### 14.8 `null` overloads

```java
static void f(Object o) { System.out.println("Object"); }
static void f(String s) { System.out.println("String"); }

f(null); // String
```

But:

```java
static void g(String s) { }
static void g(StringBuilder sb) { }

// g(null); // ambiguous
```

### 14.9 Varargs ambiguity

```java
static void f(int... x)     { System.out.println("int..."); }
static void f(Integer... x) { System.out.println("Integer..."); }

// f(); // ambiguous
```

No argument provides no type clue.

### 14.10 Interview summary

Useful priority memory:

```text
exact > widening primitive > boxing > varargs
```

But real overload resolution also considers specificity, arity, generic inference, and ambiguity. For basic-type interviews, the priority chain above solves many puzzles.

---

## 15. Equality, Identity, and Comparison

Java has several notions of sameness: primitive value equality, reference identity, object equality, ordering comparison, and collection-specific equality.

### 15.1 Primitive equality

```java
int a = 1000;
int b = 1000;
System.out.println(a == b); // true
```

For primitives, `==` compares values.

### 15.2 Reference identity

```java
String a = new String("x");
String b = new String("x");

System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

For references, `==` compares whether both references point to the same object.

### 15.3 Wrapper equality trap

```java
Integer a = 1000;
Integer b = 1000;
System.out.println(a == b);      // false in typical cases
System.out.println(a.equals(b)); // true
```

Small cached values may mislead you:

```java
Integer x = 100;
Integer y = 100;
System.out.println(x == y); // true, due to cache
```

Do not infer that `==` is safe for wrappers.

### 15.4 Primitive-wrapper comparison

```java
Integer a = 1000;
int b = 1000;
System.out.println(a == b); // true: a unboxed
```

If one side is primitive, the wrapper is usually unboxed.

### 15.5 `NaN` equality

```java
double nan = Double.NaN;
System.out.println(nan == nan); // false
```

But object equality differs:

```java
Double a = Double.NaN;
Double b = Double.NaN;
System.out.println(a.equals(b)); // true
```

### 15.6 `0.0` and `-0.0`

```java
double a = 0.0;
double b = -0.0;
System.out.println(a == b); // true
```

But:

```java
Double x = 0.0;
Double y = -0.0;
System.out.println(x.equals(y)); // false
System.out.println(Double.compare(0.0, -0.0)); // 1
```

### 15.7 `compareTo()` vs `equals()`

Some classes have comparison behavior that is not identical to equality behavior. A famous example is `BigDecimal`.

```java
java.math.BigDecimal a = new java.math.BigDecimal("1.0");
java.math.BigDecimal b = new java.math.BigDecimal("1.00");

System.out.println(a.equals(b));    // false: scale differs
System.out.println(a.compareTo(b)); // 0: numerically equal
```

This matters in sorted collections:

```java
java.util.Set<java.math.BigDecimal> hashSet = new java.util.HashSet<>();
hashSet.add(new java.math.BigDecimal("1.0"));
hashSet.add(new java.math.BigDecimal("1.00"));
System.out.println(hashSet.size()); // 2

java.util.Set<java.math.BigDecimal> treeSet = new java.util.TreeSet<>();
treeSet.add(new java.math.BigDecimal("1.0"));
treeSet.add(new java.math.BigDecimal("1.00"));
System.out.println(treeSet.size()); // 1
```

### 15.8 Arrays and equality

```java
int[] a = {1, 2};
int[] b = {1, 2};

System.out.println(a == b);       // false
System.out.println(a.equals(b));  // false, same as Object identity
System.out.println(java.util.Arrays.equals(a, b)); // true
```

### 15.9 Interview summary

Equality checklist:

- Primitive `==`: value equality.
- Reference `==`: identity.
- `.equals()`: class-defined value equality.
- Wrapper `==`: dangerous because of caching and unboxing.
- Floating-point equality has `NaN` and `-0.0` traps.
- Arrays need `Arrays.equals()` or `Arrays.deepEquals()`.

---

## 16. Basic Types and Generics

Java generics work with reference types, not primitive types. This single fact explains many design patterns and performance costs.

### 16.1 `List<int>` is illegal

```java
// java.util.List<int> xs = new java.util.ArrayList<>(); // does not compile
java.util.List<Integer> xs = new java.util.ArrayList<>();
```

Generics use reference types, so primitive values are boxed.

```java
xs.add(10);       // boxes int to Integer
int x = xs.get(0); // unboxes Integer to int
```

### 16.2 Type erasure

At runtime, generic type parameters are erased.

```java
java.util.List<Integer> ints = new java.util.ArrayList<>();
java.util.List<String> strings = new java.util.ArrayList<>();

System.out.println(ints.getClass() == strings.getClass()); // true
```

Both are just `ArrayList` at runtime.

### 16.3 Why erasure matters for primitives

If Java allowed `List<int>` under ordinary erased generics, what would the runtime representation be? An `int` cannot be used where `Object` is expected. That is one reason primitive specialization requires special language/runtime support, not just syntax.

### 16.4 Primitive-specialized streams

```java
java.util.stream.IntStream.range(0, 5)
    .map(x -> x * x)
    .forEach(System.out::println);
```

Primitive stream types avoid boxing:

- `IntStream`
- `LongStream`
- `DoubleStream`

There are no standard `ByteStream`, `ShortStream`, `CharStream`, or `BooleanStream`.

### 16.5 Primitive functional interfaces

```java
java.util.function.IntPredicate even = x -> x % 2 == 0;
java.util.function.IntUnaryOperator square = x -> x * x;
java.util.function.ToIntFunction<String> length = String::length;
```

These avoid boxing compared with generic functional interfaces like `Function<Integer, Integer>`.

### 16.6 Generic arrays

```java
// T[] array = new T[10]; // not allowed directly inside a generic class
```

Type erasure makes generic array creation unsafe.

Workarounds often require reflection or collection types.

```java
@SuppressWarnings("unchecked")
static <T> T[] newArray(Class<T> type, int length) {
    return (T[]) java.lang.reflect.Array.newInstance(type, length);
}
```

### 16.7 Varargs with generics

```java
@SafeVarargs
static <T> java.util.List<T> listOf(T... values) {
    return java.util.Arrays.asList(values);
}
```

Generic varargs can produce heap pollution warnings because varargs are arrays and arrays know their runtime component type, while generics are erased.

### 16.8 Future direction: primitive specialization

Java's current mainstream generics do not support primitive type arguments directly. This is why the standard library contains separate primitive-specialized APIs such as `IntStream` and `OptionalInt`.

> **Comment:** When discussing future Java features in interviews, be careful to distinguish stable language behavior from experimental or preview work.

### 16.9 Interview summary

Generics traps:

- `List<int>` is illegal.
- `List<Integer>` boxes values.
- Erasure means generic type parameters are not ordinary runtime types.
- Primitive-specialized APIs exist to avoid boxing overhead.
- Generic arrays and generic varargs require caution.

---

## 17. Reflection and Class Objects for Basic Types

Reflection exposes primitive types, wrapper types, arrays, and `void` through `Class` objects. This is another place where primitives and wrappers are easy to confuse.

### 17.1 `int.class` vs `Integer.class`

```java
Class<?> a = int.class;
Class<?> b = Integer.class;

System.out.println(a == b);          // false
System.out.println(a.isPrimitive()); // true
System.out.println(b.isPrimitive()); // false
```

### 17.2 `Integer.TYPE`

```java
System.out.println(Integer.TYPE == int.class); // true
System.out.println(Void.TYPE == void.class);   // true
```

Wrapper classes expose their primitive class object through the `TYPE` field.

### 17.3 Reflective method lookup

```java
class Example {
    public void f(int x) { }
}

java.lang.reflect.Method m = Example.class.getMethod("f", int.class);
System.out.println(m);
```

This fails:

```java
// Example.class.getMethod("f", Integer.class); // NoSuchMethodException
```

Reflection method lookup requires the exact declared parameter types. Autoboxing does not apply to `getMethod()` lookup.

### 17.4 Reflective invocation and boxing

```java
class Example {
    public void f(int x) {
        System.out.println(x);
    }
}

Example e = new Example();
java.lang.reflect.Method m = Example.class.getMethod("f", int.class);
m.invoke(e, Integer.valueOf(10)); // ok: reflection can unbox for invocation
```

Lookup and invocation are different phases.

### 17.5 Array class names

```java
System.out.println(int[].class.getName());      // [I
System.out.println(String[].class.getName());   // [Ljava.lang.String;
System.out.println(int[][].class.getName());    // [[I
```

These names come from JVM descriptor conventions.

### 17.6 Creating primitive arrays reflectively

```java
Object array = java.lang.reflect.Array.newInstance(int.class, 3);
java.lang.reflect.Array.setInt(array, 0, 42);
System.out.println(java.lang.reflect.Array.getInt(array, 0)); // 42
```

The result type is `Object`, but the runtime object is an `int[]`.

```java
System.out.println(array.getClass()); // class [I
```

### 17.7 `void.class`

```java
class Example {
    public void run() { }
}

java.lang.reflect.Method m = Example.class.getMethod("run");
System.out.println(m.getReturnType() == void.class); // true
```

### 17.8 Interview summary

Reflection traps:

- `int.class != Integer.class`.
- `Integer.TYPE == int.class`.
- `getMethod()` needs exact parameter types.
- Autoboxing does not help reflective lookup.
- Array class names look strange because they use JVM descriptors.

---

## 18. Memory, Performance, and JVM Reality

Interview questions often mix Java language rules with JVM implementation details. A strong answer separates specification from implementation.

### 18.1 Stack vs heap oversimplification

A common explanation says "primitives live on the stack, objects live on the heap." This is too simplistic.

```java
class Point {
    int x;
    int y;
}
```

The `int` fields are part of the object. If the object is allocated on the heap, the primitive fields are stored within that object. Local primitive variables may be stored in stack slots, registers, or optimized away.

### 18.2 Escape analysis

The JIT may determine that an object does not escape a method and optimize allocation away.

```java
static int sum() {
    Point p = new Point();
    p.x = 1;
    p.y = 2;
    return p.x + p.y;
}
```

A JVM may scalar-replace `p` and avoid allocating a real object. This is an optimization, not a language guarantee.

### 18.3 Wrapper overhead

```java
Integer x = 42;
```

Conceptually, this is a reference to an object containing an `int` value. The object has overhead: header, alignment, and reference indirection. The exact size depends on JVM implementation and settings.

### 18.4 Primitive arrays and locality

```java
int[] a = new int[1_000_000];
```

An `int[]` stores values contiguously in the array object. This is usually cache-friendly.

```java
Integer[] b = new Integer[1_000_000];
```

An `Integer[]` stores references. The actual `Integer` objects may be scattered in memory, causing pointer chasing and higher memory usage.

### 18.5 Boxing elimination

JIT compilers can often eliminate boxing in hot code.

```java
static int compute(int n) {
    Integer x = n;
    return x + 1;
}
```

A good JIT may optimize this as primitive arithmetic. But if wrappers escape into collections, reflection, fields, synchronization, or polymorphic calls, optimization may be harder.

### 18.6 Synchronizing on wrappers is dangerous

```java
Integer lock = 1;
synchronized (lock) {
    // bad idea
}
```

Because small wrapper values can be cached and shared, synchronizing on boxed values can accidentally share locks with unrelated code. Modern Java also warns against synchronizing on value-based classes.

### 18.7 Measuring performance

Bad microbenchmark:

```java
long start = System.nanoTime();
for (int i = 0; i < 1_000_000; i++) {
    // code under test
}
long end = System.nanoTime();
System.out.println(end - start);
```

This may be distorted by warmup, dead-code elimination, JIT compilation, GC, CPU frequency changes, and many other effects.

Use JMH for serious Java micro-benchmarks.

### 18.8 Language guarantees vs JVM details

Language guarantee:

```java
int x = Integer.MAX_VALUE;
System.out.println(x + 1); // specified wraparound
```

Implementation detail:

```java
// exact object header size, compressed reference mode, field layout, register allocation
```

A strong answer says: "The Java language guarantees X. HotSpot or another JVM may implement it using Y, but that is not portable language behavior."

### 18.9 Interview summary

Performance traps:

- Do not overstate stack/heap rules.
- Primitive arrays are usually much more compact than wrapper arrays.
- Boxing may be optimized away, but not always.
- Object layout is JVM-specific.
- Use JMH for serious micro-benchmarks.

---

## 19. Serialization, Parsing, and Formatting

Basic types frequently cross boundaries as text: command-line arguments, JSON, database values, logs, CSV, and network protocols. Parsing and formatting are full of edge cases.

### 19.1 `parseInt()` vs `valueOf()`

```java
int a = Integer.parseInt("123");
Integer b = Integer.valueOf("123");

System.out.println(a); // primitive int
System.out.println(b); // Integer object
```

`parseInt()` returns a primitive. `valueOf()` returns a wrapper.

### 19.2 Radix-based parsing

```java
System.out.println(Integer.parseInt("ff", 16)); // 255
System.out.println(Integer.parseInt("1010", 2)); // 10
```

`parseInt("010")` does not treat the string as octal automatically.

```java
System.out.println(Integer.parseInt("010")); // 10
```

Java source literal `010` is octal; string parsing is decimal unless you specify otherwise.

### 19.3 Decode methods

```java
System.out.println(Integer.decode("0x10")); // 16
System.out.println(Integer.decode("010"));  // 8
```

`decode()` understands prefixes such as `0x`, `#`, and leading zero for octal.

### 19.4 Overflow during parsing

```java
// Integer.parseInt("2147483648"); // NumberFormatException
System.out.println(Long.parseLong("2147483648")); // ok
```

Parsing fails if the value is outside the target type's range.

### 19.5 Unsigned parsing helpers

```java
int x = Integer.parseUnsignedInt("4294967295");
System.out.println(x);                  // -1 as signed int bits
System.out.println(Integer.toUnsignedString(x)); // 4294967295
```

Java lacks unsigned primitive integer types except `char`, but it provides helper methods for unsigned interpretation of `int` and `long` bit patterns.

### 19.6 Floating-point parsing

```java
System.out.println(Double.parseDouble("NaN"));      // NaN
System.out.println(Double.parseDouble("Infinity")); // Infinity
System.out.println(Double.parseDouble("1e3"));      // 1000.0
```

Invalid input throws `NumberFormatException`.

### 19.7 Formatting

```java
System.out.printf("%d%n", 42);
System.out.printf("%.2f%n", 1.23456); // 1.23
```

Formatting may be locale-sensitive.

```java
java.text.NumberFormat nf = java.text.NumberFormat.getInstance(java.util.Locale.GERMANY);
System.out.println(nf.format(1234.56)); // e.g. 1.234,56
```

### 19.8 `Double.toString()` round-trip behavior

Modern `Double.toString()` is designed to produce a decimal representation that can round-trip back to the same `double` value.

```java
double d = 0.1;
String s = Double.toString(d);
double parsed = Double.parseDouble(s);
System.out.println(d == parsed); // true
```

### 19.9 `BigDecimal` from strings

```java
java.math.BigDecimal money = new java.math.BigDecimal("12.34");
```

For decimal business values, parsing from strings is usually safer than constructing from binary floating-point values.

### 19.10 Interview summary

Parsing traps:

- `parseInt()` returns primitive; `valueOf()` returns wrapper.
- Source literal `010` is octal, but `Integer.parseInt("010")` is decimal.
- `Integer.decode("010")` treats it as octal.
- Overflow during parsing throws `NumberFormatException`.
- Unsigned helpers reinterpret signed bit patterns.

---

## 20. Classic Interview Puzzles

This section collects representative puzzles and explains the principle behind each one.

### 20.1 `byte` assignment puzzle

```java
byte b = 1;
// b = b + 1; // does not compile
b += 1;       // compiles
```

Explanation: `b + 1` is an `int`. Compound assignment includes an implicit cast back to `byte`.

### 20.2 Integer cache puzzle

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b); // true

Integer c = 128;
Integer d = 128;
System.out.println(c == d); // false in typical default settings
```

Explanation: small wrapper values are cached. Do not use `==` for wrapper value equality.

### 20.3 `NaN` puzzle

```java
double x = Double.NaN;
System.out.println(x == x); // false
```

Explanation: NaN is not equal to any value, including itself.

### 20.4 `Math.abs()` puzzle

```java
System.out.println(Math.abs(Integer.MIN_VALUE)); // -2147483648
```

Explanation: the positive counterpart of `Integer.MIN_VALUE` does not fit in `int`.

### 20.5 String concatenation puzzle

```java
System.out.println("a" + 1 + 2); // a12
System.out.println(1 + 2 + "a"); // 3a
```

Explanation: `+` is left-associative. Once a string appears, further `+` operations are string concatenation.

### 20.6 Emoji length puzzle

```java
String s = "😀";
System.out.println(s.length()); // 2
```

Explanation: `String.length()` counts UTF-16 code units. The emoji uses a surrogate pair.

### 20.7 `char` arithmetic puzzle

```java
char c = 'A';
System.out.println(c + 1); // 66
```

Explanation: `char` is promoted to `int` in arithmetic expressions.

### 20.8 Generic primitive puzzle

```java
// java.util.List<int> list = new java.util.ArrayList<>(); // does not compile
```

Explanation: Java generics require reference types.

### 20.9 Null unboxing puzzle

```java
Integer x = null;
// System.out.println(x + 1); // NullPointerException
```

Explanation: `x` must be unboxed before addition.

### 20.10 Overload puzzle

```java
static void f(long x) { System.out.println("long"); }
static void f(Integer x) { System.out.println("Integer"); }

f(1); // long
```

Explanation: primitive widening beats boxing.

### 20.11 `null` overload puzzle

```java
static void f(String s) { System.out.println("String"); }
static void f(Object o) { System.out.println("Object"); }

f(null); // String
```

Explanation: `String` is more specific than `Object`.

Ambiguous version:

```java
static void g(String s) { }
static void g(Integer i) { }
// g(null); // ambiguous
```

### 20.12 Floating zero puzzle

```java
System.out.println(0.0 == -0.0); // true
System.out.println(1.0 / 0.0);   // Infinity
System.out.println(1.0 / -0.0);  // -Infinity
```

Explanation: primitive equality treats zeros as equal, but arithmetic can distinguish their signs.

### 20.13 `final` constant narrowing puzzle

```java
final int x = 100;
byte b = x; // compiles
```

But:

```java
int y = 100;
// byte c = y; // does not compile
```

Explanation: compile-time constant expressions can be implicitly narrowed if they fit.

### 20.14 Interview summary

For every puzzle, identify:

1. Are operands primitive or reference?
2. Is numeric promotion happening?
3. Is boxing or unboxing happening?
4. Is overload resolution involved?
5. Is this compile-time constant behavior?
6. Is equality comparing values or references?

---

## 21. Summary Tables

This section is designed for quick review.

### 21.1 Primitive types

| Type      | Category                  |        Bits | Default field/array value |
|-----------|---------------------------|------------:|---------------------------|
| `byte`    | signed integer            |           8 | `0`                       |
| `short`   | signed integer            |          16 | `0`                       |
| `int`     | signed integer            |          32 | `0`                       |
| `long`    | signed integer            |          64 | `0L`                      |
| `float`   | floating-point            |          32 | `0.0f`                    |
| `double`  | floating-point            |          64 | `0.0d`                    |
| `char`    | unsigned UTF-16 code unit |          16 | `'\u0000'`                |
| `boolean` | logical                   | unspecified | `false`                   |

### 21.2 Wrapper classes

| Primitive | Wrapper     | Notes                             |
|-----------|-------------|-----------------------------------|
| `byte`    | `Byte`      | all values commonly cached        |
| `short`   | `Short`     | small values cached via `valueOf` |
| `int`     | `Integer`   | at least `-128..127` cached       |
| `long`    | `Long`      | at least `-128..127` cached       |
| `float`   | `Float`     | no small-value cache expectation  |
| `double`  | `Double`    | no small-value cache expectation  |
| `char`    | `Character` | small values commonly cached      |
| `boolean` | `Boolean`   | `TRUE` and `FALSE`                |

### 21.3 Numeric promotion

| Expression       | Result type |
|------------------|-------------|
| `byte + byte`    | `int`       |
| `short + short`  | `int`       |
| `char + char`    | `int`       |
| `int + long`     | `long`      |
| `long + float`   | `float`     |
| `float + double` | `double`    |

### 21.4 Conversion examples

| Code                   | Compiles? | Reason                              |
|------------------------|----------:|-------------------------------------|
| `long x = 1;`          |       yes | widening primitive                  |
| `Long x = 1;`          |        no | no `int -> long -> Long` chain      |
| `Object x = 1;`        |       yes | box to `Integer`, widen to `Object` |
| `byte b = 100;`        |       yes | constant fits                       |
| `byte b = 128;`        |        no | constant does not fit               |
| `byte b = (byte) 128;` |       yes | explicit narrowing                  |

### 21.5 Overload priority quick table

| Priority | Example                               |
|---------:|---------------------------------------|
|        1 | exact match: `f(int)` for `1`         |
|        2 | widening primitive: `f(long)` for `1` |
|        3 | boxing: `f(Integer)` for `1`          |
|        4 | varargs: `f(int...)` for `1`          |

### 21.6 Equality quick table

| Operands              | `==` means                                  |
|-----------------------|---------------------------------------------|
| primitive + primitive | value equality                              |
| reference + reference | identity equality                           |
| primitive + wrapper   | wrapper is unboxed, value equality          |
| wrapper + wrapper     | reference identity                          |
| `String` + `String`   | reference identity, unless `.equals()` used |
| array + array         | reference identity                          |

### 21.7 Common traps

| Trap                 | Correct explanation                         |
|----------------------|---------------------------------------------|
| `byte + byte`        | result is `int`                             |
| `b += 1`             | hidden narrowing cast                       |
| `Integer == Integer` | reference comparison unless unboxing occurs |
| `NaN == NaN`         | false                                       |
| `0.0 == -0.0`        | true for primitives                         |
| emoji length         | often 2 UTF-16 code units                   |
| `List<int>`          | illegal; generics require references        |
| `Long x = 1`         | no widening-then-boxing                     |

---

## 22. Practical Checklist for Interviews

This checklist is the compact reasoning procedure to apply when faced with Java basic-type puzzles.

### 22.1 Identify the category first

Ask what each symbol represents:

```text
primitive value?
wrapper reference?
String reference?
array reference?
null?
compile-time constant?
```

Most mistakes come from mixing categories.

### 22.2 Check initialization context

```java
class C {
    int field; // default 0

    void m() {
        int local;
        // System.out.println(local); // not initialized
    }
}
```

Fields and array elements get defaults. Local variables must be definitely assigned.

### 22.3 Check numeric promotion

```java
byte a = 1;
byte b = 2;
// byte c = a + b; // result is int
```

If `byte`, `short`, or `char` participates in arithmetic, assume `int` unless a larger type is present.

### 22.4 Check compound assignment

```java
byte b = 127;
b += 1;
System.out.println(b); // -128
```

Compound assignment includes an implicit cast and can hide overflow.

### 22.5 Check boxing and unboxing

```java
Integer x = null;
// int y = x; // NullPointerException
```

Whenever primitives and wrappers meet, ask whether boxing or unboxing occurs.

### 22.6 Check equality meaning

```java
Integer a = 1000;
Integer b = 1000;
System.out.println(a == b);      // reference identity
System.out.println(a.equals(b)); // value equality
```

Do not trust `==` with wrappers or strings unless you specifically mean identity.

### 22.7 Check literal types

```java
// float f = 1.0; // double literal
float f = 1.0f;
long l = 1L;
int octal = 010; // 8
```

Literal syntax affects type and value.

### 22.8 Check compile-time constants

```java
final int x = 10;
byte b = x; // ok
```

But a runtime value needs an explicit cast.

### 22.9 Check overload resolution

```java
static void f(long x) { System.out.println("long"); }
static void f(Integer x) { System.out.println("Integer"); }

f(1); // long
```

Remember:

```text
exact > widening primitive > boxing > varargs
```

Then check specificity and ambiguity.

### 22.10 Check Unicode assumptions

```java
String s = "😀";
System.out.println(s.length()); // 2
```

`char` is not always a full character. `String.length()` is not a human-visible character count.

### 22.11 Check floating-point special values

```java
System.out.println(Double.NaN == Double.NaN); // false
System.out.println(0.0 == -0.0);              // true
System.out.println(1.0 / 0.0);                // Infinity
```

Floating-point has `NaN`, infinities, signed zero, underflow, and precision limits.

### 22.12 Separate specification from implementation

Good answer:

> Java specifies that `int` overflow wraps around. The exact machine instruction or JIT optimization is implementation-specific.

Bad answer:

> It works this way because everything is on the stack.

### 22.13 Final interview strategy

For any code puzzle, walk through this sequence:

1. Determine declared types.
2. Determine literal types.
3. Apply compile-time constant rules.
4. Apply numeric promotion.
5. Apply boxing/unboxing conversions.
6. Apply overload resolution if methods are involved.
7. Determine whether `==` is primitive equality or reference identity.
8. Look for special cases: `NaN`, `-0.0`, `null`, `char`, surrogate pairs, wrapper caches.
9. Separate what Java guarantees from what a particular JVM may optimize.

If you can do that calmly, most "obscure" Java basic-type interview questions become mechanical rather than mysterious.

---

# Appendix: Compact Practice Snippets

Use these as quick self-tests.

```java
byte b = 1;
// b = b + 1;
b += 1;
System.out.println(b);
```

```java
Integer a = 127, b = 127;
Integer c = 128, d = 128;
System.out.println(a == b);
System.out.println(c == d);
```

```java
System.out.println("x" + 1 + 2);
System.out.println(1 + 2 + "x");
```

```java
String s = "😀";
System.out.println(s.length());
System.out.println(s.codePointCount(0, s.length()));
```

```java
Double x = 0.0;
Double y = -0.0;
System.out.println(0.0 == -0.0);
System.out.println(x.equals(y));
```

```java
static void f(long x) { System.out.println("long"); }
static void f(Integer x) { System.out.println("Integer"); }
// f(1);
```
