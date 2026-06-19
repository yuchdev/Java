# Advanced Java Basic Operations — Interview Question Bank

This question bank mirrors the theoretical reference point-by-point. Each item gives a question and the expected direction of thinking rather than a full textbook answer. It is intended as a base for mock interviews, self-checking, and expanding into coding exercises.


## Reference Baseline

This material is written for modern Java interview preparation. Unless a section explicitly says otherwise, assume Java 21+ syntax, with notes where behavior is older or version-sensitive. The most important authoritative references are:

- Java Language Specification, current edition: https://docs.oracle.com/javase/specs/jls/se26/html/index.html
- Oracle Java documentation on switch expressions/statements: https://docs.oracle.com/en/java/javase/26/language/switch-expressions-statements.html
- JEP 441, Pattern Matching for switch: https://openjdk.org/jeps/441


## How to Use This Question Bank

For each question, ask the candidate to state first whether the code compiles. If it compiles, ask whether it produces a value, mutates state, throws an exception, or changes control flow. The explanation matters more than the final output.

## 1. What Counts as a “Basic Operation” in Java?

**Purpose of this section.** This opening section defines the boundary of the chapter: not APIs or frameworks, but the core language mechanics that turn short snippets into interview traps. The key habit is to classify code before solving it.

### Q1.1. Can `int x = if (flag) 1 else 2;` ever be valid Java? What construct would you use instead?

Expected direction: An expression produces a value; a statement is executed for its effect. Java has expression statements, but not every statement can appear where a value is required. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q1.2. Explain the difference between Declarations vs assignments. Give one snippet where confusing them changes the result.

Expected direction: A declaration introduces a name and type; assignment stores a value into an existing variable. A declaration may contain an initializer, but that does not make declaration and assignment the same operation. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q1.3. Explain the difference between Initialization vs assignment vs reassignment. Give one snippet where confusing them changes the result.

Expected direction: Initialization gives a variable its first usable value; assignment changes a variable after it already exists. Reassignment is forbidden for final variables after they are definitely assigned. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q1.4. Explain the difference between Compile-time behavior vs runtime behavior. Give one snippet where confusing them changes the result.

Expected direction: Some rules are enforced by the compiler before the program can run; others produce runtime values or exceptions. Interview snippets should always be classified first. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q1.5. Explain the difference between Syntax sugar vs actual language rules. Give one snippet where confusing them changes the result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q1.6. Why "simple Java code" often hides complex rules?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q1.7. What is the main trap behind Interview trap categories: compilation, result, exception, overload, control flow, and how would you detect it in code review?

Expected direction: Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible. Expect compile-time reasoning: target type, inferred type, or selected overload.

## 2. Variable Declaration and Initialization

**Purpose of this section.** Initialization questions usually test whether the candidate knows where default values exist, where definite assignment is required, and how initialization order affects observable behavior.

### Q2.1. Explain the difference between Local variables vs fields vs array elements. Give one snippet where confusing them changes the result.

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q2.2. What should a senior Java candidate know about Default values: where they exist and where they do not?

Expected direction: Fields and array elements receive default values automatically. Local variables do not; they must be definitely assigned before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

Use or adapt this snippet during the interview:

```java
class Demo {
    int field;           // default: 0
    int[] arr = new int[1]; // arr[0] default: 0

    void m() {
        int local;
        // System.out.println(local); // compile-time error
    }
}
```

### Q2.3. Why local variables must be definitely assigned?

Expected direction: The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

Use or adapt this snippet during the interview:

```java
int x;
if (Math.random() > 0.5) {
    x = 10;
}
// System.out.println(x); // compile-time error: not definitely assigned
```

### Q2.4. What should a senior Java candidate know about Definite assignment and compiler flow analysis?

Expected direction: The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q2.5. What should a senior Java candidate know about Branches, loops, try/catch, and definite assignment?

Expected direction: The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q2.6. What should a senior Java candidate know about Final local variables and blank finals?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q2.7. What should a senior Java candidate know about final fields and constructor initialization?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q2.8. What should a senior Java candidate know about Initialization order inside a method?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q2.9. What should a senior Java candidate know about Multiple declarations in one statement?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q2.10. What should a senior Java candidate know about Left-to-right evaluation of declarators?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q2.11. What should a senior Java candidate know about Shadowing during initialization?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q2.12. What should a senior Java candidate know about Illegal self-reference examples?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q2.13. Explain the difference between Initialization blocks vs constructors. Give one snippet where confusing them changes the result.

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q2.14. What is the main trap behind Static initialization order traps, and how would you detect it in code review?

Expected direction: Static fields and static blocks run in textual order during class initialization. Reading a field before its initializer has completed can expose default values or surprising results. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q2.15. What should a senior Java candidate know about Circular initialization between static fields?

Expected direction: Static fields and static blocks run in textual order during class initialization. Reading a field before its initializer has completed can expose default values or surprising results. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q2.16. Design a short interview puzzle that tests Interview puzzles with "variable might not have been initialized", and explain the expected result.

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 3. Assignment Operators and Expression Results

**Purpose of this section.** Assignment in Java is not only a statement-like action; it is also an expression with a value. Many traps come from hidden conversions, chained assignment, and side effects on the left-hand side.

### Q3.1. What is printed by `boolean b; System.out.println(b = true);`?

Expected direction: An assignment expression evaluates to the value assigned, after required conversion. This permits chained assignments and assignment inside boolean conditions. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q3.2. What should a senior Java candidate know about Return value of assignment?

Expected direction: An assignment expression evaluates to the value assigned, after required conversion. This permits chained assignments and assignment inside boolean conditions. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q3.3. What should a senior Java candidate know about Chained assignment: a = b = c?

Expected direction: Assignment is right-associative. In `a = b = c`, the rightmost assignment happens first and its value becomes the value assigned further left. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

Use or adapt this snippet during the interview:

```java
int a, b, c;
a = b = c = 5; // parsed as a = (b = (c = 5))
```

### Q3.4. What should a senior Java candidate know about Assignment associativity?

Expected direction: Assignment is right-associative. In `a = b = c`, the rightmost assignment happens first and its value becomes the value assigned further left. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q3.5. What should a senior Java candidate know about Assigning inside if, while, and for?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q3.6. Why if (x = true) compiles?

Expected direction: This compiles when `x` is boolean because `x = true` is a boolean expression. It is usually a bug disguised as a valid condition. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q3.7. Why if (x = 1) does not compile?

Expected direction: This does not compile because Java conditions require boolean expressions; unlike C/C++, numeric values are not truthy or falsy. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q3.8. What should a senior Java candidate know about Compound assignment and hidden casts?

Expected direction: Compound assignment includes an implicit narrowing conversion equivalent to `E1 = (T)((E1) op (E2))`, while evaluating the left side only once. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

Use or adapt this snippet during the interview:

```java
byte b = 1;
b += 1;      // OK: implicit cast after compound assignment
// b = b + 1; // error: b + 1 is int
```

### Q3.9. What should a senior Java candidate know about +=, -=, *=, /=, %= subtleties?

Expected direction: Compound assignment includes an implicit narrowing conversion equivalent to `E1 = (T)((E1) op (E2))`, while evaluating the left side only once. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q3.10. Explain the difference between Assignment evaluation order: left side vs right side. Give one snippet where confusing them changes the result.

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q3.11. What should a senior Java candidate know about Assignment to array elements and side effects?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q3.12. Can assigning to a static field through a null reference work? What about assigning to an instance field?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q3.13. What should a senior Java candidate know about Assignment in lambdas and effectively-final variables?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q3.14. Design a short interview puzzle that tests Interview puzzles with assignment expressions, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 4. Evaluation Order and Side Effects

**Purpose of this section.** Java defines a strict left-to-right evaluation order for operands and arguments. This makes many snippets deterministic, but still difficult to reason about when side effects are densely packed.

### Q4.1. What should a senior Java candidate know about Java’s left-to-right evaluation guarantee?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q4.2. What should a senior Java candidate know about Operand evaluation before operator execution?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q4.3. Given `f(i++, i++, ++i)`, in what order are the arguments evaluated?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

### Q4.4. What should a senior Java candidate know about Array index evaluation order?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

Use or adapt this snippet during the interview:

```java
int[] a = {10, 20};
int i = 0;
a[i] = i = 1; // left array/index evaluated before right side assignment
```

### Q4.5. What should a senior Java candidate know about Field access evaluation order?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

### Q4.6. Explain the difference between Post-increment vs pre-increment. Give one snippet where confusing them changes the result.

Expected direction: Prefix increment updates first and yields the new value; postfix increment yields the old value and then updates. Both have side effects. Require step-by-step evaluation, not a memorized final number.

### Q4.7. i = i++ and why it is not undefined behavior?

Expected direction: The old value of `i` is produced by `i++`, then the side effect increments `i`, then the assignment writes the old value back. The result is deterministic. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

Use or adapt this snippet during the interview:

```java
int i = 0;
i = i++;
System.out.println(i); // 0
```

### Q4.8. Explain the difference between Nested increments: readable vs legal. Give one snippet where confusing them changes the result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Require step-by-step evaluation, not a memorized final number.

### Q4.9. What should a senior Java candidate know about Side effects in method calls?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Require step-by-step evaluation, not a memorized final number.

### Q4.10. What should a senior Java candidate know about Side effects in string concatenation?

Expected direction: String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations. Require step-by-step evaluation, not a memorized final number.

### Q4.11. What should a senior Java candidate know about Side effects in ternary expressions?

Expected direction: The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null. Require step-by-step evaluation, not a memorized final number.

### Q4.12. What should a senior Java candidate know about Side effects in switch selectors?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Require step-by-step evaluation, not a memorized final number.

### Q4.13. Design a short interview puzzle that tests Interview puzzles with i++, ++i, and assignments, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 5. Arithmetic and Logical Operations Beyond Basic Types

**Purpose of this section.** This section complements primitive-type theory by focusing on operators themselves: precedence, associativity, short-circuiting, bitwise operations, shifts, division, and side effects.

### Q5.1. Explain the difference between Operator precedence vs evaluation order. Give one snippet where confusing them changes the result.

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

### Q5.2. What is the main trap behind Associativity traps, and how would you detect it in code review?

Expected direction: Assignment is right-associative. In `a = b = c`, the rightmost assignment happens first and its value becomes the value assigned further left. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.3. What should a senior Java candidate know about Unary plus and minus?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.4. What should a senior Java candidate know about Prefix and postfix increment/decrement?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Require step-by-step evaluation, not a memorized final number.

### Q5.5. What should a senior Java candidate know about Short-circuit operators: &&, ||?

Expected direction: `&&` and `||` evaluate the right operand only when needed. This is often used for null guards and to avoid expensive or unsafe calls. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
String s = null;
if (s != null && s.length() > 0) {
    System.out.println(s);
}
```

### Q5.6. What should a senior Java candidate know about Non-short-circuit boolean operators: &, |, ^?

Expected direction: `&&` and `||` evaluate the right operand only when needed. This is often used for null guards and to avoid expensive or unsafe calls. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.7. What should a senior Java candidate know about Bitwise operations used with booleans?

Expected direction: `&`, `|`, and `^` can operate on booleans without short-circuiting. Both operands are evaluated, so side effects and exceptions may occur. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.8. What should a senior Java candidate know about Boolean XOR?

Expected direction: `&`, `|`, and `^` can operate on booleans without short-circuiting. Both operands are evaluated, so side effects and exceptions may occur. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.9. What should a senior Java candidate know about Shift operators: <<, >>, >>>?

Expected direction: For `int`, only the low five bits of the shift count are used; for `long`, the low six bits are used. Negative shift counts are also masked, not rejected. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.10. What should a senior Java candidate know about Shift distance masking?

Expected direction: For `int`, only the low five bits of the shift count are used; for `long`, the low six bits are used. Negative shift counts are also masked, not rejected. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
System.out.println(1 << 32); // 1, because int shift count uses only low 5 bits
```

### Q5.11. What should a senior Java candidate know about Negative shift counts?

Expected direction: For `int`, only the low five bits of the shift count are used; for `long`, the low six bits are used. Negative shift counts are also masked, not rejected. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.12. What should a senior Java candidate know about ~x and two’s complement intuition?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q5.13. What should a senior Java candidate know about Integer division and remainder?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.14. Compare `1 / 0`, `1.0 / 0.0`, and `0.0 / 0.0`.

Expected direction: Integer division by zero throws `ArithmeticException`. Floating-point division by zero produces signed infinity or NaN according to floating-point rules. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q5.15. Design a short interview puzzle that tests Interview puzzles with precedence and side effects, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 6. Conditional Operator `?:`

**Purpose of this section.** The ternary operator is compact but type-heavy. Interviewers use it to combine branch laziness, type inference, numeric promotion, boxing, unboxing, and null-handling in one line.

### Q6.1. What should a senior Java candidate know about The conditional operator as an expression?

Expected direction: The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q6.2. What should a senior Java candidate know about Type inference in ternary expressions?

Expected direction: The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q6.3. What should a senior Java candidate know about Numeric ternary promotion?

Expected direction: The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q6.4. What should a senior Java candidate know about Boxing and unboxing in ternary expressions?

Expected direction: The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q6.5. What should a senior Java candidate know about null in ternary expressions?

Expected direction: The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

Use or adapt this snippet during the interview:

```java
Integer x = true ? null : 1; // OK: x becomes null
// int y = true ? null : 1; // NullPointerException at runtime if selected branch unboxes null
```

### Q6.6. Given overloads `m(Integer)` and `m(Long)`, what happens with `m(flag ? 1 : null)`?

Expected direction: The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q6.7. What is the main trap behind Nested ternary readability traps, and how would you detect it in code review?

Expected direction: The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q6.8. What should a senior Java candidate know about Definite assignment through ternary expressions?

Expected direction: The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q6.9. What should a senior Java candidate know about Lazy evaluation of selected branch only?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q6.10. Design a short interview puzzle that tests Interview puzzles with Boolean, Integer, and null, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 7. Basic String Operations

**Purpose of this section.** String operations look friendly, but they hide compile-time concatenation, runtime concatenation, string conversion rules, text block processing, and performance implications.

### Q7.1. What should a senior Java candidate know about String literals and string pool recap?

Expected direction: String literals are interned. The same literal text in the same class-loader context usually refers to the same pooled String object. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### Q7.2. What should a senior Java candidate know about String concatenation as an operation?

Expected direction: String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### Q7.3. Explain the difference between Compile-time concatenation vs runtime concatenation. Give one snippet where confusing them changes the result.

Expected direction: Some rules are enforced by the compiler before the program can run; others produce runtime values or exceptions. Interview snippets should always be classified first. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### Q7.4. What should a senior Java candidate know about + with strings and non-strings?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q7.5. What is the main trap behind Left-to-right concatenation traps, and how would you detect it in code review?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

Use or adapt this snippet during the interview:

```java
System.out.println(1 + 2 + "a"); // 3a
System.out.println("a" + 1 + 2); // a12
```

### Q7.6. What should a senior Java candidate know about null in string concatenation?

Expected direction: String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q7.7. What should a senior Java candidate know about String.valueOf() behavior?

Expected direction: String conversion is based on `String.valueOf`. For null references it produces the text `"null"`, not a NullPointerException. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q7.8. What should a senior Java candidate know about Concatenation and side effects?

Expected direction: String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations. Require step-by-step evaluation, not a memorized final number.

### Q7.9. What should a senior Java candidate know about Concatenation inside loops?

Expected direction: String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### Q7.10. What should a senior Java candidate know about StringBuilder and compiler-generated concatenation?

Expected direction: String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q7.11. What should a senior Java candidate know about invokedynamic string concatenation in modern Java?

Expected direction: String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q7.12. Explain the difference between String.format() vs concatenation. Give one snippet where confusing them changes the result.

Expected direction: String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q7.13. What should a senior Java candidate know about String.join() and Collectors.joining()?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q7.14. What should a senior Java candidate know about Text blocks and indentation rules?

Expected direction: Text blocks have their own indentation and escape-processing rules. They are source-level syntax for multiline string literals, not a different runtime type. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### Q7.15. What should a senior Java candidate know about Escape sequences in normal strings and text blocks?

Expected direction: Text blocks have their own indentation and escape-processing rules. They are source-level syntax for multiline string literals, not a different runtime type. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### Q7.16. What should a senior Java candidate know about stripIndent() and translateEscapes()?

Expected direction: Text blocks have their own indentation and escape-processing rules. They are source-level syntax for multiline string literals, not a different runtime type. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q7.17. Design a short interview puzzle that tests Interview puzzles with "a" + 1 + 2, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 8. String Comparison and Searching

**Purpose of this section.** String search and comparison questions often test whether the candidate separates identity, equality, lexicographic comparison, regex-based APIs, and locale or Unicode expectations.

### Q8.1. Explain the difference between == vs .equals(). Give one snippet where confusing them changes the result.

Expected direction: `==` compares references for objects and values for primitives. `String.equals` compares character content. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q8.2. What should a senior Java candidate know about .equalsIgnoreCase() caveats?

Expected direction: Case-insensitive comparison is not a full locale-aware natural-language comparison. It is useful but should not be treated as universal text collation. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q8.3. What should a senior Java candidate know about compareTo() and lexicographic ordering?

Expected direction: `compareTo` provides lexicographic ordering based on Unicode values, not locale-specific alphabetical order. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q8.4. What should a senior Java candidate know about Unicode and locale-insensitive comparison?

Expected direction: Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### Q8.5. What should a senior Java candidate know about contains(), startsWith(), endsWith()?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q8.6. What should a senior Java candidate know about indexOf() and lastIndexOf()?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q8.7. What should a senior Java candidate know about Empty string edge cases?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### Q8.8. What should a senior Java candidate know about split() and regex surprises?

Expected direction: Several String methods use regular expressions, not literal text. `matches` attempts to match the whole input, not merely find a substring. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

Use or adapt this snippet during the interview:

```java
System.out.println(Arrays.toString("a.b".split(".")));  // [] or surprising result
System.out.println(Arrays.toString("a.b".split("\\."))); // [a, b]
```

### Q8.9. Explain the difference between replace() vs replaceAll(). Give one snippet where confusing them changes the result.

Expected direction: Several String methods use regular expressions, not literal text. `matches` attempts to match the whole input, not merely find a substring. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q8.10. What should a senior Java candidate know about matches() matches the whole string?

Expected direction: Several String methods use regular expressions, not literal text. `matches` attempts to match the whole input, not merely find a substring. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q8.11. Design a short interview puzzle that tests Interview puzzles with regex metacharacters, and explain the expected result.

Expected direction: Several String methods use regular expressions, not literal text. `matches` attempts to match the whole input, not merely find a substring. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 9. Basic Console Output

**Purpose of this section.** Output questions usually test object-to-string conversion, formatting, buffering, stream interleaving, and the difference between arrays as objects and array-content formatting helpers.

### Q9.1. Which of these appends a line separator, and which uses format conversion rules?

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q9.2. What should a senior Java candidate know about System.out as a PrintStream?

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q9.3. What should a senior Java candidate know about Auto-flushing behavior?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q9.4. What should a senior Java candidate know about Formatting with %s, %d, %f, %n?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q9.5. What should a senior Java candidate know about Locale-sensitive formatting?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q9.6. Explain the difference between System.err vs System.out. Give one snippet where confusing them changes the result.

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q9.7. What should a senior Java candidate know about Interleaving output from multiple streams?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q9.8. What is the main trap behind Output buffering traps, and how would you detect it in code review?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q9.9. Explain the difference between Printing arrays: arr.toString() vs Arrays.toString(). Give one snippet where confusing them changes the result.

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
int[] a = {1, 2, 3};
System.out.println(a);                  // class name + hash-like text
System.out.println(Arrays.toString(a)); // [1, 2, 3]
```

### Q9.10. What should a senior Java candidate know about Printing multidimensional arrays?

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q9.11. Design a short interview puzzle that tests Interview puzzles with output order and side effects, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 10. Basic Console Input

**Purpose of this section.** Console input questions are dominated by token-vs-line semantics, Scanner traps, encodings, resource lifetime, and choosing the right input abstraction for the task.

### Q10.1. What should a senior Java candidate know about System.in as an InputStream?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q10.2. What should a senior Java candidate know about Scanner basics?

Expected direction: Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q10.3. What should a senior Java candidate know about BufferedReader basics?

Expected direction: BufferedReader is line-oriented and often faster/predictable for bulk text input, but parsing must be done manually. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q10.4. Console and why it may be null?

Expected direction: `System.console()` may be null in IDEs, redirected processes, or non-interactive environments. Code must handle that case. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q10.5. Why does a line read immediately after `nextInt()` often look empty?

Expected direction: Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q10.6. What is the main trap behind The famous nextInt() / nextLine() trap, and how would you detect it in code review?

Expected direction: Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline. Check whether they explain token vs line input and resource lifetime.

Use or adapt this snippet during the interview:

```java
Scanner sc = new Scanner(System.in);
int age = sc.nextInt();
String name = sc.nextLine(); // consumes the rest of the current line, often empty
```

### Q10.7. Explain the difference between Token-based input vs line-based input. Give one snippet where confusing them changes the result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether they explain token vs line input and resource lifetime.

### Q10.8. What should a senior Java candidate know about Parsing manually from strings?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether they explain token vs line input and resource lifetime.

### Q10.9. What should a senior Java candidate know about Handling invalid input?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether they explain token vs line input and resource lifetime.

### Q10.10. What should a senior Java candidate know about InputMismatchException?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q10.11. What should a senior Java candidate know about Character encoding considerations?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether they explain token vs line input and resource lifetime.

### Q10.12. What should a senior Java candidate know about Closing Scanner and accidentally closing System.in?

Expected direction: Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline. Check whether they explain token vs line input and resource lifetime.

### Q10.13. What should a senior Java candidate know about Reading passwords with Console.readPassword()?

Expected direction: `System.console()` may be null in IDEs, redirected processes, or non-interactive environments. Code must handle that case. Check whether they explain token vs line input and resource lifetime.

### Q10.14. What should a senior Java candidate know about Competitive-programming style fast input?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether they explain token vs line input and resource lifetime.

### Q10.15. Design a short interview puzzle that tests Interview puzzles with mixed scanner methods, and explain the expected result.

Expected direction: Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 11. Blocks, Scope, and Shadowing

**Purpose of this section.** Scope questions ask where a name is valid, which declaration a name resolves to, and why shadowing is legal in some places but illegal or misleading in others.

### Q11.1. What should a senior Java candidate know about Blocks as scope boundaries?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q11.2. What should a senior Java candidate know about Local variable scope?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q11.3. What should a senior Java candidate know about Field hiding by local variables?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Expect compile-time reasoning: target type, inferred type, or selected overload.

Use or adapt this snippet during the interview:

```java
class Demo {
    int x = 1;
    void m() {
        int x = 2;
        System.out.println(x);      // 2
        System.out.println(this.x); // 1
    }
}
```

### Q11.4. What should a senior Java candidate know about Parameter shadowing?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q11.5. Explain the difference between Shadowing vs overriding. Give one snippet where confusing them changes the result.

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q11.6. What should a senior Java candidate know about Shadowing inside loops?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q11.7. What should a senior Java candidate know about Shadowing inside lambdas?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q11.8. Why some redeclarations are illegal?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q11.9. What should a senior Java candidate know about Scope of variables declared in for headers?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q11.10. What should a senior Java candidate know about Scope of variables declared in try-with-resources?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q11.11. What should a senior Java candidate know about Pattern variable scope?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q11.12. Design a short interview puzzle that tests Interview puzzles with same-name variables, and explain the expected result.

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 12. `if`, `else`, and Boolean Control Flow

**Purpose of this section.** Conditional-flow questions test boolean-only conditions, dangling else, short-circuiting, constant conditions, reachability, and null-unboxing through Boolean wrappers.

### Q12.1. What should a senior Java candidate know about if requires boolean, not numeric?

Expected direction: Java requires a boolean condition. There is no implicit conversion from int, reference, or pointer-like values to boolean. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q12.2. What should a senior Java candidate know about Dangling else?

Expected direction: An `else` binds to the nearest unmatched `if`. Braces are the safest way to express intent. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
if (a)
    if (b) f();
    else g(); // belongs to the nearest if: if (b)
```

### Q12.3. What should a senior Java candidate know about Assignment inside conditions?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q12.4. What should a senior Java candidate know about Short-circuiting in conditions?

Expected direction: `&&` and `||` evaluate the right operand only when needed. This is often used for null guards and to avoid expensive or unsafe calls. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q12.5. What should a senior Java candidate know about Side effects inside conditions?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Require step-by-step evaluation, not a memorized final number.

### Q12.6. What should a senior Java candidate know about Constant conditions?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q12.7. What should a senior Java candidate know about Unreachable statement rules?

Expected direction: The compiler rejects certain statements that are provably unreachable. Some constructs, such as non-final conditions, may avoid compile-time reachability conclusions. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q12.8. What should a senior Java candidate know about if (Boolean) and unboxing NullPointerException?

Expected direction: Java requires a boolean condition. There is no implicit conversion from int, reference, or pointer-like values to boolean. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q12.9. What should a senior Java candidate know about Guard clauses and definite assignment?

Expected direction: The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q12.10. Design a short interview puzzle that tests Interview puzzles with nested if/else, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 13. Loops: `while`, `do-while`, and `for`

**Purpose of this section.** Loop questions combine condition evaluation, update timing, reachability, definite assignment, off-by-one errors, and mutations of loop counters.

### Q13.1. What should a senior Java candidate know about while condition evaluation?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q13.2. What should a senior Java candidate know about do-while executes at least once?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q13.3. What should a senior Java candidate know about Classic for loop execution order?

Expected direction: A classic for-loop runs initialization once, then condition, body, update, and condition again until the condition fails or control jumps out. Require step-by-step evaluation, not a memorized final number.

Use or adapt this snippet during the interview:

```java
for (init(); condition(); update()) {
    body();
}
// init once, then condition -> body -> update -> condition ...
```

### Q13.4. What should a senior Java candidate know about Multiple initialization and update expressions?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q13.5. What should a senior Java candidate know about Empty loop components?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q13.6. What should a senior Java candidate know about Infinite loops and reachability?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q13.7. What should a senior Java candidate know about Loop variables and scope?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q13.8. What should a senior Java candidate know about Mutating loop variables inside body?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q13.9. What should a senior Java candidate know about Floating-point loop counters?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q13.10. What is the main trap behind Off-by-one traps, and how would you detect it in code review?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q13.11. What should a senior Java candidate know about continue and update expression execution?

Expected direction: In a classic for-loop, `continue` jumps to the update expression before the next condition check. In while-loops, it jumps directly to condition evaluation. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q13.12. What should a senior Java candidate know about break and skipped update expressions?

Expected direction: `break` exits the loop immediately and skips the for-loop update expression. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q13.13. What should a senior Java candidate know about Definite assignment through loops?

Expected direction: The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q13.14. Design a short interview puzzle that tests Interview puzzles with loop control flow, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 14. Enhanced `for` Loop

**Purpose of this section.** Enhanced for-loops are simple only at the surface. They hide array/Iterable mechanics, copy of the element reference/value, and mutation rules for collections.

### Q14.1. What should a senior Java candidate know about Enhanced for over arrays?

Expected direction: Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q14.2. What should a senior Java candidate know about Enhanced for over Iterable?

Expected direction: Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q14.3. What should a senior Java candidate know about Desugaring conceptually?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q14.4. Why assigning to loop variable does not modify array element?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Expect compile-time reasoning: target type, inferred type, or selected overload.

Use or adapt this snippet during the interview:

```java
int[] xs = {1, 2, 3};
for (int x : xs) {
    x = 99;
}
System.out.println(Arrays.toString(xs)); // [1, 2, 3]
```

### Q14.5. What should a senior Java candidate know about Mutating objects through enhanced for?

Expected direction: Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q14.6. What is the main trap behind Concurrent modification traps, and how would you detect it in code review?

Expected direction: Modifying many collections structurally while iterating with enhanced for can trigger fail-fast behavior. Use Iterator.remove or collect changes separately. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q14.7. What should a senior Java candidate know about Enhanced for and primitive arrays?

Expected direction: Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q14.8. What should a senior Java candidate know about Enhanced for and wrapper arrays?

Expected direction: Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q14.9. What should a senior Java candidate know about null array or iterable behavior?

Expected direction: Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q14.10. What should a senior Java candidate know about Removing elements safely?

Expected direction: Modifying many collections structurally while iterating with enhanced for can trigger fail-fast behavior. Use Iterator.remove or collect changes separately. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q14.11. Design a short interview puzzle that tests Interview puzzles with collection mutation, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 15. `break`, `continue`, Labels, and Reachability

**Purpose of this section.** Labels and jumps are rare in production Java, so interview questions use them to test exact control-flow knowledge and reachability analysis.

### Q15.1. What should a senior Java candidate know about Unlabeled break?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.2. What should a senior Java candidate know about Unlabeled continue?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.3. What should a senior Java candidate know about Labeled break?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
outer:
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        break outer;
    }
}
```

### Q15.4. What should a senior Java candidate know about Labeled continue?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.5. What should a senior Java candidate know about Labels are not goto?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.6. What should a senior Java candidate know about Legal places for labels?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.7. What should a senior Java candidate know about Breaking out of nested loops?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.8. What should a senior Java candidate know about Continuing outer loops?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.9. What should a senior Java candidate know about Labels and blocks?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.10. What should a senior Java candidate know about Unreachable code after break?

Expected direction: The compiler rejects certain statements that are provably unreachable. Some constructs, such as non-final conditions, may avoid compile-time reachability conclusions. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q15.11. What should a senior Java candidate know about break/continue inside try/finally?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q15.12. Design a short interview puzzle that tests Interview puzzles with labels, and explain the expected result.

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 16. Traditional `switch` Statement

**Purpose of this section.** Traditional switch remains a common interview topic because fall-through, default placement, selector restrictions, case constants, and scope rules are easy to misunderstand.

### Q16.1. What should a senior Java candidate know about switch selector types?

Expected direction: Switch selector types depend on the switch form and Java version. Traditional switches support integral-compatible types, enums, String, and wrappers through unboxing. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q16.2. What should a senior Java candidate know about case labels and constant expressions?

Expected direction: Traditional case labels must be compatible compile-time constants or enum constants. Duplicate effective values are illegal. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q16.3. What should a senior Java candidate know about Fall-through behavior?

Expected direction: Traditional colon-style switch can continue into following cases unless control flow exits. This is intentional but error-prone. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

Use or adapt this snippet during the interview:

```java
switch (x) {
    case 1: System.out.println("one");
    case 2: System.out.println("two");
}
```

### Q16.4. What should a senior Java candidate know about Missing break bugs?

Expected direction: Traditional colon-style switch can continue into following cases unless control flow exits. This is intentional but error-prone. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q16.5. What should a senior Java candidate know about default position does not have to be last?

Expected direction: The default label can appear anywhere. Execution starts at the matching label or default and then follows normal fall-through rules. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q16.6. What should a senior Java candidate know about Duplicate case labels?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q16.7. What should a senior Java candidate know about switch with char, byte, short, int?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q16.8. What should a senior Java candidate know about switch with String?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q16.9. What should a senior Java candidate know about switch with enums?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q16.10. What should a senior Java candidate know about switch with wrapper types and unboxing?

Expected direction: Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q16.11. What should a senior Java candidate know about null selector behavior in older switch forms?

Expected direction: Switch selector types depend on the switch form and Java version. Traditional switches support integral-compatible types, enums, String, and wrappers through unboxing. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q16.12. What should a senior Java candidate know about Variable scope inside switch blocks?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q16.13. What should a senior Java candidate know about Declaring variables in cases?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q16.14. What should a senior Java candidate know about Braces around case blocks?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q16.15. Design a short interview puzzle that tests Interview puzzles with fall-through, and explain the expected result.

Expected direction: Traditional colon-style switch can continue into following cases unless control flow exits. This is intentional but error-prone. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 17. Modern `switch` Expressions

**Purpose of this section.** Modern switch questions test the distinction between statements and expressions, arrow labels, exhaustiveness, expression typing, and the absence of accidental fall-through.

### Q17.1. Explain the difference between switch as a statement vs expression. Give one snippet where confusing them changes the result.

Expected direction: An expression produces a value; a statement is executed for its effect. Java has expression statements, but not every statement can appear where a value is required. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q17.2. What should a senior Java candidate know about Arrow labels: case X ->?

Expected direction: Arrow switch labels do not fall through. The right side is an expression, block, or throw statement depending on context. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

Use or adapt this snippet during the interview:

```java
String text = switch (n) {
    case 1 -> "one";
    case 2 -> "two";
    default -> "many";
};
```

### Q17.3. What should a senior Java candidate know about No accidental fall-through with arrow labels?

Expected direction: Arrow switch labels do not fall through. The right side is an expression, block, or throw statement depending on context. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q17.4. What should a senior Java candidate know about Multiple case constants?

Expected direction: Traditional case labels must be compatible compile-time constants or enum constants. Duplicate effective values are illegal. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q17.5. What should a senior Java candidate know about switch expression must produce a value?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q17.6. What should a senior Java candidate know about Exhaustiveness requirement?

Expected direction: A switch expression must cover all possible selector values or provide default. Pattern and enum switches may be exhaustive without default in some cases. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q17.7. What should a senior Java candidate know about default and exhaustive enum switches?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q17.8. What should a senior Java candidate know about switch expression type inference?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q17.9. What should a senior Java candidate know about Throwing from switch branches?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q17.10. What should a senior Java candidate know about Blocks inside switch expression arms?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q17.11. What should a senior Java candidate know about Mixing colon and arrow styles?

Expected direction: Arrow switch labels do not fall through. The right side is an expression, block, or throw statement depending on context. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q17.12. Design a short interview puzzle that tests Interview puzzles with switch expressions, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 18. `yield` in Switch Expressions

**Purpose of this section.** Yield exists to return a value from a switch expression block. The key distinction is that it exits the switch expression, not the surrounding method.

### Q18.1. Why yield exists?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q18.2. Explain the difference between yield vs return. Give one snippet where confusing them changes the result.

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q18.3. Explain the difference between yield vs break. Give one snippet where confusing them changes the result.

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q18.4. What should a senior Java candidate know about yield inside switch expression blocks?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

Use or adapt this snippet during the interview:

```java
String text = switch (n) {
    case 1 -> {
        System.out.println("special");
        yield "one";
    }
    default -> "many";
};
```

### Q18.5. What should a senior Java candidate know about yield and block scoping?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q18.6. What should a senior Java candidate know about yield cannot be used everywhere?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q18.7. What should a senior Java candidate know about yield as a restricted identifier?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q18.8. What should a senior Java candidate know about Yielding values of compatible types?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q18.9. What should a senior Java candidate know about Throwing instead of yielding?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### Q18.10. What should a senior Java candidate know about yield in nested switches?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q18.11. Design a short interview puzzle that tests Interview puzzles with yield and control flow, and explain the expected result.

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 19. Pattern Matching in Basic Control Flow

**Purpose of this section.** Pattern matching introduces flow-sensitive variables into ordinary control flow. The main interview risks are scope, dominance, null, guards, and exhaustiveness.

### Q19.1. What should a senior Java candidate know about instanceof pattern matching?

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q19.2. What should a senior Java candidate know about Pattern variable scope?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q19.3. What should a senior Java candidate know about Flow-sensitive scoping?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q19.4. Why is `s` visible in `if (o instanceof String s && s.length() > 0)`?

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q19.5. Why is `s` not safely visible in the right side of `o instanceof String s || ...`?

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q19.6. What should a senior Java candidate know about Negated pattern checks?

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q19.7. What should a senior Java candidate know about Pattern matching in switch?

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q19.8. What should a senior Java candidate know about Type patterns in switch labels?

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q19.9. What should a senior Java candidate know about Guarded patterns / when clauses?

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q19.10. What should a senior Java candidate know about null handling in pattern switch?

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q19.11. What should a senior Java candidate know about Dominance of switch labels?

Expected direction: A broader pattern before a narrower pattern can dominate it, making the later label unreachable or illegal. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q19.12. What should a senior Java candidate know about Exhaustiveness with sealed classes?

Expected direction: A switch expression must cover all possible selector values or provide default. Pattern and enum switches may be exhaustive without default in some cases. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q19.13. Design a short interview puzzle that tests Interview puzzles with pattern variable visibility, and explain the expected result.

Expected direction: Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 20. `return`, `throw`, and `finally`

**Purpose of this section.** Return and exception questions often become tricky only when finally is involved. The core issue is when expressions are evaluated and how finally can override or mutate outcomes.

### Q20.1. What should a senior Java candidate know about return from methods?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q20.2. What should a senior Java candidate know about Returning expressions and evaluation order?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

### Q20.3. What should a senior Java candidate know about Returning from try?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q20.4. What should a senior Java candidate know about finally after return?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q20.5. What should a senior Java candidate know about finally overriding return values?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

Use or adapt this snippet during the interview:

```java
static int f() {
    try { return 1; }
    finally { return 2; }
}
```

### Q20.6. What should a senior Java candidate know about Mutating returned objects in finally?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask which action wins when return, throw, and finally interact.

### Q20.7. What should a senior Java candidate know about throw and unreachable code?

Expected direction: The compiler rejects certain statements that are provably unreachable. Some constructs, such as non-final conditions, may avoid compile-time reachability conclusions. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q20.8. Explain the difference between Checked vs unchecked exceptions in basic control flow. Give one snippet where confusing them changes the result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q20.9. What should a senior Java candidate know about return inside catch?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q20.10. return inside finally: why it is dangerous?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q20.11. What should a senior Java candidate know about System.exit() and finally blocks?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q20.12. Design a short interview puzzle that tests Interview puzzles with return values and finally, and explain the expected result.

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 21. `try`, `catch`, and Try-With-Resources Basics

**Purpose of this section.** Exception-handling basics include catch selection, resource-closing order, suppressed exceptions, and the differences between finally and try-with-resources.

### Q21.1. What should a senior Java candidate know about Basic try/catch/finally flow?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask which action wins when return, throw, and finally interact.

### Q21.2. What should a senior Java candidate know about Catch block order?

Expected direction: More specific exceptions must be caught before broader supertypes. Otherwise the specific catch block is unreachable. Require step-by-step evaluation, not a memorized final number.

### Q21.3. What should a senior Java candidate know about Multi-catch?

Expected direction: Multi-catch groups unrelated alternatives. The exception parameter is effectively final and has a common supertype. Ask which action wins when return, throw, and finally interact.

### Q21.4. What should a senior Java candidate know about Effectively-final exception parameters?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask which action wins when return, throw, and finally interact.

### Q21.5. What should a senior Java candidate know about Exception variable scope?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Ask which action wins when return, throw, and finally interact.

### Q21.6. What should a senior Java candidate know about Try-with-resources syntax?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask which action wins when return, throw, and finally interact.

### Q21.7. What should a senior Java candidate know about Resource closing order?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Require step-by-step evaluation, not a memorized final number.

### Q21.8. What should a senior Java candidate know about Suppressed exceptions?

Expected direction: Try-with-resources preserves exceptions thrown during resource closing as suppressed exceptions when another exception is already primary. Ask which action wins when return, throw, and finally interact.

Use or adapt this snippet during the interview:

```java
try (var r = resource()) {
    throw new RuntimeException("body");
} // exception from close() may be suppressed under the body exception
```

### Q21.9. Explain the difference between AutoCloseable vs Closeable. Give one snippet where confusing them changes the result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q21.10. What should a senior Java candidate know about Exceptions during initialization of resources?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q21.11. Explain the difference between finally vs try-with-resources. Give one snippet where confusing them changes the result.

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q21.12. Design a short interview puzzle that tests Interview puzzles with suppressed exceptions, and explain the expected result.

Expected direction: Try-with-resources preserves exceptions thrown during resource closing as suppressed exceptions when another exception is already primary. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 22. Method Calls as Basic Operations

**Purpose of this section.** Method call questions test evaluation order, pass-by-value semantics, overload resolution, varargs, static dispatch, and calls through null references.

### Q22.1. What should a senior Java candidate know about Argument evaluation order?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

### Q22.2. What should a senior Java candidate know about Pass-by-value in Java?

Expected direction: Java always passes arguments by value. For objects, the value is a copy of the reference, so object state can be mutated but the caller’s variable cannot be rebound. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
static void change(StringBuilder sb) {
    sb.append("x");     // mutates object
    sb = new StringBuilder("new"); // reassigns local copy only
}
```

### Q22.3. What should a senior Java candidate know about Passing object references by value?

Expected direction: Java always passes arguments by value. For objects, the value is a copy of the reference, so object state can be mutated but the caller’s variable cannot be rebound. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q22.4. Explain the difference between Mutating object state vs reassigning parameters. Give one snippet where confusing them changes the result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q22.5. What should a senior Java candidate know about Overload resolution basics?

Expected direction: Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q22.6. Explain the difference between Widening vs boxing vs varargs. Give one snippet where confusing them changes the result.

Expected direction: Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q22.7. Explain the difference between Static method hiding vs instance method overriding. Give one snippet where confusing them changes the result.

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q22.8. What happens for `ref.staticMethod()` when `ref` is null? What about `ref.instanceMethod()`?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q22.9. Why is calling a static method through an instance legal but discouraged?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q22.10. What should a senior Java candidate know about Varargs as arrays?

Expected direction: Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q22.11. What should a senior Java candidate know about Ambiguous overloads?

Expected direction: Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q22.12. Design a short interview puzzle that tests Interview puzzles with method calls and parameters, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 23. Object Creation and Initialization Expressions

**Purpose of this section.** Object creation questions test the exact sequence from argument evaluation through superclass construction, field initialization, initializer blocks, and constructor bodies.

### Q23.1. What should a senior Java candidate know about new as an expression?

Expected direction: Object creation is an expression: it evaluates constructor arguments, allocates, initializes superclass state, initializes fields/blocks, and runs the constructor body. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q23.2. What should a senior Java candidate know about Constructor argument evaluation order?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

### Q23.3. What should a senior Java candidate know about Field initialization before constructor body?

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q23.4. What should a senior Java candidate know about Instance initializer blocks?

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q23.5. What should a senior Java candidate know about Static initializer blocks?

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q23.6. What should a senior Java candidate know about Constructor chaining with this()?

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q23.7. What should a senior Java candidate know about Superclass initialization order?

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q23.8. What should a senior Java candidate know about Dynamic dispatch during construction?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
class Base { Base() { f(); } void f() {} }
class Child extends Base { int x = 10; void f() { System.out.println(x); } }
// Child constructor may call overridden f before Child fields are initialized.
```

### Q23.9. What should a senior Java candidate know about Anonymous classes?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q23.10. What is the main trap behind Double-brace initialization trap, and how would you detect it in code review?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q23.11. What should a senior Java candidate know about Records and compact constructors?

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q23.12. Design a short interview puzzle that tests Interview puzzles with constructor order, and explain the expected result.

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 24. Lambdas and Basic Functional Operations

**Purpose of this section.** Lambda basics test target typing, capture rules, effective finality, return semantics, this-binding, method references, and overload ambiguity.

### Q24.1. What should a senior Java candidate know about Lambda syntax forms?

Expected direction: Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q24.2. Explain the difference between Expression lambdas vs block lambdas. Give one snippet where confusing them changes the result.

Expected direction: Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q24.3. What should a senior Java candidate know about return inside block lambdas?

Expected direction: Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q24.4. What should a senior Java candidate know about Effectively-final captured variables?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Expect compile-time reasoning: target type, inferred type, or selected overload.

Use or adapt this snippet during the interview:

```java
int x = 10;
Runnable r = () -> System.out.println(x);
// x++; // would make x not effectively final
```

### Q24.5. Explain the difference between Capturing references vs values. Give one snippet where confusing them changes the result.

Expected direction: Captured local variables must be final or effectively final. The lambda captures a value/reference, not a mutable local variable slot. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q24.6. What should a senior Java candidate know about this inside lambda?

Expected direction: Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q24.7. Explain the difference between Lambda vs anonymous class. Give one snippet where confusing them changes the result.

Expected direction: Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q24.8. What should a senior Java candidate know about Method references?

Expected direction: Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q24.9. What should a senior Java candidate know about Overload ambiguity with lambdas?

Expected direction: Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q24.10. What should a senior Java candidate know about Checked exceptions in lambdas?

Expected direction: Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q24.11. Design a short interview puzzle that tests Interview puzzles with capture and mutation, and explain the expected result.

Expected direction: Captured local variables must be final or effectively final. The lambda captures a value/reference, not a mutable local variable slot. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 25. Basic Operations with Arrays

**Purpose of this section.** Array operations combine object identity, bounds checks, null checks, copying semantics, shallow/deep equality, and evaluation order.

### Q25.1. What should a senior Java candidate know about Array creation expressions?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q25.2. What should a senior Java candidate know about Array initializer syntax?

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q25.3. What should a senior Java candidate know about Default values in arrays?

Expected direction: Fields and array elements receive default values automatically. Local variables do not; they must be definitely assigned before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q25.4. What should a senior Java candidate know about Accessing array elements?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q25.5. What should a senior Java candidate know about Array index evaluation order?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

Use or adapt this snippet during the interview:

```java
int[] a = {10, 20};
int i = 0;
a[i] = i = 1; // left array/index evaluated before right side assignment
```

### Q25.6. What should a senior Java candidate know about ArrayIndexOutOfBoundsException?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q25.7. What should a senior Java candidate know about NullPointerException with arrays?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q25.8. What should a senior Java candidate know about Array assignment and reference copying?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q25.9. Explain the difference between Shallow copy vs deep copy. Give one snippet where confusing them changes the result.

Expected direction: A shallow copy duplicates the container but not referenced objects. A deep copy also duplicates reachable mutable objects according to the chosen depth. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q25.10. What should a senior Java candidate know about clone() on arrays?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q25.11. What should a senior Java candidate know about System.arraycopy()?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q25.12. What should a senior Java candidate know about Arrays.copyOf()?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q25.13. Explain the difference between Arrays.equals() vs ==. Give one snippet where confusing them changes the result.

Expected direction: `==` compares references for objects and values for primitives. `String.equals` compares character content. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q25.14. What should a senior Java candidate know about Arrays.deepEquals()?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q25.15. Design a short interview puzzle that tests Interview puzzles with array mutation, and explain the expected result.

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 26. Basic Operations with Collections

**Purpose of this section.** Collection basics are full of practical traps: fixed-size views, immutable factories, equality-based lookup, fail-fast iterators, and mutable keys.

### Q26.1. What should a senior Java candidate know about List.of() immutability?

Expected direction: `List.of` creates an unmodifiable list and rejects null elements. It is not the same as an ArrayList. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q26.2. What is the main trap behind Arrays.asList() fixed-size list trap, and how would you detect it in code review?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

Use or adapt this snippet during the interview:

```java
List<String> xs = Arrays.asList("a", "b");
xs.set(0, "x"); // OK
// xs.add("c"); // UnsupportedOperationException
```

### Q26.3. What should a senior Java candidate know about new ArrayList<>(Arrays.asList(...))?

Expected direction: Object creation is an expression: it evaluates constructor arguments, allocates, initializes superclass state, initializes fields/blocks, and runs the constructor body. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q26.4. What should a senior Java candidate know about contains() and .equals()?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q26.5. What should a senior Java candidate know about Removing while iterating?

Expected direction: Modifying many collections structurally while iterating with enhanced for can trigger fail-fast behavior. Use Iterator.remove or collect changes separately. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q26.6. What should a senior Java candidate know about Iterator removal?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q26.7. What should a senior Java candidate know about Fail-fast behavior?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q26.8. What should a senior Java candidate know about ConcurrentModificationException misconceptions?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q26.9. What should a senior Java candidate know about Map.get() and missing keys?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q26.10. What should a senior Java candidate know about Map.getOrDefault()?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q26.11. What is the main trap behind computeIfAbsent() basics and traps, and how would you detect it in code review?

Expected direction: `computeIfAbsent` computes only when there is no mapping or the existing mapping is null, but the mapping function must avoid unsafe side effects on the same map. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q26.12. Build or explain the essential entries of the Mutable keys in maps. Which mistakes should it prevent?

Expected direction: Mutating fields used by `equals`/`hashCode` after inserting a key into a hash map can make the entry unreachable by lookup. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q26.13. Design a short interview puzzle that tests Interview puzzles with lists and maps, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 27. `var` and Local Variable Type Inference

**Purpose of this section.** var questions test whether the candidate understands that inference happens at compile time and produces a fixed static type, not a dynamic variable.

### Q27.1. What should a senior Java candidate know about What var is and is not?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Expect compile-time reasoning: target type, inferred type, or selected overload.

### Q27.2. What should a senior Java candidate know about var requires initializer?

Expected direction: Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q27.3. What should a senior Java candidate know about var is not dynamic typing?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q27.4. What should a senior Java candidate know about Inferred type may be surprising?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q27.5. What should a senior Java candidate know about var with literals?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

Use or adapt this snippet during the interview:

```java
var a = 1;  // int
var b = 1L; // long
var c = 1.0; // double
```

### Q27.6. What should a senior Java candidate know about var with diamond operator?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q27.7. What should a senior Java candidate know about var with anonymous classes?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q27.8. var with lambdas: why it cannot infer target type alone?

Expected direction: Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q27.9. What should a senior Java candidate know about var in enhanced for?

Expected direction: Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q27.10. What should a senior Java candidate know about var in try-with-resources?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q27.11. What should a senior Java candidate know about var with null?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q27.12. Design a short interview puzzle that tests Interview puzzles with inferred types, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 28. `assert` and Other Often-Forgotten Basic Syntax

**Purpose of this section.** Forgotten syntax questions test features that exist in the language but are often disabled, rarely used, or misunderstood as runtime validation tools.

### Q28.1. What should a senior Java candidate know about assert syntax?

Expected direction: Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q28.2. What should a senior Java candidate know about Assertions disabled by default?

Expected direction: Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

Use or adapt this snippet during the interview:

```java
assert expensiveCheck(); // may not run unless assertions are enabled with -ea
```

### Q28.3. What should a senior Java candidate know about Enabling assertions?

Expected direction: Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q28.4. What should a senior Java candidate know about Assertion side effects?

Expected direction: Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`. Require step-by-step evaluation, not a memorized final number.

### Q28.5. Explain the difference between assert vs validation. Give one snippet where confusing them changes the result.

Expected direction: Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q28.6. What should a senior Java candidate know about assert and production code?

Expected direction: Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q28.7. What should a senior Java candidate know about strictfp historical context?

Expected direction: `strictfp` historically forced strict floating-point semantics; modern Java has made floating-point behavior more consistently strict, reducing its practical importance. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q28.8. What should a senior Java candidate know about native, transient, volatile, synchronized as "basic-looking" modifiers?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q28.9. Design a short interview puzzle that tests Interview puzzles with disabled assertions, and explain the expected result.

Expected direction: Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 29. Comments, Unicode Escapes, and Lexical Traps

**Purpose of this section.** Lexical traps are intentionally obscure. The most important one is that Unicode escapes are processed before tokenization, even inside comments and strings.

### Q29.1. What should a senior Java candidate know about Single-line comments?

Expected direction: Comments are removed during lexical processing, but Unicode escapes may be processed before the comment is recognized. Block comments do not nest. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q29.2. What should a senior Java candidate know about Block comments do not nest?

Expected direction: Comments are removed during lexical processing, but Unicode escapes may be processed before the comment is recognized. Block comments do not nest. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q29.3. What should a senior Java candidate know about Javadoc comments?

Expected direction: Comments are removed during lexical processing, but Unicode escapes may be processed before the comment is recognized. Block comments do not nest. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q29.4. What should a senior Java candidate know about Unicode escapes processed early?

Expected direction: Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
// The following can create a real line break before lexical analysis:
// \u000a System.out.println("surprise");
```

### Q29.5. What should a senior Java candidate know about Unicode escapes inside comments?

Expected direction: Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q29.6. What should a senior Java candidate know about Hidden line terminators?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q29.7. What should a senior Java candidate know about Escaped identifiers? No, but Unicode characters in identifiers?

Expected direction: Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q29.8. Explain the difference between Reserved keywords vs restricted identifiers. Give one snippet where confusing them changes the result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q29.9. What should a senior Java candidate know about var, yield, and contextual restrictions?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q29.10. Design a short interview puzzle that tests Interview puzzles with code that looks commented out but is not, and explain the expected result.

Expected direction: Comments are removed during lexical processing, but Unicode escapes may be processed before the comment is recognized. Block comments do not nest. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 30. Packages, Imports, and Simple Name Resolution

**Purpose of this section.** Name-resolution questions test whether imports are understood as compile-time shortcuts rather than runtime loading mechanisms, and how ambiguity is resolved.

### Q30.1. What should a senior Java candidate know about package declaration basics?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q30.2. What should a senior Java candidate know about Single-type imports?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q30.3. What should a senior Java candidate know about On-demand imports?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q30.4. What should a senior Java candidate know about Static imports?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
import static java.lang.Math.*;
System.out.println(sqrt(16));
```

### Q30.5. What should a senior Java candidate know about Name conflicts?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q30.6. What should a senior Java candidate know about java.lang implicit import?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q30.7. What should a senior Java candidate know about Same-package visibility?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q30.8. What should a senior Java candidate know about Fully qualified names?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q30.9. What should a senior Java candidate know about Static import ambiguity?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q30.10. What should a senior Java candidate know about Import does not include subpackages?

Expected direction: Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q30.11. Design a short interview puzzle that tests Interview puzzles with conflicting names, and explain the expected result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 31. Compilation vs Runtime Traps

**Purpose of this section.** A good interview answer first classifies a snippet: does it fail compilation, fail at runtime, or produce a surprising value? This section formalizes that workflow.

### Q31.1. What should a senior Java candidate know about Code that does not compile?

Expected direction: A compile-time error means the program is not valid Java source for the selected language level; no runtime answer exists. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q31.2. What should a senior Java candidate know about Code that compiles but throws?

Expected direction: Some snippets pass static checks but fail during execution because of null, bounds, arithmetic, class initialization, or resource errors. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q31.3. What should a senior Java candidate know about Code that compiles and prints surprising output?

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q31.4. What should a senior Java candidate know about Constant expressions and compiler folding?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

Use or adapt this snippet during the interview:

```java
final int x = 1 + 2; // compile-time constant
byte b = x;          // OK because x is constant and fits in byte
```

### Q31.5. What should a senior Java candidate know about Unreachable code detection?

Expected direction: The compiler rejects certain statements that are provably unreachable. Some constructs, such as non-final conditions, may avoid compile-time reachability conclusions. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q31.6. What should a senior Java candidate know about Definite assignment analysis limitations?

Expected direction: The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q31.7. What should a senior Java candidate know about Runtime class initialization timing?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q31.8. Explain the difference between Source compatibility vs bytecode compatibility. Give one snippet where confusing them changes the result.

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q31.9. Build or explain the essential entries of the Interview classification checklist. Which mistakes should it prevent?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

## 32. Classic Interview Puzzle Section

**Purpose of this section.** This section collects canonical snippets. Each puzzle is less about memorizing the output and more about applying the chapter’s classification strategy.

### Q32.1. What happens when this snippet is compiled and run: `int i = 0; i = i++;`?

Expected direction: The old value of `i` is produced by `i++`, then the side effect increments `i`, then the assignment writes the old value back. The result is deterministic. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.2. What happens when this snippet is compiled and run: `System.out.println(i++ + ++i);`?

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.3. What happens when this snippet is compiled and run: `if (flag = true)`?

Expected direction: This is a canonical interview snippet. The answer should be derived by checking compilation, evaluation order, conversions, and control flow rather than by guessing. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.4. What happens when this snippet is compiled and run: `String s = null; System.out.println(s + "x");`?

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.5. What happens when this snippet is compiled and run: `System.out.println(null + "x");`?

Expected direction: Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.6. What happens when this snippet is compiled and run: `Scanner.nextInt()` followed by `nextLine()`?

Expected direction: Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.7. What happens when this snippet is compiled and run: `switch` without `break`?

Expected direction: This is a canonical interview snippet. The answer should be derived by checking compilation, evaluation order, conversions, and control flow rather than by guessing. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.8. What happens when this snippet is compiled and run: `default` in the middle of switch?

Expected direction: This is a canonical interview snippet. The answer should be derived by checking compilation, evaluation order, conversions, and control flow rather than by guessing. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.9. What happens when this snippet is compiled and run: `switch` expression without exhaustive branches?

Expected direction: This is a canonical interview snippet. The answer should be derived by checking compilation, evaluation order, conversions, and control flow rather than by guessing. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.10. What happens when this snippet is compiled and run: `yield` vs `return`?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.11. What happens when this snippet is compiled and run: Labeled `break` from nested loops?

Expected direction: Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q32.12. What happens when this snippet is compiled and run: `finally` overriding return?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.13. What happens when this snippet is compiled and run: `Arrays.asList()` modification trap?

Expected direction: Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.14. What happens when this snippet is compiled and run: Enhanced `for` reassignment trap?

Expected direction: Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q32.15. What happens when this snippet is compiled and run: `var x = 1;` vs `var x = 1L;`?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q32.16. What happens when this snippet is compiled and run: Pattern variable scope trap?

Expected direction: A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically. Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### Q32.17. What happens when this snippet is compiled and run: Unicode escape in comment trap?

Expected direction: Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q32.18. What happens when this snippet is compiled and run: Static method call through `null` reference?

Expected direction: A static method call written through a null reference can still work because the method is resolved from the compile-time type, though the style is misleading. A strong answer should name the relevant Java rule and apply it to a short snippet.

## 33. Summary Tables

**Purpose of this section.** The tables act as quick recall material. They compress rules into checklists suitable for interview preparation and review.

### Q33.1. Build or explain the essential entries of the Statement vs expression table. Which mistakes should it prevent?

Expected direction: An expression produces a value; a statement is executed for its effect. Java has expression statements, but not every statement can appear where a value is required. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q33.2. Build or explain the essential entries of the Initialization rules table. Which mistakes should it prevent?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q33.3. Build or explain the essential entries of the Definite assignment cheat sheet. Which mistakes should it prevent?

Expected direction: The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q33.4. Build or explain the essential entries of the Evaluation order cheat sheet. Which mistakes should it prevent?

Expected direction: Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions. Require step-by-step evaluation, not a memorized final number.

### Q33.5. Build or explain the essential entries of the Operator precedence table. Which mistakes should it prevent?

Expected direction: Precedence controls grouping, not necessarily the chronological order of evaluation. Parentheses change grouping and often make interview answers safer. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q33.6. Build or explain the essential entries of the Assignment and compound assignment table. Which mistakes should it prevent?

Expected direction: Compound assignment includes an implicit narrowing conversion equivalent to `E1 = (T)((E1) op (E2))`, while evaluating the left side only once. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q33.7. Build or explain the essential entries of the String operation traps table. Which mistakes should it prevent?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q33.8. Build or explain the essential entries of the Scanner method behavior table. Which mistakes should it prevent?

Expected direction: Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline. Check whether they explain token vs line input and resource lifetime.

### Q33.9. Build or explain the essential entries of the Loop control flow table. Which mistakes should it prevent?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q33.10. Build or explain the essential entries of the Traditional switch vs switch expression table. Which mistakes should it prevent?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q33.11. Build or explain the essential entries of the break vs continue vs return vs yield table. Which mistakes should it prevent?

Expected direction: `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q33.12. Build or explain the essential entries of the Try/finally control-flow table. Which mistakes should it prevent?

Expected direction: A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers. Ask which action wins when return, throw, and finally interact.

### Q33.13. Build or explain the essential entries of the var inference examples table. Which mistakes should it prevent?

Expected direction: `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed. Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### Q33.14. Build or explain the essential entries of the Most common interview mistakes checklist. Which mistakes should it prevent?

Expected direction: This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics. A strong answer should name the relevant Java rule and apply it to a short snippet.

## 34. Practical Interview Strategy

**Purpose of this section.** This closing section gives a repeatable reasoning procedure for basic Java snippets under interview pressure.

### Q34.1. What should a senior Java candidate know about First ask: does it compile??

Expected direction: Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q34.2. What should a senior Java candidate know about Then ask: what is initialized??

Expected direction: Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition. Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### Q34.3. What should a senior Java candidate know about Then ask: what is evaluated first??

Expected direction: Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q34.4. What should a senior Java candidate know about Then ask: is there boxing, unboxing, or promotion??

Expected direction: Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q34.5. What should a senior Java candidate know about Then ask: is this a statement or expression??

Expected direction: An expression produces a value; a statement is executed for its effect. Java has expression statements, but not every statement can appear where a value is required. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q34.6. What should a senior Java candidate know about Then ask: can control flow skip this assignment??

Expected direction: Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition. Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### Q34.7. What should a senior Java candidate know about Then ask: can null appear??

Expected direction: Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q34.8. What should a senior Java candidate know about Then ask: is this old switch or new switch??

Expected direction: Object creation is an expression: it evaluates constructor arguments, allocates, initializes superclass state, initializes fields/blocks, and runs the constructor body. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q34.9. What should a senior Java candidate know about Then ask: is there hidden fall-through??

Expected direction: Traditional colon-style switch can continue into following cases unless control flow exits. This is intentional but error-prone. A strong answer should name the relevant Java rule and apply it to a short snippet.

### Q34.10. What should a senior Java candidate know about Then ask: is the result compile-time or runtime??

Expected direction: Some rules are enforced by the compiler before the program can run; others produce runtime values or exceptions. Interview snippets should always be classified first. A strong answer should name the relevant Java rule and apply it to a short snippet.

## Appendix. Suggested Scoring Rubric

| Score | Meaning |
|---:|---|
| 0 | Guesses the answer or gives a language from another ecosystem |
| 1 | Knows the result but cannot explain the rule |
| 2 | Explains the main rule but misses edge cases |
| 3 | Explains compile-time vs runtime behavior and relevant Java rule |
| 4 | Adds version/API nuance, gives counterexamples, and writes a safe alternative |

A strong senior-level answer usually includes: whether it compiles, the exact evaluation/control-flow path, the relevant conversion or scope rule, and a safer production-style rewrite.