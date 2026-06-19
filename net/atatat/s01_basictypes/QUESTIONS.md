# Advanced Java Basic Types — Interview Question Bank

This document is a companion question bank for the chapter **Advanced and Obscure Facts About Basic Java Types**.

It follows the same 22-subchapter structure and turns each subject into advanced interview material:

- short theoretical questions;
- code-reading puzzles;
- “does it compile?” questions;
- expected answer outlines;
- comments explaining the actual trap.

The goal is not to memorize isolated trivia. The goal is to train a reliable interview habit:

1. identify the exact type of every expression;
2. check whether conversion, promotion, boxing, unboxing, or overload resolution happens;
3. distinguish Java language rules from JVM implementation details;
4. explain not only the result, but **why** Java is required to behave that way.

Most examples can be pasted into a `main` method or JShell.

```java
public class Demo {
    public static void main(String[] args) {
        // paste examples here
    }
}
```

---


## 1. What “Basic Types” Actually Mean in Java

### Question 1.1 — Is `String` a basic type?

**Question**

Many developers informally say that Java has “basic types” and include `String` in the list. Is `String` a primitive type, a basic type, or something else?

**Expected answer**

`String` is not a primitive type. It is a final reference type with special language support:

- string literals exist;
- string literals are interned;
- `+` is overloaded by the language for string concatenation;
- `String` objects are immutable;
- `String` has special treatment in class files and constant pools.

**Trap**

Do not confuse “commonly used built-in class” with “primitive type”. The Java primitive types are only:

```java
byte, short, int, long, float, double, char, boolean
```

---

### Question 1.2 — Are arrays primitive or reference types?

**Question**

What is the type category of `int[]`?

```java
int[] a = {1, 2, 3};

System.out.println(a instanceof Object);
System.out.println(a.getClass());
```

**Expected answer**

`int[]` is a reference type. Arrays are objects in Java, even arrays whose elements are primitives.

`a instanceof Object` prints `true`.

**Comment**

The elements are primitive `int` values, but the array itself has identity, a runtime class, a `length` field, and inherits methods from `Object`.

---

### Question 1.3 — Can primitive types have `Class` objects?

**Question**

What does this print?

```java
System.out.println(int.class);
System.out.println(Integer.class);
System.out.println(Integer.TYPE);
System.out.println(int.class == Integer.TYPE);
```

**Expected answer**

`int.class` is a `Class` object representing the primitive type `int`.

`Integer.class` represents the wrapper class.

`Integer.TYPE` is the same primitive `Class` object as `int.class`, so the final comparison is `true`.

**Trap**

Primitive values are not objects, but primitive types still have `Class` metadata.

---

### Question 1.4 — Is `char` Java’s unsigned integer type?

**Question**

Java is often said to have no unsigned primitive integer types. Is `char` an exception?

**Expected answer**

`char` is an unsigned 16-bit integral primitive type, but it is not a general-purpose unsigned integer type. It represents UTF-16 code units and participates in numeric promotion.

```java
char c = 65535;
System.out.println((int) c); // 65535
```

**Trap**

`char` is unsigned numerically, but treating it as a “small unsigned int” usually leads to Unicode and readability bugs.

---

### Question 1.5 — What is the difference between `void` and `Void`?

**Question**

Can `void` be used as a type argument? Can `Void` be used as a return type?

**Expected answer**

`void` is a keyword used for methods that return no value. It is not a normal type and cannot be used as a generic argument.

`Void` is a reference class, commonly used as a placeholder in generics:

```java
Callable<Void> task = () -> {
    System.out.println("work");
    return null;
};
```

**Trap**

A method returning `Void` must return a reference, usually `null`. A method returning `void` returns nothing.

---

### Question 1.6 — Does Java have a bottom type?

**Question**

What is the type of the `null` literal? Is it the same as `Object`?

**Expected answer**

The `null` literal has the special null type. It can be assigned to any reference type, but not to primitive types.

```java
String s = null;
Object o = null;
// int x = null; // does not compile
```

**Trap**

The null type has no name in source code. It is not `Object`, but it is assignment-compatible with reference types.

---

## 2. Primitive Types: Small, Simple, and Full of Traps

### Question 2.1 — Field defaults vs local variables

**Question**

Does this compile?

```java
class Demo {
    int field;

    void test() {
        int local;
        System.out.println(field);
        System.out.println(local);
    }
}
```

**Expected answer**

It does not compile because `local` may not have been initialized.

Fields receive default values. Local variables do not.

**Comment**

`field` defaults to `0`. `local` must be definitely assigned before use.

---

### Question 2.2 — Is integer overflow undefined?

**Question**

What does this print?

```java
int x = Integer.MAX_VALUE;
System.out.println(x + 1);
```

**Expected answer**

It prints `-2147483648`.

Java integer overflow is defined by two’s-complement wraparound semantics for `int` and `long`.

**Trap**

Unlike C and C++, signed integer overflow in Java is not undefined behavior.

---

### Question 2.3 — Do primitive variables have identity?

**Question**

Can you synchronize on an `int`, call methods on it, or compare its identity?

**Expected answer**

No. Primitive values are not objects and have no identity.

```java
int x = 10;
// synchronized (x) {}   // does not compile
// x.toString();         // does not compile
```

To call methods, boxing or explicit wrapper usage is involved:

```java
Integer boxed = x;
System.out.println(boxed.toString());
```

**Trap**

Autoboxing can hide the difference in simple code, but the primitive itself still has no identity.

---

### Question 2.4 — What is the size of `boolean`?

**Question**

How many bits does a Java `boolean` occupy?

**Expected answer**

The Java language specification does not define the storage size of `boolean`.

The JVM and object layout are implementation details. A `boolean[]` and a `boolean` field may be represented differently by a particular JVM.

**Trap**

An interview answer like “one bit” or “one byte” is usually too confident. The correct answer separates language semantics from JVM implementation.

---

### Question 2.5 — Are primitives passed by reference?

**Question**

What does this print?

```java
static void change(int x) {
    x = 100;
}

public static void main(String[] args) {
    int a = 10;
    change(a);
    System.out.println(a);
}
```

**Expected answer**

It prints `10`.

Java is pass-by-value. For primitives, the value is copied. Assigning to the parameter does not affect the caller’s variable.

**Trap**

Java is also pass-by-value for object references. The reference value is copied.

---

### Question 2.6 — Can primitive layout be relied on for performance reasoning?

**Question**

A candidate says: “Local primitive variables are always stored on the stack, objects are always on the heap.” Is this correct?

**Expected answer**

It is an oversimplification.

The Java language does not expose stack/heap placement as a semantic guarantee. The JVM may optimize using escape analysis, scalar replacement, registers, and other techniques.

**Trap**

Use stack/heap vocabulary carefully. For interviews, explain semantics first, implementation second.

---

## 3. Integer Types: `byte`, `short`, `int`, `long`

### Question 3.1 — Why does `byte + byte` not produce `byte`?

**Question**

Does this compile?

```java
byte a = 1;
byte b = 2;
byte c = a + b;
```

**Expected answer**

It does not compile.

`byte`, `short`, and `char` are promoted to `int` in arithmetic expressions. Therefore `a + b` has type `int`.

Correct versions:

```java
byte c1 = (byte) (a + b);

byte c2 = 1 + 2; // compile-time constant expression fits into byte
```

**Trap**

The compiler treats constant expressions differently from runtime expressions.

---

### Question 3.2 — Why does compound assignment compile?

**Question**

Why does the second assignment compile while the first one does not?

```java
byte b = 1;

// b = b + 1; // does not compile
b += 1;      // compiles
```

**Expected answer**

`b = b + 1` fails because `b + 1` is an `int`.

`b += 1` includes an implicit cast equivalent to:

```java
b = (byte) (b + 1);
```

**Trap**

Compound assignment can silently narrow and overflow.

---

### Question 3.3 — Compile-time narrowing with `final`

**Question**

Does this compile?

```java
final int x = 100;
byte b = x;
```

What about this?

```java
int y = 100;
byte c = y;
```

**Expected answer**

The first compiles because `x` is a compile-time constant expression and its value fits in `byte`.

The second does not compile because `y` is a runtime variable, even if the current value is `100`.

**Trap**

`final` alone is not enough. The variable must be initialized with a compile-time constant expression.

---

### Question 3.4 — The octal literal trap

**Question**

What does this print?

```java
int a = 10;
int b = 010;

System.out.println(a);
System.out.println(b);
```

**Expected answer**

It prints:

```text
10
8
```

A leading zero creates an octal integer literal.

**Trap**

This can produce subtle bugs in old code, generated code, date-like constants, or interview puzzles.

---

### Question 3.5 — `Math.abs(Integer.MIN_VALUE)`

**Question**

What does this print?

```java
int x = Integer.MIN_VALUE;
System.out.println(Math.abs(x));
System.out.println(Math.abs(x) < 0);
```

**Expected answer**

It prints:

```text
-2147483648
true
```

`Integer.MIN_VALUE` has no positive counterpart in signed 32-bit two’s-complement `int`.

**Trap**

Code such as `Math.abs(hash) % buckets` can still be negative.

Safer alternatives include:

```java
int index = Math.floorMod(hash, buckets);
```

---

### Question 3.6 — Integer division and remainder with negatives

**Question**

What does this print?

```java
System.out.println(-7 / 3);
System.out.println(-7 % 3);
System.out.println(Math.floorDiv(-7, 3));
System.out.println(Math.floorMod(-7, 3));
```

**Expected answer**

Integer division `/` truncates toward zero:

```text
-2
-1
-3
2
```

**Trap**

`%` is remainder, not mathematical modulo. For non-negative modulo behavior, use `Math.floorMod`.

---

### Question 3.7 — Integer division by zero vs floating-point division by zero

**Question**

What happens here?

```java
System.out.println(1 / 0);
```

And here?

```java
System.out.println(1.0 / 0.0);
```

**Expected answer**

Integer division by zero throws `ArithmeticException`.

Floating-point division by zero follows IEEE 754 behavior and produces infinity:

```text
Infinity
```

**Trap**

The same-looking operation has different behavior depending on operand types.

---

## 4. `char`: The Most Misunderstood Primitive

### Question 4.1 — Can an emoji fit in one `char`?

**Question**

What does this print?

```java
String s = "😀";

System.out.println(s.length());
System.out.println(s.codePointCount(0, s.length()));
```

**Expected answer**

Commonly:

```text
2
1
```

The emoji is one Unicode code point but two UTF-16 code units, represented by a surrogate pair.

**Trap**

`char` is a UTF-16 code unit, not a Unicode character in the user-visible sense.

---

### Question 4.2 — Why does `'A' + 1` produce an `int`?

**Question**

What does this print?

```java
char c = 'A';
System.out.println(c + 1);
System.out.println((char) (c + 1));
```

**Expected answer**

It prints:

```text
66
B
```

`char` is promoted to `int` during arithmetic.

**Trap**

A `char` can be used in numeric expressions, but the result is usually `int`.

---

### Question 4.3 — What happens when casting `-1` to `char`?

**Question**

What does this print?

```java
char c = (char) -1;
System.out.println((int) c);
```

**Expected answer**

It prints:

```text
65535
```

The narrowing conversion keeps the low 16 bits.

**Trap**

`char` is unsigned. Casting negative integers to `char` can produce large positive numeric values.

---

### Question 4.4 — Unicode escapes before lexical analysis

**Question**

Why can this be dangerous?

```java
// \u000A System.out.println("This can become real code");
```

**Expected answer**

Unicode escapes are processed before lexical analysis. `\u000A` is a newline character, so it can unexpectedly terminate a comment before the compiler tokenizes the source.

**Trap**

Unicode escapes are not ordinary runtime string escapes. They are source-level preprocessing escapes.

---

### Question 4.5 — How should you iterate over Unicode code points?

**Question**

What is wrong with this code?

```java
String text = "A😀B";

for (int i = 0; i < text.length(); i++) {
    char c = text.charAt(i);
    System.out.println(c);
}
```

**Expected answer**

It iterates over UTF-16 code units, not Unicode code points. It will split surrogate pairs.

Better:

```java
text.codePoints().forEach(cp -> {
    System.out.println(Character.toChars(cp));
});
```

**Trap**

Even code points are not always user-visible characters. Grapheme clusters can be more complex.

---

### Question 4.6 — Is `Character` the same as `char`?

**Question**

What is the difference between `char` and `Character` in this example?

```java
Character c = null;
// char x = c;
```

**Expected answer**

`Character` is a wrapper reference type. It can be `null`.

Assigning it to `char` requires unboxing. If `c` is `null`, unboxing throws `NullPointerException`.

**Trap**

Wrapper types add nullability and identity concerns that primitive types do not have.

---

## 5. Floating-Point Types: `float` and `double`

### Question 5.1 — Why does `0.1 + 0.2 != 0.3`?

**Question**

What does this print?

```java
System.out.println(0.1 + 0.2 == 0.3);
System.out.println(0.1 + 0.2);
```

**Expected answer**

Usually:

```text
false
0.30000000000000004
```

Decimal fractions such as `0.1` and `0.2` cannot be represented exactly in binary floating-point.

**Trap**

This is not a Java-specific bug. It is normal IEEE 754 floating-point behavior.

---

### Question 5.2 — Why does `float f = 1.0;` fail?

**Question**

Does this compile?

```java
float f = 1.0;
```

**Expected answer**

No. `1.0` is a `double` literal by default.

Correct:

```java
float f1 = 1.0f;
float f2 = (float) 1.0;
```

**Trap**

Floating-point literals default to `double`; integer literals default to `int`.

---

### Question 5.3 — `NaN` equality

**Question**

What does this print?

```java
double x = Double.NaN;

System.out.println(x == x);
System.out.println(Double.valueOf(x).equals(Double.NaN));
```

**Expected answer**

It prints:

```text
false
true
```

Primitive `NaN` is not equal to anything, including itself.

`Double.equals()` treats two NaN values as equal for wrapper equality semantics.

**Trap**

`==`, `equals`, and `compare` are not interchangeable for floating-point wrappers.

---

### Question 5.4 — Positive zero and negative zero

**Question**

What does this print?

```java
double a = 0.0;
double b = -0.0;

System.out.println(a == b);
System.out.println(Double.valueOf(a).equals(Double.valueOf(b)));
System.out.println(1.0 / a);
System.out.println(1.0 / b);
```

**Expected answer**

Primitive comparison says `0.0 == -0.0` is `true`.

`Double.equals()` distinguishes them.

Division reveals the sign:

```text
true
false
Infinity
-Infinity
```

**Trap**

Floating-point zero has a sign bit.

---

### Question 5.5 — Floating-point division by zero

**Question**

What happens here?

```java
System.out.println(1.0 / 0.0);
System.out.println(0.0 / 0.0);
```

**Expected answer**

It prints:

```text
Infinity
NaN
```

Floating-point division by zero does not throw `ArithmeticException`.

**Trap**

Do not generalize integer arithmetic rules to floating-point arithmetic.

---

### Question 5.6 — `BigDecimal` constructor trap

**Question**

What is the difference?

```java
System.out.println(new BigDecimal(0.1));
System.out.println(BigDecimal.valueOf(0.1));
System.out.println(new BigDecimal("0.1"));
```

**Expected answer**

`new BigDecimal(0.1)` captures the exact binary floating-point value of `0.1`, not the decimal value `0.1`.

`BigDecimal.valueOf(0.1)` uses the canonical string representation of the double.

`new BigDecimal("0.1")` constructs exactly the decimal value.

**Trap**

For money, prefer string input, integer minor units, or carefully controlled `BigDecimal` construction.

---

## 6. `boolean`: Simple Type, Subtle Semantics

### Question 6.1 — Is `boolean` numeric?

**Question**

Does this compile?

```java
boolean b = true;
int x = b;
```

**Expected answer**

No. Java has no implicit conversion between `boolean` and numeric types.

**Trap**

Unlike C, Java does not treat `0` as false and non-zero as true.

---

### Question 6.2 — Assignment inside `if`

**Question**

Does this compile?

```java
boolean ready = false;

if (ready = true) {
    System.out.println("ready");
}
```

**Expected answer**

Yes, it compiles and prints `ready`.

The assignment expression has type `boolean`, so it is legal in an `if`.

**Trap**

This is legal but almost always bad style. For constants, prefer:

```java
if (ready) { ... }
```

---

### Question 6.3 — Null unboxing with `Boolean`

**Question**

What happens?

```java
Boolean flag = null;

if (flag) {
    System.out.println("yes");
}
```

**Expected answer**

It throws `NullPointerException` because `flag` must be unboxed to `boolean`.

**Trap**

A `Boolean` can have three practical states: `TRUE`, `FALSE`, and `null`.

---

### Question 6.4 — Short-circuit vs non-short-circuit operators

**Question**

What does this print?

```java
static boolean left() {
    System.out.println("left");
    return false;
}

static boolean right() {
    System.out.println("right");
    return true;
}

public static void main(String[] args) {
    System.out.println(left() && right());
    System.out.println(left() & right());
}
```

**Expected answer**

For `&&`, `right()` is not called after `left()` returns false.

For `&`, both sides are evaluated.

**Trap**

`&` and `|` are valid boolean operators, not only bitwise integer operators.

---

### Question 6.5 — `Boolean.TRUE == Boolean.valueOf(true)`

**Question**

What does this print?

```java
System.out.println(Boolean.TRUE == Boolean.valueOf(true));
System.out.println(new Boolean(true) == Boolean.TRUE);
```

**Expected answer**

The first prints `true`.

The second prints `false` if the constructor is used, because it creates a distinct object. The constructor is deprecated, and `Boolean.valueOf` should be preferred.

**Trap**

Wrapper caches can make `==` accidentally appear to work.

---

### Question 6.6 — Boolean arrays and memory

**Question**

Can you rely on `boolean[]` using one bit per element?

**Expected answer**

No. Java does not specify such storage layout.

Use `BitSet` if you explicitly need compact bit-level storage.

**Trap**

Semantics and memory layout are separate concerns.

---

## 7. Type Conversion Rules

### Question 7.1 — Why does `Long x = 1;` fail?

**Question**

Does this compile?

```java
Long x = 1;
```

**Expected answer**

No.

Java does not perform widening primitive conversion from `int` to `long` and then boxing to `Long` in this assignment.

Correct:

```java
Long a = 1L;
Long b = Long.valueOf(1);
long c = 1;
```

**Trap**

Widening primitive conversion plus boxing is not generally combined.

---

### Question 7.2 — Boxing followed by widening reference conversion

**Question**

Does this compile?

```java
Object o = 1;
Number n = 1;
```

**Expected answer**

Yes.

The `int` literal is boxed to `Integer`, then widened by reference conversion to `Object` or `Number`.

**Trap**

Boxing then widening reference is allowed. Widening primitive then boxing is the one people often expect incorrectly.

---

### Question 7.3 — Narrowing from floating point to integer

**Question**

What does this print?

```java
System.out.println((int) Double.NaN);
System.out.println((int) 1e100);
System.out.println((int) -1e100);
```

**Expected answer**

It prints:

```text
0
2147483647
-2147483648
```

Narrowing floating-point conversions have specified behavior for NaN and out-of-range values.

**Trap**

This is not undefined behavior.

---

### Question 7.4 — `char` conversions

**Question**

Which lines compile?

```java
char c1 = 65;
char c2 = -1;
char c3 = (char) -1;
int x = c1;
```

**Expected answer**

`c1` compiles because `65` is a constant expression in range.

`c2` does not compile because `-1` is outside `char` range.

`c3` compiles due to explicit cast.

`int x = c1` compiles due to widening primitive conversion.

**Trap**

`char` is unsigned, but it still participates in numeric conversion rules.

---

### Question 7.5 — Casts do not change the object

**Question**

What happens?

```java
Object o = "hello";
Integer i = (Integer) o;
```

**Expected answer**

It compiles but throws `ClassCastException` at runtime.

A cast changes the static type view; it does not transform the object.

**Trap**

Primitive casts convert values. Reference casts check object compatibility.

---

### Question 7.6 — Method invocation conversions

**Question**

Which overload is called?

```java
static void f(long x) {
    System.out.println("long");
}

static void f(Integer x) {
    System.out.println("Integer");
}

public static void main(String[] args) {
    f(1);
}
```

**Expected answer**

`f(long)` is called.

Widening primitive conversion beats boxing.

**Trap**

Overload resolution has a priority order. Do not reason only from “closest-looking type”.

---

## 8. Numeric Promotion and Expressions

### Question 8.1 — Unary numeric promotion

**Question**

Does this compile?

```java
byte b = 1;
byte c = +b;
```

**Expected answer**

No.

Unary `+` applies unary numeric promotion. `+b` has type `int`.

Correct:

```java
byte c = (byte) +b;
```

**Trap**

Even unary operators can promote `byte`, `short`, and `char` to `int`.

---

### Question 8.2 — Binary numeric promotion

**Question**

What is the type of each expression?

```java
byte b = 1;
short s = 2;
char c = 3;

var x = b + s;
var y = c + b;
var z = 1L + b;
var w = 1.0f + 2L;
```

**Expected answer**

- `x` is `int`;
- `y` is `int`;
- `z` is `long`;
- `w` is `float`.

**Trap**

`byte`, `short`, and `char` usually disappear into `int` in arithmetic.

---

### Question 8.3 — Why does `b++` compile?

**Question**

Why does this compile?

```java
byte b = 127;
b++;
System.out.println(b);
```

**Expected answer**

Post-increment includes an implicit narrowing conversion back to the variable type.

It prints:

```text
-128
```

**Trap**

`++` can overflow silently on small integral types.

---

### Question 8.4 — Constant expression overflow

**Question**

Does this compile?

```java
int x = 2147483647 + 1;
System.out.println(x);
```

**Expected answer**

Yes. It is a compile-time constant expression of type `int`, and integer overflow is defined.

It prints:

```text
-2147483648
```

**Trap**

Compile-time constant evaluation follows Java integer arithmetic rules.

---

### Question 8.5 — String concatenation order

**Question**

What does this print?

```java
System.out.println("a" + 1 + 2);
System.out.println(1 + 2 + "a");
System.out.println('a' + 1 + "x");
System.out.println("x" + 'a' + 1);
```

**Expected answer**

```text
a12
3a
98x
xa1
```

**Trap**

Evaluation is left-to-right. Once a `String` operand is involved, concatenation happens.

Before that, numeric promotion may happen.

---

### Question 8.6 — Conditional operator and numeric promotion

**Question**

What is the result?

```java
byte b = 1;
int i = 2;

var x = true ? b : i;
System.out.println(((Object) x).getClass());
```

**Expected answer**

`x` is an `int` value boxed to `Integer` for the `Object` cast.

**Trap**

The conditional operator has its own type rules, including numeric promotion and special cases for constants.

---

## 9. Wrapper Classes

### Question 9.1 — Integer cache

**Question**

What does this print?

```java
Integer a = 127;
Integer b = 127;
Integer c = 128;
Integer d = 128;

System.out.println(a == b);
System.out.println(c == d);
```

**Expected answer**

Usually:

```text
true
false
```

`Integer.valueOf` caches at least values from `-128` to `127`.

**Trap**

Do not rely on `==` for wrapper value equality. Use `.equals()` or compare primitives.

---

### Question 9.2 — Wrapper equality across types

**Question**

What does this print?

```java
Integer i = 1;
Long l = 1L;

System.out.println(i.equals(l));
System.out.println(i == 1);
System.out.println(l == 1);
```

**Expected answer**

`i.equals(l)` is `false` because the wrapper types differ.

`i == 1` unboxes `i` and compares primitive `int`.

`l == 1` unboxes `l`, widens `1`, and compares primitive numeric values, so it is `true`.

**Trap**

`equals()` is not numeric comparison across wrapper classes.

---

### Question 9.3 — Null unboxing in arithmetic

**Question**

What happens?

```java
Integer x = null;
int y = x + 1;
```

**Expected answer**

It throws `NullPointerException`.

`x` must be unboxed before arithmetic.

**Trap**

Autoboxing/unboxing makes code concise but can hide runtime null failures.

---

### Question 9.4 — `valueOf` vs constructors

**Question**

Why is this discouraged?

```java
Integer x = new Integer(10);
```

**Expected answer**

Wrapper constructors are deprecated. Prefer:

```java
Integer x = Integer.valueOf(10);
```

`valueOf` may reuse cached instances and makes intent clearer.

**Trap**

Creating wrapper objects manually is rarely needed and can defeat caching.

---

### Question 9.5 — Hidden allocation in collections

**Question**

What hidden operations happen here?

```java
List<Integer> list = new ArrayList<>();

for (int i = 0; i < 1_000_000; i++) {
    list.add(i);
}
```

**Expected answer**

Each `int` is boxed to `Integer` before storage. Depending on values and JIT optimizations, this can cause many allocations.

**Trap**

Generics do not support primitive type arguments, so collections of primitives use wrappers unless a specialized library or primitive array is used.

---

### Question 9.6 — Wrapper comparison and unboxing

**Question**

What happens?

```java
Integer x = null;

if (x == 0) {
    System.out.println("zero");
}
```

**Expected answer**

It throws `NullPointerException` because `x` is unboxed for primitive comparison.

**Trap**

`==` between wrapper and primitive is not reference comparison; it becomes primitive comparison after unboxing.

---

## 10. `String`: Not Primitive, But Interview-Critical

### Question 10.1 — String pool and `new String`

**Question**

What does this print?

```java
String a = "java";
String b = "java";
String c = new String("java");

System.out.println(a == b);
System.out.println(a == c);
System.out.println(a.equals(c));
```

**Expected answer**

```text
true
false
true
```

`a` and `b` refer to the same interned string literal. `c` is a distinct object with the same contents.

**Trap**

`==` checks reference identity. `.equals()` checks string contents.

---

### Question 10.2 — Compile-time vs runtime concatenation

**Question**

What does this print?

```java
String a = "ja" + "va";
String b = "java";

String part = "ja";
String c = part + "va";

System.out.println(a == b);
System.out.println(c == b);
```

**Expected answer**

```text
true
false
```

`"ja" + "va"` is a compile-time constant expression and is interned as `"java"`.

`part + "va"` is runtime concatenation.

**Trap**

The same textual result can have different identity.

---

### Question 10.3 — `final` and constant strings

**Question**

What does this print?

```java
final String part = "ja";
String a = part + "va";
String b = "java";

System.out.println(a == b);
```

**Expected answer**

It prints `true`.

`part` is a compile-time constant variable, so the concatenation is a compile-time constant expression.

**Trap**

`final` matters only when the initializer is itself a compile-time constant expression.

---

### Question 10.4 — Unicode length in strings

**Question**

Why is this dangerous?

```java
if (password.length() >= 8) {
    System.out.println("long enough");
}
```

**Expected answer**

`String.length()` counts UTF-16 code units, not user-visible characters.

For many validation cases this may be acceptable, but it should not be mistaken for grapheme count or display width.

**Trap**

Text length is a domain question, not just a Java API question.

---

### Question 10.5 — `String` immutability

**Question**

What does this print?

```java
String s = "hello";
s.toUpperCase();

System.out.println(s);
```

**Expected answer**

It prints:

```text
hello
```

`String` is immutable. `toUpperCase()` returns a new string.

Correct:

```java
s = s.toUpperCase();
```

**Trap**

Many string methods do not modify the original instance.

---

### Question 10.6 — `String` vs `char[]` for secrets

**Question**

Why do security guidelines often prefer `char[]` over `String` for passwords?

**Expected answer**

A `String` is immutable, internable, and cannot be wiped in place. A `char[]` can be overwritten after use.

```java
Arrays.fill(passwordChars, '\0');
```

**Trap**

This is not absolute protection. Copies may still exist, but `char[]` gives more control than `String`.

---

## 11. Arrays of Basic Types

### Question 11.1 — Array covariance

**Question**

What happens?

```java
Object[] arr = new String[1];
arr[0] = 42;
```

**Expected answer**

It compiles but throws `ArrayStoreException` at runtime.

Arrays are covariant, so `String[]` is an `Object[]`, but the runtime array type is still `String[]`.

**Trap**

Generics are invariant, arrays are covariant and reified.

---

### Question 11.2 — Primitive arrays and wrapper arrays

**Question**

Does this compile?

```java
Integer[] boxed = new Integer[3];
int[] primitive = boxed;
```

**Expected answer**

No.

`int[]` and `Integer[]` are completely different array types. There is no array-level boxing conversion.

**Trap**

Autoboxing applies to individual values, not to whole arrays.

---

### Question 11.3 — Multidimensional arrays

**Question**

What does this print?

```java
int[][] matrix = new int[2][];
matrix[0] = new int[3];
matrix[1] = new int[1];

System.out.println(matrix.length);
System.out.println(matrix[0].length);
System.out.println(matrix[1].length);
```

**Expected answer**

```text
2
3
1
```

Java multidimensional arrays are arrays of arrays. They can be jagged.

**Trap**

`int[][]` is not necessarily a rectangular matrix.

---

### Question 11.4 — Array default values

**Question**

What does this print?

```java
int[] numbers = new int[2];
String[] strings = new String[2];
boolean[] flags = new boolean[2];

System.out.println(numbers[0]);
System.out.println(strings[0]);
System.out.println(flags[0]);
```

**Expected answer**

```text
0
null
false
```

Array elements receive default values.

**Trap**

This differs from local variables, which must be definitely assigned before use.

---

### Question 11.5 — `Arrays.equals` vs `==`

**Question**

What does this print?

```java
int[] a = {1, 2};
int[] b = {1, 2};

System.out.println(a == b);
System.out.println(a.equals(b));
System.out.println(Arrays.equals(a, b));
```

**Expected answer**

```text
false
false
true
```

Arrays do not override `Object.equals()` for content equality.

**Trap**

Use `Arrays.equals` or `Arrays.deepEquals` depending on the level of nesting.

---

### Question 11.6 — Varargs are arrays

**Question**

What is `args` in this method?

```java
static void f(int... args) {
    System.out.println(args.getClass());
}
```

**Expected answer**

`int...` is compiled as `int[]`.

Calling:

```java
f(1, 2, 3);
```

creates or passes an `int[]`.

**Trap**

Varargs can allocate arrays and participate in overload resolution.

---

## 12. Literals and Compile-Time Constants

### Question 12.1 — Literal default types

**Question**

What are the default types?

```java
var a = 1;
var b = 1L;
var c = 1.0;
var d = 1.0f;
```

**Expected answer**

- `a`: `int`;
- `b`: `long`;
- `c`: `double`;
- `d`: `float`.

**Trap**

Literals have types before assignment context is considered.

---

### Question 12.2 — Underscore placement

**Question**

Which literals are valid?

```java
int a = 1_000;
int b = _1000;
int c = 1000_;
double d = 1_.0;
double e = 1.0_0;
```

**Expected answer**

Valid:

```java
int a = 1_000;
double e = 1.0_0;
```

Invalid underscore placements include the beginning/end of a literal and around the decimal point.

**Trap**

Underscores are for readability, but their placement is restricted.

---

### Question 12.3 — `int` literal too large

**Question**

Does this compile?

```java
long x = 2147483648;
```

**Expected answer**

No.

The literal `2147483648` is treated as an `int` literal first and is too large.

Correct:

```java
long x = 2147483648L;
```

**Trap**

Assignment target type does not rescue an invalid integer literal.

---

### Question 12.4 — Constant inlining

**Question**

Why can changing this constant require recompiling client code?

```java
public class Api {
    public static final int TIMEOUT = 10;
}
```

**Expected answer**

If `TIMEOUT` is a compile-time constant, client classes may inline its value into their bytecode.

Changing it to `20` and recompiling only `Api` may not update already-compiled clients.

**Trap**

Public constants are part of your binary compatibility story.

---

### Question 12.5 — Class literals

**Question**

What is the difference?

```java
System.out.println(int.class);
System.out.println(Integer.class);
System.out.println(void.class);
System.out.println(Void.class);
```

**Expected answer**

`int.class` and `void.class` represent primitive and void `Class` objects.

`Integer.class` and `Void.class` represent reference classes.

**Trap**

`void` is not a normal value type, but it still has a class literal.

---

### Question 12.6 — Character and string literals

**Question**

Which lines compile?

```java
char a = 'A';
String b = "A";
char c = "A";
String d = 'A';
```

**Expected answer**

The first two compile.

The last two do not compile without explicit conversion.

Correct conversions:

```java
String d = String.valueOf('A');
char c = "A".charAt(0);
```

**Trap**

A one-character string is still a `String`, not a `char`.

---

## 13. `null`, `void`, and Special Types

### Question 13.1 — Overload resolution with `null`

**Question**

Which method is called?

```java
static void f(Object x) {
    System.out.println("Object");
}

static void f(String x) {
    System.out.println("String");
}

public static void main(String[] args) {
    f(null);
}
```

**Expected answer**

`f(String)` is called because `String` is more specific than `Object`.

**Trap**

`null` can be assigned to both, so overload resolution chooses the most specific applicable method.

---

### Question 13.2 — Ambiguous `null`

**Question**

Does this compile?

```java
static void f(String x) {}
static void f(Integer x) {}

public static void main(String[] args) {
    f(null);
}
```

**Expected answer**

No. The call is ambiguous.

Neither `String` nor `Integer` is more specific than the other.

**Trap**

`null` is often the fastest way to expose ambiguous overload sets.

---

### Question 13.3 — `null` and primitives

**Question**

What happens?

```java
Integer x = null;
int y = x;
```

**Expected answer**

It compiles but throws `NullPointerException` at runtime due to unboxing.

**Trap**

`null` cannot be assigned to a primitive, but it can hide inside a wrapper until unboxing.

---

### Question 13.4 — `Void` in generics

**Question**

Why might someone use `CompletableFuture<Void>`?

**Expected answer**

It represents an asynchronous computation that has completion status but no meaningful result value.

Example:

```java
CompletableFuture<Void> done =
    CompletableFuture.runAsync(() -> System.out.println("work"));
```

**Trap**

`Void` is not the same as `void`. A `Void` value is normally `null`.

---

### Question 13.5 — Can you instantiate `Void`?

**Question**

Can this be written?

```java
Void v = new Void();
```

**Expected answer**

No. `Void` has no public constructor.

In normal code, the only useful value of type `Void` is `null`.

**Trap**

`Void` exists mostly for reflection and generic APIs.

---

### Question 13.6 — The null type has no name

**Question**

Can you declare a variable of the null type?

**Expected answer**

No. The null type is a special compile-time concept and has no source-level name.

```java
var x = null; // does not compile
```

`var` cannot infer a type from `null` alone.

**Trap**

`null` is not an instance of `Object`.

---

## 14. Overload Resolution and Basic Types

### Question 14.1 — Widening beats boxing

**Question**

What does this print?

```java
static void f(long x) {
    System.out.println("long");
}

static void f(Integer x) {
    System.out.println("Integer");
}

public static void main(String[] args) {
    f(10);
}
```

**Expected answer**

It prints:

```text
long
```

Widening primitive conversion beats boxing.

**Trap**

Many candidates incorrectly expect `Integer` because `10` is an `int`.

---

### Question 14.2 — Boxing beats varargs

**Question**

What does this print?

```java
static void f(Integer x) {
    System.out.println("Integer");
}

static void f(int... x) {
    System.out.println("varargs");
}

public static void main(String[] args) {
    f(10);
}
```

**Expected answer**

It prints:

```text
Integer
```

Boxing is preferred over varargs.

**Trap**

Varargs are considered later in overload resolution.

---

### Question 14.3 — Exact match wins

**Question**

What does this print?

```java
static void f(int x) {
    System.out.println("int");
}

static void f(long x) {
    System.out.println("long");
}

static void f(Integer x) {
    System.out.println("Integer");
}

public static void main(String[] args) {
    f(10);
}
```

**Expected answer**

It prints:

```text
int
```

Exact match wins before widening or boxing.

---

### Question 14.4 — `char` overload puzzle

**Question**

What does this print?

```java
static void f(int x) {
    System.out.println("int");
}

static void f(Character x) {
    System.out.println("Character");
}

public static void main(String[] args) {
    f('A');
}
```

**Expected answer**

It prints:

```text
int
```

`char` can be widened to `int`, and widening beats boxing.

**Trap**

A `char` literal is not automatically boxed if a widening primitive overload is available.

---

### Question 14.5 — `null` and overloads

**Question**

What does this print?

```java
static void f(Object x) {
    System.out.println("Object");
}

static void f(Integer x) {
    System.out.println("Integer");
}

public static void main(String[] args) {
    f(null);
}
```

**Expected answer**

It prints:

```text
Integer
```

`Integer` is more specific than `Object`.

**Trap**

Changing one overload can silently change which method `null` selects.

---

### Question 14.6 — Varargs ambiguity

**Question**

Does this compile?

```java
static void f(int... x) {}
static void f(Integer... x) {}

public static void main(String[] args) {
    f();
}
```

**Expected answer**

No. The empty varargs call is ambiguous.

Both overloads are applicable and neither is more specific.

**Trap**

Varargs overloads can produce surprising ambiguity, especially with empty argument lists.

---

## 15. Equality, Identity, and Comparison

### Question 15.1 — Primitive vs wrapper comparison

**Question**

What happens?

```java
Integer x = 1000;
int y = 1000;

System.out.println(x == y);
```

**Expected answer**

It prints `true`.

`x` is unboxed to `int`; primitive comparison is performed.

**Trap**

`==` between wrapper and primitive is not identity comparison.

---

### Question 15.2 — Wrapper identity

**Question**

What does this print?

```java
Integer a = 1000;
Integer b = 1000;

System.out.println(a == b);
System.out.println(a.equals(b));
```

**Expected answer**

Usually:

```text
false
true
```

The two wrappers have equal values but are not necessarily the same object.

**Trap**

Small cached values make this bug intermittent-looking.

---

### Question 15.3 — `NaN` in collections

**Question**

What does this print?

```java
Double a = Double.NaN;
Double b = Double.NaN;

System.out.println(a == b);
System.out.println(a.equals(b));
System.out.println(new HashSet<>(List.of(a)).contains(b));
```

**Expected answer**

The `==` comparison depends on reference identity. With autoboxing of `Double.NaN`, do not rely on identity.

`a.equals(b)` is `true`.

The set contains check is `true`.

**Trap**

Primitive NaN equality and wrapper equality differ.

---

### Question 15.4 — `0.0` and `-0.0`

**Question**

What does this print?

```java
System.out.println(0.0 == -0.0);
System.out.println(Double.valueOf(0.0).equals(Double.valueOf(-0.0)));
System.out.println(Double.compare(0.0, -0.0));
```

**Expected answer**

```text
true
false
1
```

`Double.compare` treats positive zero as greater than negative zero.

**Trap**

Primitive equality, wrapper equality, and ordering comparison are different APIs.

---

### Question 15.5 — `BigDecimal.equals` vs `compareTo`

**Question**

What does this print?

```java
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("1.00");

System.out.println(a.equals(b));
System.out.println(a.compareTo(b) == 0);
```

**Expected answer**

```text
false
true
```

`equals()` considers scale. `compareTo()` compares numeric value.

**Trap**

This matters when using `BigDecimal` as keys in maps or elements in sets.

---

### Question 15.6 — Array equality

**Question**

What does this print?

```java
int[] a = {1, 2};
int[] b = {1, 2};

System.out.println(a.equals(b));
System.out.println(Objects.equals(a, b));
System.out.println(Arrays.equals(a, b));
```

**Expected answer**

```text
false
false
true
```

Arrays use identity equality unless helper methods are used.

**Trap**

`Objects.equals` calls `equals`; it does not magically do array content comparison.

---

## 16. Basic Types and Generics

### Question 16.1 — Why is `List<int>` illegal?

**Question**

Why does Java not allow this?

```java
List<int> values = new ArrayList<>();
```

**Expected answer**

Java generics work with reference types, not primitive types.

Use:

```java
List<Integer> values = new ArrayList<>();
```

or primitive-specialized alternatives such as arrays, `IntStream`, or third-party primitive collections.

**Trap**

`List<Integer>` is not the same as a primitive list. It involves boxing.

---

### Question 16.2 — Autoboxing in generic APIs

**Question**

What hidden operation occurs?

```java
List<Integer> values = new ArrayList<>();
values.add(42);
int x = values.get(0);
```

**Expected answer**

`42` is boxed to `Integer` for storage.

`values.get(0)` returns `Integer`, which is unboxed to `int`.

**Trap**

Generic APIs can hide both allocation and null-unboxing risks.

---

### Question 16.3 — Primitive-specialized streams

**Question**

Why might this be better?

```java
int sum = IntStream.range(0, 1_000_000).sum();
```

Compared with:

```java
int sum = Stream.iterate(0, i -> i + 1)
    .limit(1_000_000)
    .reduce(0, Integer::sum);
```

**Expected answer**

`IntStream` avoids boxing each element and uses primitive-specialized operations.

**Trap**

Streams are not automatically primitive-efficient. Choose `IntStream`, `LongStream`, or `DoubleStream` when appropriate.

---

### Question 16.4 — Generic arrays

**Question**

Why is this problematic?

```java
T[] array = new T[10];
```

**Expected answer**

It does not compile because of type erasure. At runtime, Java needs a reified component type for array creation.

**Trap**

Arrays are reified; generics are erased. This mismatch causes many Java generic-array restrictions.

---

### Question 16.5 — Varargs and heap pollution

**Question**

Why can this be dangerous?

```java
@SafeVarargs
static <T> void f(List<T>... lists) {
    Object[] array = lists;
    array[0] = List.of(42);
}
```

**Expected answer**

Generic varargs are implemented as arrays. Arrays are reified and covariant, while generic type arguments are erased. This can create heap pollution.

**Trap**

`@SafeVarargs` is a promise by the author, not magic protection.

---

### Question 16.6 — Future value/primitive classes

**Question**

Why do interviewers sometimes mention Project Valhalla when discussing primitives and generics?

**Expected answer**

Because Java has long-standing friction between primitives and generics. Valhalla aims to improve value-oriented programming and specialization, reducing the gap between primitive efficiency and object abstraction.

**Trap**

Do not describe future features as if they are already universally available in normal Java code.

---

## 17. Reflection and Class Objects for Basic Types

### Question 17.1 — Primitive `Class` objects

**Question**

What does this print?

```java
System.out.println(int.class.isPrimitive());
System.out.println(Integer.class.isPrimitive());
System.out.println(void.class.isPrimitive());
```

**Expected answer**

```text
true
false
true
```

`void.class` is also considered primitive by `Class.isPrimitive()`.

**Trap**

Primitive `Class` objects exist even though primitive values are not objects.

---

### Question 17.2 — Reflective method lookup and primitive parameters

**Question**

Given:

```java
class Service {
    public void f(int x) {}
}
```

Which lookup is correct?

```java
Service.class.getMethod("f", int.class);
Service.class.getMethod("f", Integer.class);
```

**Expected answer**

Only the lookup with `int.class` finds the method.

Reflection lookup requires exact parameter types.

**Trap**

Autoboxing does not help `getMethod` find a method with a different declared parameter type.

---

### Question 17.3 — `Method.invoke` and arguments

**Question**

What should you be careful about when invoking a method reflectively?

```java
Method m = Service.class.getMethod("f", int.class);
m.invoke(new Service(), Integer.valueOf(10));
```

**Expected answer**

Reflection invocation can perform method-invocation-like conversions for arguments, including unwrapping wrappers for primitive parameters.

However, lookup and invocation are separate phases. Lookup still requires the exact declared parameter types.

**Trap**

Many candidates mix up reflective lookup with reflective invocation.

---

### Question 17.4 — Array class objects

**Question**

What does this print?

```java
System.out.println(int[].class.isArray());
System.out.println(int[].class.getComponentType());
System.out.println(String[][].class.getComponentType());
```

**Expected answer**

`int[].class` is an array class.

Its component type is `int`.

The component type of `String[][]` is `String[]`.

**Trap**

Multidimensional arrays are arrays whose component type may itself be an array.

---

### Question 17.5 — `Integer.TYPE`

**Question**

Why does this exist?

```java
System.out.println(Integer.TYPE == int.class);
```

**Expected answer**

Wrapper classes expose their corresponding primitive type through `TYPE`.

The expression prints `true`.

**Trap**

`Integer.TYPE` is not the same as `Integer.class`.

---

### Question 17.6 — Reflection and `void`

**Question**

How do you check whether a reflected method returns `void`?

```java
Method m = SomeClass.class.getMethod("run");
```

**Expected answer**

Use:

```java
m.getReturnType() == void.class
```

or:

```java
m.getReturnType() == Void.TYPE
```

**Trap**

`Void.class` is the wrapper class, not the primitive `void` return type.

---

## 18. Memory, Performance, and JVM Reality

### Question 18.1 — Primitive array vs wrapper array

**Question**

Why is this usually much more memory-efficient?

```java
int[] a = new int[1_000_000];
```

Compared with:

```java
Integer[] b = new Integer[1_000_000];
```

**Expected answer**

`int[]` stores primitive values directly.

`Integer[]` stores references to `Integer` objects. Each boxed value may be a separate object with object header overhead and pointer indirection.

**Trap**

`Integer[]` is not a compact array of integer values.

---

### Question 18.2 — Object headers and wrappers

**Question**

Why is `Integer` heavier than `int`?

**Expected answer**

`Integer` is an object. It has object identity, a header, alignment requirements, and reference access overhead.

`int` is a primitive value.

**Trap**

Wrapper classes are convenient but not free.

---

### Question 18.3 — Can JIT eliminate boxing?

**Question**

Can this allocation always be assumed?

```java
Integer x = 10;
int y = x + 1;
```

**Expected answer**

At the language level, boxing and unboxing occur.

At runtime, the JIT may eliminate allocations using escape analysis and scalar replacement.

**Trap**

Do not claim either “it always allocates” or “it never allocates” without measurement and context.

---

### Question 18.4 — Why microbenchmarks lie

**Question**

What is wrong with this benchmark?

```java
long start = System.nanoTime();

for (int i = 0; i < 1_000_000; i++) {
    Integer x = i;
}

System.out.println(System.nanoTime() - start);
```

**Expected answer**

The JIT may optimize away unused work. The benchmark lacks warmup, proper measurement iterations, dead-code prevention, and JVM isolation.

**Trap**

Use JMH for serious Java microbenchmarking.

---

### Question 18.5 — Locality and arrays

**Question**

Why can `int[]` be faster than `List<Integer>` in tight loops?

**Expected answer**

`int[]` provides contiguous primitive storage and better cache locality.

`List<Integer>` stores references to boxed objects, causing pointer chasing and possible cache misses.

**Trap**

High-level APIs can hide memory-layout costs.

---

### Question 18.6 — Specification vs implementation

**Question**

Is it correct to answer an interview question by saying “HotSpot stores it this way”?

**Expected answer**

Only if the question is about HotSpot.

For language questions, answer from the Java language specification first. Then optionally discuss common JVM implementation behavior.

**Trap**

Mixing specification guarantees with implementation details leads to brittle reasoning.

---

## 19. Serialization, Parsing, and Formatting

### Question 19.1 — `parseInt` vs `valueOf`

**Question**

What is the difference?

```java
int a = Integer.parseInt("42");
Integer b = Integer.valueOf("42");
```

**Expected answer**

`parseInt` returns primitive `int`.

`valueOf` returns `Integer`.

**Trap**

`valueOf` may use cached wrapper instances for some values. `parseInt` avoids boxing.

---

### Question 19.2 — Radix parsing

**Question**

What does this print?

```java
System.out.println(Integer.parseInt("10"));
System.out.println(Integer.parseInt("10", 2));
System.out.println(Integer.parseInt("10", 16));
```

**Expected answer**

```text
10
2
16
```

**Trap**

The same string has different numeric meaning depending on radix.

---

### Question 19.3 — Parsing overflow

**Question**

What happens?

```java
System.out.println(Integer.parseInt("2147483648"));
```

**Expected answer**

It throws `NumberFormatException` because the value is outside `int` range.

**Trap**

Parsing validates range. It does not silently wrap like arithmetic overflow.

---

### Question 19.4 — Unsigned helpers

**Question**

What does this print?

```java
int x = Integer.parseUnsignedInt("4294967295");
System.out.println(x);
System.out.println(Integer.toUnsignedString(x));
```

**Expected answer**

The stored `int` value is `-1`, but its unsigned string representation is `4294967295`.

**Trap**

Java has signed primitive integers, but provides helper methods for unsigned interpretation.

---

### Question 19.5 — Locale-sensitive formatting

**Question**

Why can this be dangerous?

```java
System.out.printf("%f%n", 1234.56);
```

**Expected answer**

Some formatting APIs are locale-sensitive. Decimal separators and grouping can differ by locale.

For stable machine-readable output, specify a locale:

```java
System.out.printf(Locale.ROOT, "%f%n", 1234.56);
```

**Trap**

Human formatting and machine serialization are different tasks.

---

### Question 19.6 — `Double.toString` round-trip

**Question**

Why is `Double.toString` often safer than manually choosing a few decimal places?

**Expected answer**

Modern Java produces a decimal representation sufficient to recover the same `double` value when parsed back.

Manual rounding may lose information.

**Trap**

Display formatting and exact serialization have different requirements.

---

## 20. Classic Interview Puzzles

### Question 20.1 — `byte` assignment puzzle

**Question**

Explain all three lines:

```java
byte a = 1;
byte b = 1 + 2;
// byte c = a + 2;
```

**Expected answer**

`a` compiles because `1` fits into `byte`.

`b` compiles because `1 + 2` is a compile-time constant expression that fits into `byte`.

`c` does not compile because `a + 2` is an `int` expression evaluated at runtime.

**Trap**

Constant expression rules matter.

---

### Question 20.2 — Integer cache puzzle

**Question**

Explain the output:

```java
Integer a = 127;
Integer b = 127;
Integer c = 128;
Integer d = 128;

System.out.println(a == b);
System.out.println(c == d);
```

**Expected answer**

At least `-128` to `127` are cached for `Integer.valueOf`.

`a == b` is usually `true`; `c == d` is usually `false`.

**Trap**

The correct advice is still: never use `==` for wrapper value equality.

---

### Question 20.3 — `i = i++`

**Question**

What does this print?

```java
int i = 1;
i = i++;
System.out.println(i);
```

**Expected answer**

It prints `1`.

Post-increment produces the old value, then increments the variable, then assignment writes the old value back.

**Trap**

Avoid clever side-effect expressions.

---

### Question 20.4 — Overload puzzle

**Question**

What does this print?

```java
static void f(long x) { System.out.println("long"); }
static void f(Object x) { System.out.println("Object"); }
static void f(int... x) { System.out.println("varargs"); }

public static void main(String[] args) {
    f(1);
}
```

**Expected answer**

It prints:

```text
long
```

Widening primitive conversion beats boxing and varargs.

---

### Question 20.5 — Emoji length puzzle

**Question**

What does this print?

```java
String s = "😈";
System.out.println(s.length());
System.out.println(s.codePointCount(0, s.length()));
```

**Expected answer**

Usually:

```text
2
1
```

The emoji is represented as a surrogate pair in UTF-16.

**Trap**

`length()` is not user-visible character count.

---

### Question 20.6 — `HashSet` with `BigDecimal`

**Question**

What does this print?

```java
Set<BigDecimal> set = new HashSet<>();
set.add(new BigDecimal("1.0"));

System.out.println(set.contains(new BigDecimal("1.00")));
```

**Expected answer**

It prints `false`.

`HashSet` uses `equals` and `hashCode`, and `BigDecimal.equals` considers scale.

**Trap**

Numeric equality and object equality may differ.

---

### Question 20.7 — `switch` and wrappers

**Question**

What happens?

```java
Integer x = null;

switch (x) {
    case 1 -> System.out.println("one");
    default -> System.out.println("other");
}
```

**Expected answer**

It throws `NullPointerException` because `x` must be unboxed.

**Trap**

Language syntax can hide unboxing.

---

### Question 20.8 — `char` puzzle

**Question**

What does this print?

```java
char c = '0';
System.out.println(c + 1);
System.out.println((char) (c + 1));
```

**Expected answer**

```text
49
1
```

The character `'0'` has numeric code unit value 48.

**Trap**

Characters and numeric digits are not the same thing.

---

## 21. Summary Tables

### Question 21.1 — Fill the default values table

**Question**

What are the default field values?

| Type | Default |
|---|---|
| `byte`, `short`, `int`, `long` | ? |
| `float`, `double` | ? |
| `char` | ? |
| `boolean` | ? |
| reference types | ? |

**Expected answer**

| Type | Default |
|---|---|
| `byte`, `short`, `int`, `long` | zero of that type |
| `float`, `double` | positive zero |
| `char` | `'\u0000'` |
| `boolean` | `false` |
| reference types | `null` |

**Trap**

These are field and array-element defaults, not local-variable defaults.

---

### Question 21.2 — Rank overload conversions

**Question**

Rank these from more preferred to less preferred in overload resolution:

- boxing;
- exact match;
- varargs;
- primitive widening.

**Expected answer**

Simplified practical order:

1. exact match;
2. primitive widening;
3. boxing;
4. varargs.

**Trap**

There are additional details, but this ranking solves many interview puzzles.

---

### Question 21.3 — Numeric promotion table

**Question**

What is the result type?

| Expression | Result type |
|---|---|
| `byte + byte` | ? |
| `short + char` | ? |
| `int + long` | ? |
| `long + float` | ? |
| `float + double` | ? |

**Expected answer**

| Expression | Result type |
|---|---|
| `byte + byte` | `int` |
| `short + char` | `int` |
| `int + long` | `long` |
| `long + float` | `float` |
| `float + double` | `double` |

**Trap**

`float` can represent a wider range than `long`, but with less integer precision.

---

### Question 21.4 — Wrapper caches

**Question**

Which wrappers are commonly cached by `valueOf`?

**Expected answer**

The most important guaranteed practical cases:

- `Boolean`: `TRUE` and `FALSE`;
- `Byte`: all values;
- `Short`, `Integer`, `Long`: at least `-128` to `127`;
- `Character`: at least `'\u0000'` to `'\u007f'`.

**Trap**

Cache details beyond guaranteed ranges should not be used for program correctness.

---

### Question 21.5 — Primitive ranges

**Question**

Which primitive integral type has no negative values?

**Expected answer**

`char`.

It ranges from `0` to `65535`.

**Trap**

`char` is still not a proper Unicode character abstraction.

---

### Question 21.6 — Compile-time vs runtime checklist

**Question**

When deciding whether a narrowing assignment compiles, what should you check?

**Expected answer**

Check:

1. Is the expression a compile-time constant expression?
2. Is the target type `byte`, `short`, `char`, or maybe another narrower primitive?
3. Does the constant value fit into the target type?
4. Is an explicit cast present?

**Trap**

The compiler does not use arbitrary runtime reasoning such as “this variable currently contains 1”.

---

## 22. Practical Checklist for Interviews

### Question 22.1 — How do you approach a Java type puzzle?

**Question**

Given an unfamiliar expression, what is your method?

**Expected answer**

A reliable method:

1. identify the declared types;
2. identify literal types;
3. apply unary/binary numeric promotion;
4. check boxing/unboxing;
5. check widening/narrowing;
6. check overload priority;
7. check whether comparison is primitive equality, reference identity, or `.equals()`;
8. check runtime exceptions such as NPE, `ClassCastException`, or `ArithmeticException`.

**Trap**

Guessing from intuition is how most interview puzzles defeat candidates.

---

### Question 22.2 — How do you answer “does it compile?”

**Question**

What categories of failure should you check first?

**Expected answer**

Check:

- uninitialized local variables;
- invalid narrowing assignment;
- invalid literal range;
- ambiguous overloads;
- primitive vs reference mismatch;
- generic primitive usage such as `List<int>`;
- checked exceptions if present;
- invalid use of `null`.

**Trap**

Many examples are compile-time traps, not runtime traps.

---

### Question 22.3 — How do you answer “what does it print?”

**Question**

What should you write before calculating mentally?

**Expected answer**

Annotate each expression with its type.

Example:

```java
byte b = 1;
System.out.println(b + 1); // b promoted to int, result int
```

**Trap**

Wrong answers often come from assuming the result type is the same as the variable type.

---

### Question 22.4 — How do you avoid wrapper bugs in production code?

**Question**

What practices reduce wrapper-related bugs?

**Expected answer**

- Prefer primitives when null is not meaningful.
- Use `Objects.equals(a, b)` for nullable references.
- Avoid `==` for wrapper value comparison.
- Watch for unboxing in comparisons, arithmetic, `switch`, and conditions.
- Use primitive collections or arrays in performance-critical code.

**Trap**

Autoboxing makes code look primitive-like while keeping reference-type risks.

---

### Question 22.5 — How do you discuss JVM implementation without overclaiming?

**Question**

How should you answer performance questions about primitive and wrapper types?

**Expected answer**

Separate three layers:

1. Java language semantics;
2. bytecode/JVM model;
3. specific runtime implementation and JIT optimization.

Then say what must happen semantically and what the JVM may optimize.

**Trap**

Avoid absolute statements like “this is always on the stack” or “this always allocates”.

---

### Question 22.6 — How do you handle Unicode questions?

**Question**

What should you immediately clarify?

**Expected answer**

Clarify whether the question is about:

- UTF-16 code units;
- Unicode code points;
- grapheme clusters;
- display width;
- normalization;
- locale-sensitive case conversion.

**Trap**

`char`, `String.length()`, and “character count” are not the same concept.

---

### Question 22.7 — How do you answer overload-resolution puzzles?

**Question**

What minimal priority list should you remember?

**Expected answer**

For many basic-type puzzles:

```text
exact match
primitive widening
boxing
varargs
```

Then handle “most specific” and ambiguity rules.

**Trap**

`null` and varargs can make seemingly simple overloads ambiguous.

---

### Question 22.8 — How do you turn a puzzle answer into a senior-level answer?

**Question**

What makes an answer sound senior rather than memorized?

**Expected answer**

A senior-level answer includes:

- the immediate result;
- the relevant language rule;
- the runtime risk if any;
- production guidance;
- when the rule matters in real systems.

Example:

> `Integer a = 128; Integer b = 128; a == b` is not reliable because wrappers are reference objects and `==` compares identity. Some small values are cached, but production code should use `.equals()` or primitives.

**Trap**

Interviewers often care more about reasoning discipline than the puzzle result itself.

---

## Final Practice Recommendation

Use this question bank in three passes:

1. **Compile-time pass** — decide whether each snippet compiles.
2. **Runtime pass** — if it compiles, predict output or exception.
3. **Explanation pass** — explain the exact rule: promotion, conversion, boxing, unboxing, overload resolution, identity, equality, Unicode, or JVM implementation detail.

For serious preparation, paste each snippet into JShell or a small Java file and verify your prediction. Then modify one type at a time:

- replace `int` with `Integer`;
- replace `long` with `Long`;
- replace `char` with `Character`;
- add `null`;
- add an overload;
- make a variable `final`;
- change a literal from `1` to `1L`, `1.0`, or `1.0f`.

That is the fastest way to stop memorizing Java type trivia and start reasoning about Java type behavior reliably.
