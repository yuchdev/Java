# Advanced Java Basic Operations — Theoretical Reference

A point-by-point reference following the proposed chapter table of contents. It focuses on non-trivial, obscure, and interview-relevant Java behavior around variable initialization, assignments, expressions, strings, console I/O, control flow, switch/yield, exceptions, arrays, collections, lambdas, `var`, comments, imports, and compilation/runtime traps.


## Reference Baseline

This material is written for modern Java interview preparation. Unless a section explicitly says otherwise, assume Java 21+ syntax, with notes where behavior is older or version-sensitive. The most important authoritative references are:

- Java Language Specification, current edition: https://docs.oracle.com/javase/specs/jls/se26/html/index.html
- Oracle Java documentation on switch expressions/statements: https://docs.oracle.com/en/java/javase/26/language/switch-expressions-statements.html
- JEP 441, Pattern Matching for switch: https://openjdk.org/jeps/441


## Full Covered ToC

1. What Counts as a “Basic Operation” in Java?
   1.1. Expressions vs statements
   1.2. Declarations vs assignments
   1.3. Initialization vs assignment vs reassignment
   1.4. Compile-time behavior vs runtime behavior
   1.5. Syntax sugar vs actual language rules
   1.6. Why “simple Java code” often hides complex rules
   1.7. Interview trap categories: compilation, result, exception, overload, control flow
2. Variable Declaration and Initialization
   2.1. Local variables vs fields vs array elements
   2.2. Default values: where they exist and where they do not
   2.3. Why local variables must be definitely assigned
   2.4. Definite assignment and compiler flow analysis
   2.5. Branches, loops, `try/catch`, and definite assignment
   2.6. Final local variables and blank finals
   2.7. `final` fields and constructor initialization
   2.8. Initialization order inside a method
   2.9. Multiple declarations in one statement
   2.10. Left-to-right evaluation of declarators
   2.11. Shadowing during initialization
   2.12. Illegal self-reference examples
   2.13. Initialization blocks vs constructors
   2.14. Static initialization order traps
   2.15. Circular initialization between static fields
   2.16. Interview puzzles with “variable might not have been initialized”
3. Assignment Operators and Expression Results
   3.1. Assignment as an expression
   3.2. Return value of assignment
   3.3. Chained assignment: `a = b = c`
   3.4. Assignment associativity
   3.5. Assigning inside `if`, `while`, and `for`
   3.6. Why `if (x = true)` compiles
   3.7. Why `if (x = 1)` does not compile
   3.8. Compound assignment and hidden casts
   3.9. `+=`, `-=`, `*=`, `/=`, `%=` subtleties
   3.10. Assignment evaluation order: left side vs right side
   3.11. Assignment to array elements and side effects
   3.12. Assignment to fields through `null` references
   3.13. Assignment in lambdas and effectively-final variables
   3.14. Interview puzzles with assignment expressions
4. Evaluation Order and Side Effects
   4.1. Java’s left-to-right evaluation guarantee
   4.2. Operand evaluation before operator execution
   4.3. Method argument evaluation order
   4.4. Array index evaluation order
   4.5. Field access evaluation order
   4.6. Post-increment vs pre-increment
   4.7. `i = i++` and why it is not undefined behavior
   4.8. Nested increments: readable vs legal
   4.9. Side effects in method calls
   4.10. Side effects in string concatenation
   4.11. Side effects in ternary expressions
   4.12. Side effects in `switch` selectors
   4.13. Interview puzzles with `i++`, `++i`, and assignments
5. Arithmetic and Logical Operations Beyond Basic Types
   5.1. Operator precedence vs evaluation order
   5.2. Associativity traps
   5.3. Unary plus and minus
   5.4. Prefix and postfix increment/decrement
   5.5. Short-circuit operators: `&&`, `||`
   5.6. Non-short-circuit boolean operators: `&`, `|`, `^`
   5.7. Bitwise operations used with booleans
   5.8. Boolean XOR
   5.9. Shift operators: `<<`, `>>`, `>>>`
   5.10. Shift distance masking
   5.11. Negative shift counts
   5.12. `~x` and two’s complement intuition
   5.13. Integer division and remainder
   5.14. Evaluation of division by zero
   5.15. Interview puzzles with precedence and side effects
6. Conditional Operator `?:`
   6.1. The conditional operator as an expression
   6.2. Type inference in ternary expressions
   6.3. Numeric ternary promotion
   6.4. Boxing and unboxing in ternary expressions
   6.5. `null` in ternary expressions
   6.6. Ternary expressions and overload resolution
   6.7. Nested ternary readability traps
   6.8. Definite assignment through ternary expressions
   6.9. Lazy evaluation of selected branch only
   6.10. Interview puzzles with `Boolean`, `Integer`, and `null`
7. Basic String Operations
   7.1. String literals and string pool recap
   7.2. String concatenation as an operation
   7.3. Compile-time concatenation vs runtime concatenation
   7.4. `+` with strings and non-strings
   7.5. Left-to-right concatenation traps
   7.6. `null` in string concatenation
   7.7. `String.valueOf()` behavior
   7.8. Concatenation and side effects
   7.9. Concatenation inside loops
   7.10. `StringBuilder` and compiler-generated concatenation
   7.11. `invokedynamic` string concatenation in modern Java
   7.12. `String.format()` vs concatenation
   7.13. `String.join()` and `Collectors.joining()`
   7.14. Text blocks and indentation rules
   7.15. Escape sequences in normal strings and text blocks
   7.16. `stripIndent()` and `translateEscapes()`
   7.17. Interview puzzles with `"a" + 1 + 2`
8. String Comparison and Searching
   8.1. `==` vs `.equals()`
   8.2. `.equalsIgnoreCase()` caveats
   8.3. `compareTo()` and lexicographic ordering
   8.4. Unicode and locale-insensitive comparison
   8.5. `contains()`, `startsWith()`, `endsWith()`
   8.6. `indexOf()` and `lastIndexOf()`
   8.7. Empty string edge cases
   8.8. `split()` and regex surprises
   8.9. `replace()` vs `replaceAll()`
   8.10. `matches()` matches the whole string
   8.11. Interview puzzles with regex metacharacters
9. Basic Console Output
   9.1. `System.out.print()` vs `println()` vs `printf()`
   9.2. `System.out` as a `PrintStream`
   9.3. Auto-flushing behavior
   9.4. Formatting with `%s`, `%d`, `%f`, `%n`
   9.5. Locale-sensitive formatting
   9.6. `System.err` vs `System.out`
   9.7. Interleaving output from multiple streams
   9.8. Output buffering traps
   9.9. Printing arrays: `arr.toString()` vs `Arrays.toString()`
   9.10. Printing multidimensional arrays
   9.11. Interview puzzles with output order and side effects
10. Basic Console Input
   10.1. `System.in` as an `InputStream`
   10.2. `Scanner` basics
   10.3. `BufferedReader` basics
   10.4. `Console` and why it may be `null`
   10.5. `Scanner.next()` vs `nextLine()`
   10.6. The famous `nextInt()` / `nextLine()` trap
   10.7. Token-based input vs line-based input
   10.8. Parsing manually from strings
   10.9. Handling invalid input
   10.10. `InputMismatchException`
   10.11. Character encoding considerations
   10.12. Closing `Scanner` and accidentally closing `System.in`
   10.13. Reading passwords with `Console.readPassword()`
   10.14. Competitive-programming style fast input
   10.15. Interview puzzles with mixed scanner methods
11. Blocks, Scope, and Shadowing
   11.1. Blocks as scope boundaries
   11.2. Local variable scope
   11.3. Field hiding by local variables
   11.4. Parameter shadowing
   11.5. Shadowing vs overriding
   11.6. Shadowing inside loops
   11.7. Shadowing inside lambdas
   11.8. Why some redeclarations are illegal
   11.9. Scope of variables declared in `for` headers
   11.10. Scope of variables declared in `try-with-resources`
   11.11. Pattern variable scope
   11.12. Interview puzzles with same-name variables
12. `if`, `else`, and Boolean Control Flow
   12.1. `if` requires boolean, not numeric
   12.2. Dangling `else`
   12.3. Assignment inside conditions
   12.4. Short-circuiting in conditions
   12.5. Side effects inside conditions
   12.6. Constant conditions
   12.7. Unreachable statement rules
   12.8. `if (Boolean)` and unboxing `NullPointerException`
   12.9. Guard clauses and definite assignment
   12.10. Interview puzzles with nested `if/else`
13. Loops: `while`, `do-while`, and `for`
   13.1. `while` condition evaluation
   13.2. `do-while` executes at least once
   13.3. Classic `for` loop execution order
   13.4. Multiple initialization and update expressions
   13.5. Empty loop components
   13.6. Infinite loops and reachability
   13.7. Loop variables and scope
   13.8. Mutating loop variables inside body
   13.9. Floating-point loop counters
   13.10. Off-by-one traps
   13.11. `continue` and update expression execution
   13.12. `break` and skipped update expressions
   13.13. Definite assignment through loops
   13.14. Interview puzzles with loop control flow
14. Enhanced `for` Loop
   14.1. Enhanced `for` over arrays
   14.2. Enhanced `for` over `Iterable`
   14.3. Desugaring conceptually
   14.4. Why assigning to loop variable does not modify array element
   14.5. Mutating objects through enhanced `for`
   14.6. Concurrent modification traps
   14.7. Enhanced `for` and primitive arrays
   14.8. Enhanced `for` and wrapper arrays
   14.9. `null` array or iterable behavior
   14.10. Removing elements safely
   14.11. Interview puzzles with collection mutation
15. `break`, `continue`, Labels, and Reachability
   15.1. Unlabeled `break`
   15.2. Unlabeled `continue`
   15.3. Labeled `break`
   15.4. Labeled `continue`
   15.5. Labels are not `goto`
   15.6. Legal places for labels
   15.7. Breaking out of nested loops
   15.8. Continuing outer loops
   15.9. Labels and blocks
   15.10. Unreachable code after `break`
   15.11. `break`/`continue` inside `try/finally`
   15.12. Interview puzzles with labels
16. Traditional `switch` Statement
   16.1. `switch` selector types
   16.2. `case` labels and constant expressions
   16.3. Fall-through behavior
   16.4. Missing `break` bugs
   16.5. `default` position does not have to be last
   16.6. Duplicate case labels
   16.7. `switch` with `char`, `byte`, `short`, `int`
   16.8. `switch` with `String`
   16.9. `switch` with enums
   16.10. `switch` with wrapper types and unboxing
   16.11. `null` selector behavior in older switch forms
   16.12. Variable scope inside switch blocks
   16.13. Declaring variables in cases
   16.14. Braces around case blocks
   16.15. Interview puzzles with fall-through
17. Modern `switch` Expressions
   17.1. `switch` as a statement vs expression
   17.2. Arrow labels: `case X ->`
   17.3. No accidental fall-through with arrow labels
   17.4. Multiple case constants
   17.5. `switch` expression must produce a value
   17.6. Exhaustiveness requirement
   17.7. `default` and exhaustive enum switches
   17.8. `switch` expression type inference
   17.9. Throwing from switch branches
   17.10. Blocks inside switch expression arms
   17.11. Mixing colon and arrow styles
   17.12. Interview puzzles with switch expressions
18. `yield` in Switch Expressions
   18.1. Why `yield` exists
   18.2. `yield` vs `return`
   18.3. `yield` vs `break`
   18.4. `yield` inside switch expression blocks
   18.5. `yield` and block scoping
   18.6. `yield` cannot be used everywhere
   18.7. `yield` as a restricted identifier
   18.8. Yielding values of compatible types
   18.9. Throwing instead of yielding
   18.10. `yield` in nested switches
   18.11. Interview puzzles with `yield` and control flow
19. Pattern Matching in Basic Control Flow
   19.1. `instanceof` pattern matching
   19.2. Pattern variable scope
   19.3. Flow-sensitive scoping
   19.4. Pattern variables with `&&`
   19.5. Why pattern variables may not exist after `||`
   19.6. Negated pattern checks
   19.7. Pattern matching in `switch`
   19.8. Type patterns in switch labels
   19.9. Guarded patterns / `when` clauses
   19.10. `null` handling in pattern switch
   19.11. Dominance of switch labels
   19.12. Exhaustiveness with sealed classes
   19.13. Interview puzzles with pattern variable visibility
20. `return`, `throw`, and `finally`
   20.1. `return` from methods
   20.2. Returning expressions and evaluation order
   20.3. Returning from `try`
   20.4. `finally` after `return`
   20.5. `finally` overriding return values
   20.6. Mutating returned objects in `finally`
   20.7. `throw` and unreachable code
   20.8. Checked vs unchecked exceptions in basic control flow
   20.9. `return` inside `catch`
   20.10. `return` inside `finally`: why it is dangerous
   20.11. `System.exit()` and finally blocks
   20.12. Interview puzzles with return values and finally
21. `try`, `catch`, and Try-With-Resources Basics
   21.1. Basic `try/catch/finally` flow
   21.2. Catch block order
   21.3. Multi-catch
   21.4. Effectively-final exception parameters
   21.5. Exception variable scope
   21.6. Try-with-resources syntax
   21.7. Resource closing order
   21.8. Suppressed exceptions
   21.9. `AutoCloseable` vs `Closeable`
   21.10. Exceptions during initialization of resources
   21.11. `finally` vs try-with-resources
   21.12. Interview puzzles with suppressed exceptions
22. Method Calls as Basic Operations
   22.1. Argument evaluation order
   22.2. Pass-by-value in Java
   22.3. Passing object references by value
   22.4. Mutating object state vs reassigning parameters
   22.5. Overload resolution basics
   22.6. Widening vs boxing vs varargs
   22.7. Static method hiding vs instance method overriding
   22.8. Calling methods through `null` references
   22.9. Static method call through object reference
   22.10. Varargs as arrays
   22.11. Ambiguous overloads
   22.12. Interview puzzles with method calls and parameters
23. Object Creation and Initialization Expressions
   23.1. `new` as an expression
   23.2. Constructor argument evaluation order
   23.3. Field initialization before constructor body
   23.4. Instance initializer blocks
   23.5. Static initializer blocks
   23.6. Constructor chaining with `this()`
   23.7. Superclass initialization order
   23.8. Dynamic dispatch during construction
   23.9. Anonymous classes
   23.10. Double-brace initialization trap
   23.11. Records and compact constructors
   23.12. Interview puzzles with constructor order
24. Lambdas and Basic Functional Operations
   24.1. Lambda syntax forms
   24.2. Expression lambdas vs block lambdas
   24.3. `return` inside block lambdas
   24.4. Effectively-final captured variables
   24.5. Capturing references vs values
   24.6. `this` inside lambda
   24.7. Lambda vs anonymous class
   24.8. Method references
   24.9. Overload ambiguity with lambdas
   24.10. Checked exceptions in lambdas
   24.11. Interview puzzles with capture and mutation
25. Basic Operations with Arrays
   25.1. Array creation expressions
   25.2. Array initializer syntax
   25.3. Default values in arrays
   25.4. Accessing array elements
   25.5. Array index evaluation order
   25.6. `ArrayIndexOutOfBoundsException`
   25.7. `NullPointerException` with arrays
   25.8. Array assignment and reference copying
   25.9. Shallow copy vs deep copy
   25.10. `clone()` on arrays
   25.11. `System.arraycopy()`
   25.12. `Arrays.copyOf()`
   25.13. `Arrays.equals()` vs `==`
   25.14. `Arrays.deepEquals()`
   25.15. Interview puzzles with array mutation
26. Basic Operations with Collections
   26.1. `List.of()` immutability
   26.2. `Arrays.asList()` fixed-size list trap
   26.3. `new ArrayList<>(Arrays.asList(...))`
   26.4. `contains()` and `.equals()`
   26.5. Removing while iterating
   26.6. Iterator removal
   26.7. Fail-fast behavior
   26.8. `ConcurrentModificationException` misconceptions
   26.9. `Map.get()` and missing keys
   26.10. `Map.getOrDefault()`
   26.11. `computeIfAbsent()` basics and traps
   26.12. Mutable keys in maps
   26.13. Interview puzzles with lists and maps
27. `var` and Local Variable Type Inference
   27.1. What `var` is and is not
   27.2. `var` requires initializer
   27.3. `var` is not dynamic typing
   27.4. Inferred type may be surprising
   27.5. `var` with literals
   27.6. `var` with diamond operator
   27.7. `var` with anonymous classes
   27.8. `var` with lambdas: why it cannot infer target type alone
   27.9. `var` in enhanced `for`
   27.10. `var` in try-with-resources
   27.11. `var` with `null`
   27.12. Interview puzzles with inferred types
28. `assert` and Other Often-Forgotten Basic Syntax
   28.1. `assert` syntax
   28.2. Assertions disabled by default
   28.3. Enabling assertions
   28.4. Assertion side effects
   28.5. `assert` vs validation
   28.6. `assert` and production code
   28.7. `strictfp` historical context
   28.8. `native`, `transient`, `volatile`, `synchronized` as “basic-looking” modifiers
   28.9. Interview puzzles with disabled assertions
29. Comments, Unicode Escapes, and Lexical Traps
   29.1. Single-line comments
   29.2. Block comments do not nest
   29.3. Javadoc comments
   29.4. Unicode escapes processed early
   29.5. Unicode escapes inside comments
   29.6. Hidden line terminators
   29.7. Escaped identifiers? No, but Unicode characters in identifiers
   29.8. Reserved keywords vs restricted identifiers
   29.9. `var`, `yield`, and contextual restrictions
   29.10. Interview puzzles with code that looks commented out but is not
30. Packages, Imports, and Simple Name Resolution
   30.1. `package` declaration basics
   30.2. Single-type imports
   30.3. On-demand imports
   30.4. Static imports
   30.5. Name conflicts
   30.6. `java.lang` implicit import
   30.7. Same-package visibility
   30.8. Fully qualified names
   30.9. Static import ambiguity
   30.10. Import does not include subpackages
   30.11. Interview puzzles with conflicting names
31. Compilation vs Runtime Traps
   31.1. Code that does not compile
   31.2. Code that compiles but throws
   31.3. Code that compiles and prints surprising output
   31.4. Constant expressions and compiler folding
   31.5. Unreachable code detection
   31.6. Definite assignment analysis limitations
   31.7. Runtime class initialization timing
   31.8. Source compatibility vs bytecode compatibility
   31.9. Interview classification checklist
32. Classic Interview Puzzle Section
   32.1. `int i = 0; i = i++;`
   32.2. `System.out.println(i++ + ++i);`
   32.3. `if (flag = true)`
   32.4. `String s = null; System.out.println(s + "x");`
   32.5. `System.out.println(null + "x");`
   32.6. `Scanner.nextInt()` followed by `nextLine()`
   32.7. `switch` without `break`
   32.8. `default` in the middle of switch
   32.9. `switch` expression without exhaustive branches
   32.10. `yield` vs `return`
   32.11. Labeled `break` from nested loops
   32.12. `finally` overriding return
   32.13. `Arrays.asList()` modification trap
   32.14. Enhanced `for` reassignment trap
   32.15. `var x = 1;` vs `var x = 1L;`
   32.16. Pattern variable scope trap
   32.17. Unicode escape in comment trap
   32.18. Static method call through `null` reference
33. Summary Tables
   33.1. Statement vs expression table
   33.2. Initialization rules table
   33.3. Definite assignment cheat sheet
   33.4. Evaluation order cheat sheet
   33.5. Operator precedence table
   33.6. Assignment and compound assignment table
   33.7. String operation traps table
   33.8. Scanner method behavior table
   33.9. Loop control flow table
   33.10. Traditional switch vs switch expression table
   33.11. `break` vs `continue` vs `return` vs `yield` table
   33.12. Try/finally control-flow table
   33.13. `var` inference examples table
   33.14. Most common interview mistakes checklist
34. Practical Interview Strategy
   34.1. First ask: does it compile?
   34.2. Then ask: what is initialized?
   34.3. Then ask: what is evaluated first?
   34.4. Then ask: is there boxing, unboxing, or promotion?
   34.5. Then ask: is this a statement or expression?
   34.6. Then ask: can control flow skip this assignment?
   34.7. Then ask: can `null` appear?
   34.8. Then ask: is this old switch or new switch?
   34.9. Then ask: is there hidden fall-through?
   34.10. Then ask: is the result compile-time or runtime?

## 1. What Counts as a “Basic Operation” in Java?

This opening section defines the boundary of the chapter: not APIs or frameworks, but the core language mechanics that turn short snippets into interview traps. The key habit is to classify code before solving it.

### 1.1. Expressions vs statements

**Key idea.** An expression produces a value; a statement is executed for its effect. Java has expression statements, but not every statement can appear where a value is required.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 1.2. Declarations vs assignments

**Key idea.** A declaration introduces a name and type; assignment stores a value into an existing variable. A declaration may contain an initializer, but that does not make declaration and assignment the same operation.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 1.3. Initialization vs assignment vs reassignment

**Key idea.** Initialization gives a variable its first usable value; assignment changes a variable after it already exists. Reassignment is forbidden for final variables after they are definitely assigned.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 1.4. Compile-time behavior vs runtime behavior

**Key idea.** Some rules are enforced by the compiler before the program can run; others produce runtime values or exceptions. Interview snippets should always be classified first.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 1.5. Syntax sugar vs actual language rules

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 1.6. Why “simple Java code” often hides complex rules

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 1.7. Interview trap categories: compilation, result, exception, overload, control flow

**Key idea.** Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

## 2. Variable Declaration and Initialization

Initialization questions usually test whether the candidate knows where default values exist, where definite assignment is required, and how initialization order affects observable behavior.

### 2.1. Local variables vs fields vs array elements

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 2.2. Default values: where they exist and where they do not

**Key idea.** Fields and array elements receive default values automatically. Local variables do not; they must be definitely assigned before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

**Example.**

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

### 2.3. Why local variables must be definitely assigned

**Key idea.** The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

**Example.**

```java
int x;
if (Math.random() > 0.5) {
    x = 10;
}
// System.out.println(x); // compile-time error: not definitely assigned
```

### 2.4. Definite assignment and compiler flow analysis

**Key idea.** The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 2.5. Branches, loops, `try/catch`, and definite assignment

**Key idea.** The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 2.6. Final local variables and blank finals

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 2.7. `final` fields and constructor initialization

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 2.8. Initialization order inside a method

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 2.9. Multiple declarations in one statement

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 2.10. Left-to-right evaluation of declarators

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 2.11. Shadowing during initialization

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 2.12. Illegal self-reference examples

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 2.13. Initialization blocks vs constructors

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 2.14. Static initialization order traps

**Key idea.** Static fields and static blocks run in textual order during class initialization. Reading a field before its initializer has completed can expose default values or surprising results.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 2.15. Circular initialization between static fields

**Key idea.** Static fields and static blocks run in textual order during class initialization. Reading a field before its initializer has completed can expose default values or surprising results.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 2.16. Interview puzzles with “variable might not have been initialized”

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 3. Assignment Operators and Expression Results

Assignment in Java is not only a statement-like action; it is also an expression with a value. Many traps come from hidden conversions, chained assignment, and side effects on the left-hand side.

### 3.1. Assignment as an expression

**Key idea.** An assignment expression evaluates to the value assigned, after required conversion. This permits chained assignments and assignment inside boolean conditions.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 3.2. Return value of assignment

**Key idea.** An assignment expression evaluates to the value assigned, after required conversion. This permits chained assignments and assignment inside boolean conditions.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 3.3. Chained assignment: `a = b = c`

**Key idea.** Assignment is right-associative. In `a = b = c`, the rightmost assignment happens first and its value becomes the value assigned further left.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

**Example.**

```java
int a, b, c;
a = b = c = 5; // parsed as a = (b = (c = 5))
```

### 3.4. Assignment associativity

**Key idea.** Assignment is right-associative. In `a = b = c`, the rightmost assignment happens first and its value becomes the value assigned further left.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 3.5. Assigning inside `if`, `while`, and `for`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 3.6. Why `if (x = true)` compiles

**Key idea.** This compiles when `x` is boolean because `x = true` is a boolean expression. It is usually a bug disguised as a valid condition.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 3.7. Why `if (x = 1)` does not compile

**Key idea.** This does not compile because Java conditions require boolean expressions; unlike C/C++, numeric values are not truthy or falsy.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 3.8. Compound assignment and hidden casts

**Key idea.** Compound assignment includes an implicit narrowing conversion equivalent to `E1 = (T)((E1) op (E2))`, while evaluating the left side only once.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

**Example.**

```java
byte b = 1;
b += 1;      // OK: implicit cast after compound assignment
// b = b + 1; // error: b + 1 is int
```

### 3.9. `+=`, `-=`, `*=`, `/=`, `%=` subtleties

**Key idea.** Compound assignment includes an implicit narrowing conversion equivalent to `E1 = (T)((E1) op (E2))`, while evaluating the left side only once.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 3.10. Assignment evaluation order: left side vs right side

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 3.11. Assignment to array elements and side effects

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 3.12. Assignment to fields through `null` references

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 3.13. Assignment in lambdas and effectively-final variables

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 3.14. Interview puzzles with assignment expressions

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 4. Evaluation Order and Side Effects

Java defines a strict left-to-right evaluation order for operands and arguments. This makes many snippets deterministic, but still difficult to reason about when side effects are densely packed.

### 4.1. Java’s left-to-right evaluation guarantee

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 4.2. Operand evaluation before operator execution

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 4.3. Method argument evaluation order

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 4.4. Array index evaluation order

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

**Example.**

```java
int[] a = {10, 20};
int i = 0;
a[i] = i = 1; // left array/index evaluated before right side assignment
```

### 4.5. Field access evaluation order

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 4.6. Post-increment vs pre-increment

**Key idea.** Prefix increment updates first and yields the new value; postfix increment yields the old value and then updates. Both have side effects.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 4.7. `i = i++` and why it is not undefined behavior

**Key idea.** The old value of `i` is produced by `i++`, then the side effect increments `i`, then the assignment writes the old value back. The result is deterministic.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

**Example.**

```java
int i = 0;
i = i++;
System.out.println(i); // 0
```

### 4.8. Nested increments: readable vs legal

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 4.9. Side effects in method calls

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 4.10. Side effects in string concatenation

**Key idea.** String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 4.11. Side effects in ternary expressions

**Key idea.** The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 4.12. Side effects in `switch` selectors

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 4.13. Interview puzzles with `i++`, `++i`, and assignments

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 5. Arithmetic and Logical Operations Beyond Basic Types

This section complements primitive-type theory by focusing on operators themselves: precedence, associativity, short-circuiting, bitwise operations, shifts, division, and side effects.

### 5.1. Operator precedence vs evaluation order

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 5.2. Associativity traps

**Key idea.** Assignment is right-associative. In `a = b = c`, the rightmost assignment happens first and its value becomes the value assigned further left.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.3. Unary plus and minus

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.4. Prefix and postfix increment/decrement

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 5.5. Short-circuit operators: `&&`, `||`

**Key idea.** `&&` and `||` evaluate the right operand only when needed. This is often used for null guards and to avoid expensive or unsafe calls.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
String s = null;
if (s != null && s.length() > 0) {
    System.out.println(s);
}
```

### 5.6. Non-short-circuit boolean operators: `&`, `|`, `^`

**Key idea.** `&&` and `||` evaluate the right operand only when needed. This is often used for null guards and to avoid expensive or unsafe calls.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.7. Bitwise operations used with booleans

**Key idea.** `&`, `|`, and `^` can operate on booleans without short-circuiting. Both operands are evaluated, so side effects and exceptions may occur.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.8. Boolean XOR

**Key idea.** `&`, `|`, and `^` can operate on booleans without short-circuiting. Both operands are evaluated, so side effects and exceptions may occur.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.9. Shift operators: `<<`, `>>`, `>>>`

**Key idea.** For `int`, only the low five bits of the shift count are used; for `long`, the low six bits are used. Negative shift counts are also masked, not rejected.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.10. Shift distance masking

**Key idea.** For `int`, only the low five bits of the shift count are used; for `long`, the low six bits are used. Negative shift counts are also masked, not rejected.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
System.out.println(1 << 32); // 1, because int shift count uses only low 5 bits
```

### 5.11. Negative shift counts

**Key idea.** For `int`, only the low five bits of the shift count are used; for `long`, the low six bits are used. Negative shift counts are also masked, not rejected.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.12. `~x` and two’s complement intuition

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 5.13. Integer division and remainder

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.14. Evaluation of division by zero

**Key idea.** Integer division by zero throws `ArithmeticException`. Floating-point division by zero produces signed infinity or NaN according to floating-point rules.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 5.15. Interview puzzles with precedence and side effects

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 6. Conditional Operator `?:`

The ternary operator is compact but type-heavy. Interviewers use it to combine branch laziness, type inference, numeric promotion, boxing, unboxing, and null-handling in one line.

### 6.1. The conditional operator as an expression

**Key idea.** The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 6.2. Type inference in ternary expressions

**Key idea.** The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 6.3. Numeric ternary promotion

**Key idea.** The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 6.4. Boxing and unboxing in ternary expressions

**Key idea.** The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 6.5. `null` in ternary expressions

**Key idea.** The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

**Example.**

```java
Integer x = true ? null : 1; // OK: x becomes null
// int y = true ? null : 1; // NullPointerException at runtime if selected branch unboxes null
```

### 6.6. Ternary expressions and overload resolution

**Key idea.** The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 6.7. Nested ternary readability traps

**Key idea.** The conditional operator evaluates only the selected branch, but its overall type is determined by detailed compile-time rules involving numeric promotion, boxing, and null.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 6.8. Definite assignment through ternary expressions

**Key idea.** The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 6.9. Lazy evaluation of selected branch only

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 6.10. Interview puzzles with `Boolean`, `Integer`, and `null`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 7. Basic String Operations

String operations look friendly, but they hide compile-time concatenation, runtime concatenation, string conversion rules, text block processing, and performance implications.

### 7.1. String literals and string pool recap

**Key idea.** String literals are interned. The same literal text in the same class-loader context usually refers to the same pooled String object.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### 7.2. String concatenation as an operation

**Key idea.** String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### 7.3. Compile-time concatenation vs runtime concatenation

**Key idea.** Some rules are enforced by the compiler before the program can run; others produce runtime values or exceptions. Interview snippets should always be classified first.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### 7.4. `+` with strings and non-strings

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 7.5. Left-to-right concatenation traps

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

**Example.**

```java
System.out.println(1 + 2 + "a"); // 3a
System.out.println("a" + 1 + 2); // a12
```

### 7.6. `null` in string concatenation

**Key idea.** String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 7.7. `String.valueOf()` behavior

**Key idea.** String conversion is based on `String.valueOf`. For null references it produces the text `"null"`, not a NullPointerException.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 7.8. Concatenation and side effects

**Key idea.** String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 7.9. Concatenation inside loops

**Key idea.** String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### 7.10. `StringBuilder` and compiler-generated concatenation

**Key idea.** String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 7.11. `invokedynamic` string concatenation in modern Java

**Key idea.** String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 7.12. `String.format()` vs concatenation

**Key idea.** String concatenation converts operands using string conversion. Constant concatenations may be folded at compile time; non-constant concatenations are runtime operations.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 7.13. `String.join()` and `Collectors.joining()`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 7.14. Text blocks and indentation rules

**Key idea.** Text blocks have their own indentation and escape-processing rules. They are source-level syntax for multiline string literals, not a different runtime type.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### 7.15. Escape sequences in normal strings and text blocks

**Key idea.** Text blocks have their own indentation and escape-processing rules. They are source-level syntax for multiline string literals, not a different runtime type.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### 7.16. `stripIndent()` and `translateEscapes()`

**Key idea.** Text blocks have their own indentation and escape-processing rules. They are source-level syntax for multiline string literals, not a different runtime type.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 7.17. Interview puzzles with `"a" + 1 + 2`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 8. String Comparison and Searching

String search and comparison questions often test whether the candidate separates identity, equality, lexicographic comparison, regex-based APIs, and locale or Unicode expectations.

### 8.1. `==` vs `.equals()`

**Key idea.** `==` compares references for objects and values for primitives. `String.equals` compares character content.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 8.2. `.equalsIgnoreCase()` caveats

**Key idea.** Case-insensitive comparison is not a full locale-aware natural-language comparison. It is useful but should not be treated as universal text collation.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 8.3. `compareTo()` and lexicographic ordering

**Key idea.** `compareTo` provides lexicographic ordering based on Unicode values, not locale-specific alphabetical order.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 8.4. Unicode and locale-insensitive comparison

**Key idea.** Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### 8.5. `contains()`, `startsWith()`, `endsWith()`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 8.6. `indexOf()` and `lastIndexOf()`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 8.7. Empty string edge cases

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether the candidate distinguishes literal operations, regex operations, interning, and null string conversion.

### 8.8. `split()` and regex surprises

**Key idea.** Several String methods use regular expressions, not literal text. `matches` attempts to match the whole input, not merely find a substring.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

**Example.**

```java
System.out.println(Arrays.toString("a.b".split(".")));  // [] or surprising result
System.out.println(Arrays.toString("a.b".split("\\."))); // [a, b]
```

### 8.9. `replace()` vs `replaceAll()`

**Key idea.** Several String methods use regular expressions, not literal text. `matches` attempts to match the whole input, not merely find a substring.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 8.10. `matches()` matches the whole string

**Key idea.** Several String methods use regular expressions, not literal text. `matches` attempts to match the whole input, not merely find a substring.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 8.11. Interview puzzles with regex metacharacters

**Key idea.** Several String methods use regular expressions, not literal text. `matches` attempts to match the whole input, not merely find a substring.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 9. Basic Console Output

Output questions usually test object-to-string conversion, formatting, buffering, stream interleaving, and the difference between arrays as objects and array-content formatting helpers.

### 9.1. `System.out.print()` vs `println()` vs `printf()`

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 9.2. `System.out` as a `PrintStream`

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 9.3. Auto-flushing behavior

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 9.4. Formatting with `%s`, `%d`, `%f`, `%n`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 9.5. Locale-sensitive formatting

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 9.6. `System.err` vs `System.out`

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 9.7. Interleaving output from multiple streams

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 9.8. Output buffering traps

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 9.9. Printing arrays: `arr.toString()` vs `Arrays.toString()`

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
int[] a = {1, 2, 3};
System.out.println(a);                  // class name + hash-like text
System.out.println(Arrays.toString(a)); // [1, 2, 3]
```

### 9.10. Printing multidimensional arrays

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 9.11. Interview puzzles with output order and side effects

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 10. Basic Console Input

Console input questions are dominated by token-vs-line semantics, Scanner traps, encodings, resource lifetime, and choosing the right input abstraction for the task.

### 10.1. `System.in` as an `InputStream`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 10.2. `Scanner` basics

**Key idea.** Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 10.3. `BufferedReader` basics

**Key idea.** BufferedReader is line-oriented and often faster/predictable for bulk text input, but parsing must be done manually.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 10.4. `Console` and why it may be `null`

**Key idea.** `System.console()` may be null in IDEs, redirected processes, or non-interactive environments. Code must handle that case.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 10.5. `Scanner.next()` vs `nextLine()`

**Key idea.** Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 10.6. The famous `nextInt()` / `nextLine()` trap

**Key idea.** Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

**Example.**

```java
Scanner sc = new Scanner(System.in);
int age = sc.nextInt();
String name = sc.nextLine(); // consumes the rest of the current line, often empty
```

### 10.7. Token-based input vs line-based input

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

### 10.8. Parsing manually from strings

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

### 10.9. Handling invalid input

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

### 10.10. `InputMismatchException`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 10.11. Character encoding considerations

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

### 10.12. Closing `Scanner` and accidentally closing `System.in`

**Key idea.** Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

### 10.13. Reading passwords with `Console.readPassword()`

**Key idea.** `System.console()` may be null in IDEs, redirected processes, or non-interactive environments. Code must handle that case.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

### 10.14. Competitive-programming style fast input

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

### 10.15. Interview puzzles with mixed scanner methods

**Key idea.** Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 11. Blocks, Scope, and Shadowing

Scope questions ask where a name is valid, which declaration a name resolves to, and why shadowing is legal in some places but illegal or misleading in others.

### 11.1. Blocks as scope boundaries

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 11.2. Local variable scope

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 11.3. Field hiding by local variables

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

**Example.**

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

### 11.4. Parameter shadowing

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 11.5. Shadowing vs overriding

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 11.6. Shadowing inside loops

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 11.7. Shadowing inside lambdas

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 11.8. Why some redeclarations are illegal

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 11.9. Scope of variables declared in `for` headers

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 11.10. Scope of variables declared in `try-with-resources`

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 11.11. Pattern variable scope

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### 11.12. Interview puzzles with same-name variables

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 12. `if`, `else`, and Boolean Control Flow

Conditional-flow questions test boolean-only conditions, dangling else, short-circuiting, constant conditions, reachability, and null-unboxing through Boolean wrappers.

### 12.1. `if` requires boolean, not numeric

**Key idea.** Java requires a boolean condition. There is no implicit conversion from int, reference, or pointer-like values to boolean.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 12.2. Dangling `else`

**Key idea.** An `else` binds to the nearest unmatched `if`. Braces are the safest way to express intent.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
if (a)
    if (b) f();
    else g(); // belongs to the nearest if: if (b)
```

### 12.3. Assignment inside conditions

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 12.4. Short-circuiting in conditions

**Key idea.** `&&` and `||` evaluate the right operand only when needed. This is often used for null guards and to avoid expensive or unsafe calls.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 12.5. Side effects inside conditions

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 12.6. Constant conditions

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 12.7. Unreachable statement rules

**Key idea.** The compiler rejects certain statements that are provably unreachable. Some constructs, such as non-final conditions, may avoid compile-time reachability conclusions.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 12.8. `if (Boolean)` and unboxing `NullPointerException`

**Key idea.** Java requires a boolean condition. There is no implicit conversion from int, reference, or pointer-like values to boolean.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 12.9. Guard clauses and definite assignment

**Key idea.** The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 12.10. Interview puzzles with nested `if/else`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 13. Loops: `while`, `do-while`, and `for`

Loop questions combine condition evaluation, update timing, reachability, definite assignment, off-by-one errors, and mutations of loop counters.

### 13.1. `while` condition evaluation

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 13.2. `do-while` executes at least once

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 13.3. Classic `for` loop execution order

**Key idea.** A classic for-loop runs initialization once, then condition, body, update, and condition again until the condition fails or control jumps out.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

**Example.**

```java
for (init(); condition(); update()) {
    body();
}
// init once, then condition -> body -> update -> condition ...
```

### 13.4. Multiple initialization and update expressions

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 13.5. Empty loop components

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 13.6. Infinite loops and reachability

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 13.7. Loop variables and scope

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 13.8. Mutating loop variables inside body

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 13.9. Floating-point loop counters

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 13.10. Off-by-one traps

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 13.11. `continue` and update expression execution

**Key idea.** In a classic for-loop, `continue` jumps to the update expression before the next condition check. In while-loops, it jumps directly to condition evaluation.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 13.12. `break` and skipped update expressions

**Key idea.** `break` exits the loop immediately and skips the for-loop update expression.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 13.13. Definite assignment through loops

**Key idea.** The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 13.14. Interview puzzles with loop control flow

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 14. Enhanced `for` Loop

Enhanced for-loops are simple only at the surface. They hide array/Iterable mechanics, copy of the element reference/value, and mutation rules for collections.

### 14.1. Enhanced `for` over arrays

**Key idea.** Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 14.2. Enhanced `for` over `Iterable`

**Key idea.** Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 14.3. Desugaring conceptually

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 14.4. Why assigning to loop variable does not modify array element

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

**Example.**

```java
int[] xs = {1, 2, 3};
for (int x : xs) {
    x = 99;
}
System.out.println(Arrays.toString(xs)); // [1, 2, 3]
```

### 14.5. Mutating objects through enhanced `for`

**Key idea.** Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 14.6. Concurrent modification traps

**Key idea.** Modifying many collections structurally while iterating with enhanced for can trigger fail-fast behavior. Use Iterator.remove or collect changes separately.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 14.7. Enhanced `for` and primitive arrays

**Key idea.** Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 14.8. Enhanced `for` and wrapper arrays

**Key idea.** Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 14.9. `null` array or iterable behavior

**Key idea.** Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 14.10. Removing elements safely

**Key idea.** Modifying many collections structurally while iterating with enhanced for can trigger fail-fast behavior. Use Iterator.remove or collect changes separately.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 14.11. Interview puzzles with collection mutation

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 15. `break`, `continue`, Labels, and Reachability

Labels and jumps are rare in production Java, so interview questions use them to test exact control-flow knowledge and reachability analysis.

### 15.1. Unlabeled `break`

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.2. Unlabeled `continue`

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.3. Labeled `break`

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
outer:
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        break outer;
    }
}
```

### 15.4. Labeled `continue`

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.5. Labels are not `goto`

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.6. Legal places for labels

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.7. Breaking out of nested loops

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.8. Continuing outer loops

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.9. Labels and blocks

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.10. Unreachable code after `break`

**Key idea.** The compiler rejects certain statements that are provably unreachable. Some constructs, such as non-final conditions, may avoid compile-time reachability conclusions.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 15.11. `break`/`continue` inside `try/finally`

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 15.12. Interview puzzles with labels

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 16. Traditional `switch` Statement

Traditional switch remains a common interview topic because fall-through, default placement, selector restrictions, case constants, and scope rules are easy to misunderstand.

### 16.1. `switch` selector types

**Key idea.** Switch selector types depend on the switch form and Java version. Traditional switches support integral-compatible types, enums, String, and wrappers through unboxing.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 16.2. `case` labels and constant expressions

**Key idea.** Traditional case labels must be compatible compile-time constants or enum constants. Duplicate effective values are illegal.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 16.3. Fall-through behavior

**Key idea.** Traditional colon-style switch can continue into following cases unless control flow exits. This is intentional but error-prone.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

**Example.**

```java
switch (x) {
    case 1: System.out.println("one");
    case 2: System.out.println("two");
}
```

### 16.4. Missing `break` bugs

**Key idea.** Traditional colon-style switch can continue into following cases unless control flow exits. This is intentional but error-prone.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 16.5. `default` position does not have to be last

**Key idea.** The default label can appear anywhere. Execution starts at the matching label or default and then follows normal fall-through rules.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 16.6. Duplicate case labels

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 16.7. `switch` with `char`, `byte`, `short`, `int`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 16.8. `switch` with `String`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 16.9. `switch` with enums

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 16.10. `switch` with wrapper types and unboxing

**Key idea.** Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 16.11. `null` selector behavior in older switch forms

**Key idea.** Switch selector types depend on the switch form and Java version. Traditional switches support integral-compatible types, enums, String, and wrappers through unboxing.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 16.12. Variable scope inside switch blocks

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 16.13. Declaring variables in cases

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 16.14. Braces around case blocks

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 16.15. Interview puzzles with fall-through

**Key idea.** Traditional colon-style switch can continue into following cases unless control flow exits. This is intentional but error-prone.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 17. Modern `switch` Expressions

Modern switch questions test the distinction between statements and expressions, arrow labels, exhaustiveness, expression typing, and the absence of accidental fall-through.

### 17.1. `switch` as a statement vs expression

**Key idea.** An expression produces a value; a statement is executed for its effect. Java has expression statements, but not every statement can appear where a value is required.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 17.2. Arrow labels: `case X ->`

**Key idea.** Arrow switch labels do not fall through. The right side is an expression, block, or throw statement depending on context.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

**Example.**

```java
String text = switch (n) {
    case 1 -> "one";
    case 2 -> "two";
    default -> "many";
};
```

### 17.3. No accidental fall-through with arrow labels

**Key idea.** Arrow switch labels do not fall through. The right side is an expression, block, or throw statement depending on context.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 17.4. Multiple case constants

**Key idea.** Traditional case labels must be compatible compile-time constants or enum constants. Duplicate effective values are illegal.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 17.5. `switch` expression must produce a value

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 17.6. Exhaustiveness requirement

**Key idea.** A switch expression must cover all possible selector values or provide default. Pattern and enum switches may be exhaustive without default in some cases.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 17.7. `default` and exhaustive enum switches

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 17.8. `switch` expression type inference

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 17.9. Throwing from switch branches

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 17.10. Blocks inside switch expression arms

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 17.11. Mixing colon and arrow styles

**Key idea.** Arrow switch labels do not fall through. The right side is an expression, block, or throw statement depending on context.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 17.12. Interview puzzles with switch expressions

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 18. `yield` in Switch Expressions

Yield exists to return a value from a switch expression block. The key distinction is that it exits the switch expression, not the surrounding method.

### 18.1. Why `yield` exists

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 18.2. `yield` vs `return`

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 18.3. `yield` vs `break`

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 18.4. `yield` inside switch expression blocks

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

**Example.**

```java
String text = switch (n) {
    case 1 -> {
        System.out.println("special");
        yield "one";
    }
    default -> "many";
};
```

### 18.5. `yield` and block scoping

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 18.6. `yield` cannot be used everywhere

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 18.7. `yield` as a restricted identifier

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 18.8. Yielding values of compatible types

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 18.9. Throwing instead of yielding

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Check whether they separate traditional switch statements from modern switch expressions and know which forms require values or allow fall-through.

### 18.10. `yield` in nested switches

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 18.11. Interview puzzles with `yield` and control flow

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 19. Pattern Matching in Basic Control Flow

Pattern matching introduces flow-sensitive variables into ordinary control flow. The main interview risks are scope, dominance, null, guards, and exhaustiveness.

### 19.1. `instanceof` pattern matching

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 19.2. Pattern variable scope

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### 19.3. Flow-sensitive scoping

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 19.4. Pattern variables with `&&`

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

**Example.**

```java
if (obj instanceof String s && s.length() > 3) {
    System.out.println(s.toUpperCase());
}
```

### 19.5. Why pattern variables may not exist after `||`

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### 19.6. Negated pattern checks

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### 19.7. Pattern matching in `switch`

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### 19.8. Type patterns in switch labels

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### 19.9. Guarded patterns / `when` clauses

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### 19.10. `null` handling in pattern switch

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 19.11. Dominance of switch labels

**Key idea.** A broader pattern before a narrower pattern can dominate it, making the later label unreachable or illegal.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 19.12. Exhaustiveness with sealed classes

**Key idea.** A switch expression must cover all possible selector values or provide default. Pattern and enum switches may be exhaustive without default in some cases.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 19.13. Interview puzzles with pattern variable visibility

**Key idea.** Pattern matching combines a test with variable binding. The pattern variable is in scope only where the compiler knows the match succeeded.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 20. `return`, `throw`, and `finally`

Return and exception questions often become tricky only when finally is involved. The core issue is when expressions are evaluated and how finally can override or mutate outcomes.

### 20.1. `return` from methods

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 20.2. Returning expressions and evaluation order

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 20.3. Returning from `try`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 20.4. `finally` after `return`

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 20.5. `finally` overriding return values

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

**Example.**

```java
static int f() {
    try { return 1; }
    finally { return 2; }
}
```

### 20.6. Mutating returned objects in `finally`

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask which action wins when return, throw, and finally interact.

### 20.7. `throw` and unreachable code

**Key idea.** The compiler rejects certain statements that are provably unreachable. Some constructs, such as non-final conditions, may avoid compile-time reachability conclusions.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 20.8. Checked vs unchecked exceptions in basic control flow

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 20.9. `return` inside `catch`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 20.10. `return` inside `finally`: why it is dangerous

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 20.11. `System.exit()` and finally blocks

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 20.12. Interview puzzles with return values and finally

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 21. `try`, `catch`, and Try-With-Resources Basics

Exception-handling basics include catch selection, resource-closing order, suppressed exceptions, and the differences between finally and try-with-resources.

### 21.1. Basic `try/catch/finally` flow

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask which action wins when return, throw, and finally interact.

### 21.2. Catch block order

**Key idea.** More specific exceptions must be caught before broader supertypes. Otherwise the specific catch block is unreachable.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 21.3. Multi-catch

**Key idea.** Multi-catch groups unrelated alternatives. The exception parameter is effectively final and has a common supertype.

**Interview angle.** Ask which action wins when return, throw, and finally interact.

### 21.4. Effectively-final exception parameters

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask which action wins when return, throw, and finally interact.

### 21.5. Exception variable scope

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Ask which action wins when return, throw, and finally interact.

### 21.6. Try-with-resources syntax

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask which action wins when return, throw, and finally interact.

### 21.7. Resource closing order

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 21.8. Suppressed exceptions

**Key idea.** Try-with-resources preserves exceptions thrown during resource closing as suppressed exceptions when another exception is already primary.

**Interview angle.** Ask which action wins when return, throw, and finally interact.

**Example.**

```java
try (var r = resource()) {
    throw new RuntimeException("body");
} // exception from close() may be suppressed under the body exception
```

### 21.9. `AutoCloseable` vs `Closeable`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 21.10. Exceptions during initialization of resources

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 21.11. `finally` vs try-with-resources

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 21.12. Interview puzzles with suppressed exceptions

**Key idea.** Try-with-resources preserves exceptions thrown during resource closing as suppressed exceptions when another exception is already primary.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 22. Method Calls as Basic Operations

Method call questions test evaluation order, pass-by-value semantics, overload resolution, varargs, static dispatch, and calls through null references.

### 22.1. Argument evaluation order

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 22.2. Pass-by-value in Java

**Key idea.** Java always passes arguments by value. For objects, the value is a copy of the reference, so object state can be mutated but the caller’s variable cannot be rebound.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
static void change(StringBuilder sb) {
    sb.append("x");     // mutates object
    sb = new StringBuilder("new"); // reassigns local copy only
}
```

### 22.3. Passing object references by value

**Key idea.** Java always passes arguments by value. For objects, the value is a copy of the reference, so object state can be mutated but the caller’s variable cannot be rebound.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 22.4. Mutating object state vs reassigning parameters

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 22.5. Overload resolution basics

**Key idea.** Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 22.6. Widening vs boxing vs varargs

**Key idea.** Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 22.7. Static method hiding vs instance method overriding

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 22.8. Calling methods through `null` references

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 22.9. Static method call through object reference

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 22.10. Varargs as arrays

**Key idea.** Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 22.11. Ambiguous overloads

**Key idea.** Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 22.12. Interview puzzles with method calls and parameters

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 23. Object Creation and Initialization Expressions

Object creation questions test the exact sequence from argument evaluation through superclass construction, field initialization, initializer blocks, and constructor bodies.

### 23.1. `new` as an expression

**Key idea.** Object creation is an expression: it evaluates constructor arguments, allocates, initializes superclass state, initializes fields/blocks, and runs the constructor body.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 23.2. Constructor argument evaluation order

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 23.3. Field initialization before constructor body

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 23.4. Instance initializer blocks

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 23.5. Static initializer blocks

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 23.6. Constructor chaining with `this()`

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 23.7. Superclass initialization order

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 23.8. Dynamic dispatch during construction

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
class Base { Base() { f(); } void f() {} }
class Child extends Base { int x = 10; void f() { System.out.println(x); } }
// Child constructor may call overridden f before Child fields are initialized.
```

### 23.9. Anonymous classes

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 23.10. Double-brace initialization trap

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 23.11. Records and compact constructors

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 23.12. Interview puzzles with constructor order

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 24. Lambdas and Basic Functional Operations

Lambda basics test target typing, capture rules, effective finality, return semantics, this-binding, method references, and overload ambiguity.

### 24.1. Lambda syntax forms

**Key idea.** Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 24.2. Expression lambdas vs block lambdas

**Key idea.** Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 24.3. `return` inside block lambdas

**Key idea.** Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 24.4. Effectively-final captured variables

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

**Example.**

```java
int x = 10;
Runnable r = () -> System.out.println(x);
// x++; // would make x not effectively final
```

### 24.5. Capturing references vs values

**Key idea.** Captured local variables must be final or effectively final. The lambda captures a value/reference, not a mutable local variable slot.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 24.6. `this` inside lambda

**Key idea.** Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 24.7. Lambda vs anonymous class

**Key idea.** Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 24.8. Method references

**Key idea.** Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 24.9. Overload ambiguity with lambdas

**Key idea.** Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 24.10. Checked exceptions in lambdas

**Key idea.** Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 24.11. Interview puzzles with capture and mutation

**Key idea.** Captured local variables must be final or effectively final. The lambda captures a value/reference, not a mutable local variable slot.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 25. Basic Operations with Arrays

Array operations combine object identity, bounds checks, null checks, copying semantics, shallow/deep equality, and evaluation order.

### 25.1. Array creation expressions

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 25.2. Array initializer syntax

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 25.3. Default values in arrays

**Key idea.** Fields and array elements receive default values automatically. Local variables do not; they must be definitely assigned before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 25.4. Accessing array elements

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 25.5. Array index evaluation order

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

**Example.**

```java
int[] a = {10, 20};
int i = 0;
a[i] = i = 1; // left array/index evaluated before right side assignment
```

### 25.6. `ArrayIndexOutOfBoundsException`

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 25.7. `NullPointerException` with arrays

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 25.8. Array assignment and reference copying

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 25.9. Shallow copy vs deep copy

**Key idea.** A shallow copy duplicates the container but not referenced objects. A deep copy also duplicates reachable mutable objects according to the chosen depth.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 25.10. `clone()` on arrays

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 25.11. `System.arraycopy()`

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 25.12. `Arrays.copyOf()`

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 25.13. `Arrays.equals()` vs `==`

**Key idea.** `==` compares references for objects and values for primitives. `String.equals` compares character content.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 25.14. `Arrays.deepEquals()`

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 25.15. Interview puzzles with array mutation

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 26. Basic Operations with Collections

Collection basics are full of practical traps: fixed-size views, immutable factories, equality-based lookup, fail-fast iterators, and mutable keys.

### 26.1. `List.of()` immutability

**Key idea.** `List.of` creates an unmodifiable list and rejects null elements. It is not the same as an ArrayList.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 26.2. `Arrays.asList()` fixed-size list trap

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

**Example.**

```java
List<String> xs = Arrays.asList("a", "b");
xs.set(0, "x"); // OK
// xs.add("c"); // UnsupportedOperationException
```

### 26.3. `new ArrayList<>(Arrays.asList(...))`

**Key idea.** Object creation is an expression: it evaluates constructor arguments, allocates, initializes superclass state, initializes fields/blocks, and runs the constructor body.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 26.4. `contains()` and `.equals()`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 26.5. Removing while iterating

**Key idea.** Modifying many collections structurally while iterating with enhanced for can trigger fail-fast behavior. Use Iterator.remove or collect changes separately.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 26.6. Iterator removal

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 26.7. Fail-fast behavior

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 26.8. `ConcurrentModificationException` misconceptions

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 26.9. `Map.get()` and missing keys

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 26.10. `Map.getOrDefault()`

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 26.11. `computeIfAbsent()` basics and traps

**Key idea.** `computeIfAbsent` computes only when there is no mapping or the existing mapping is null, but the mapping function must avoid unsafe side effects on the same map.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 26.12. Mutable keys in maps

**Key idea.** Mutating fields used by `equals`/`hashCode` after inserting a key into a hash map can make the entry unreachable by lookup.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 26.13. Interview puzzles with lists and maps

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 27. `var` and Local Variable Type Inference

var questions test whether the candidate understands that inference happens at compile time and produces a fixed static type, not a dynamic variable.

### 27.1. What `var` is and is not

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Expect compile-time reasoning: target type, inferred type, or selected overload.

### 27.2. `var` requires initializer

**Key idea.** Construction order matters: superclass initialization precedes subclass field initializers and constructor body. Dynamic dispatch during construction is dangerous.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 27.3. `var` is not dynamic typing

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 27.4. Inferred type may be surprising

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 27.5. `var` with literals

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

**Example.**

```java
var a = 1;  // int
var b = 1L; // long
var c = 1.0; // double
```

### 27.6. `var` with diamond operator

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 27.7. `var` with anonymous classes

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 27.8. `var` with lambdas: why it cannot infer target type alone

**Key idea.** Lambdas are target-typed expressions. Their meaning depends on the functional interface expected by the surrounding context.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 27.9. `var` in enhanced `for`

**Key idea.** Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 27.10. `var` in try-with-resources

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 27.11. `var` with `null`

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 27.12. Interview puzzles with inferred types

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 28. `assert` and Other Often-Forgotten Basic Syntax

Forgotten syntax questions test features that exist in the language but are often disabled, rarely used, or misunderstood as runtime validation tools.

### 28.1. `assert` syntax

**Key idea.** Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 28.2. Assertions disabled by default

**Key idea.** Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

**Example.**

```java
assert expensiveCheck(); // may not run unless assertions are enabled with -ea
```

### 28.3. Enabling assertions

**Key idea.** Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 28.4. Assertion side effects

**Key idea.** Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 28.5. `assert` vs validation

**Key idea.** Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 28.6. `assert` and production code

**Key idea.** Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 28.7. `strictfp` historical context

**Key idea.** `strictfp` historically forced strict floating-point semantics; modern Java has made floating-point behavior more consistently strict, reducing its practical importance.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 28.8. `native`, `transient`, `volatile`, `synchronized` as “basic-looking” modifiers

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 28.9. Interview puzzles with disabled assertions

**Key idea.** Assertions are a development-time checking mechanism and are disabled by default unless enabled with JVM flags such as `-ea`.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 29. Comments, Unicode Escapes, and Lexical Traps

Lexical traps are intentionally obscure. The most important one is that Unicode escapes are processed before tokenization, even inside comments and strings.

### 29.1. Single-line comments

**Key idea.** Comments are removed during lexical processing, but Unicode escapes may be processed before the comment is recognized. Block comments do not nest.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 29.2. Block comments do not nest

**Key idea.** Comments are removed during lexical processing, but Unicode escapes may be processed before the comment is recognized. Block comments do not nest.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 29.3. Javadoc comments

**Key idea.** Comments are removed during lexical processing, but Unicode escapes may be processed before the comment is recognized. Block comments do not nest.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 29.4. Unicode escapes processed early

**Key idea.** Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
// The following can create a real line break before lexical analysis:
// \u000a System.out.println("surprise");
```

### 29.5. Unicode escapes inside comments

**Key idea.** Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 29.6. Hidden line terminators

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 29.7. Escaped identifiers? No, but Unicode characters in identifiers

**Key idea.** Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 29.8. Reserved keywords vs restricted identifiers

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 29.9. `var`, `yield`, and contextual restrictions

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 29.10. Interview puzzles with code that looks commented out but is not

**Key idea.** Comments are removed during lexical processing, but Unicode escapes may be processed before the comment is recognized. Block comments do not nest.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 30. Packages, Imports, and Simple Name Resolution

Name-resolution questions test whether imports are understood as compile-time shortcuts rather than runtime loading mechanisms, and how ambiguity is resolved.

### 30.1. `package` declaration basics

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 30.2. Single-type imports

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 30.3. On-demand imports

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 30.4. Static imports

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
import static java.lang.Math.*;
System.out.println(sqrt(16));
```

### 30.5. Name conflicts

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 30.6. `java.lang` implicit import

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 30.7. Same-package visibility

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 30.8. Fully qualified names

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 30.9. Static import ambiguity

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 30.10. Import does not include subpackages

**Key idea.** Imports affect source-name resolution only. They do not load classes, import subpackages, or override declarations in the current package.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 30.11. Interview puzzles with conflicting names

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

## 31. Compilation vs Runtime Traps

A good interview answer first classifies a snippet: does it fail compilation, fail at runtime, or produce a surprising value? This section formalizes that workflow.

### 31.1. Code that does not compile

**Key idea.** A compile-time error means the program is not valid Java source for the selected language level; no runtime answer exists.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 31.2. Code that compiles but throws

**Key idea.** Some snippets pass static checks but fail during execution because of null, bounds, arithmetic, class initialization, or resource errors.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 31.3. Code that compiles and prints surprising output

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 31.4. Constant expressions and compiler folding

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

**Example.**

```java
final int x = 1 + 2; // compile-time constant
byte b = x;          // OK because x is constant and fits in byte
```

### 31.5. Unreachable code detection

**Key idea.** The compiler rejects certain statements that are provably unreachable. Some constructs, such as non-final conditions, may avoid compile-time reachability conclusions.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 31.6. Definite assignment analysis limitations

**Key idea.** The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 31.7. Runtime class initialization timing

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 31.8. Source compatibility vs bytecode compatibility

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 31.9. Interview classification checklist

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

## 32. Classic Interview Puzzle Section

This section collects canonical snippets. Each puzzle is less about memorizing the output and more about applying the chapter’s classification strategy.

### 32.1. `int i = 0; i = i++;`

**Key idea.** The old value of `i` is produced by `i++`, then the side effect increments `i`, then the assignment writes the old value back. The result is deterministic.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.2. `System.out.println(i++ + ++i);`

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.3. `if (flag = true)`

**Key idea.** This is a canonical interview snippet. The answer should be derived by checking compilation, evaluation order, conversions, and control flow rather than by guessing.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.4. `String s = null; System.out.println(s + "x");`

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.5. `System.out.println(null + "x");`

**Key idea.** Console output is performed through PrintStream. Formatting, flushing, and conversion rules affect what appears and when.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.6. `Scanner.nextInt()` followed by `nextLine()`

**Key idea.** Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.7. `switch` without `break`

**Key idea.** This is a canonical interview snippet. The answer should be derived by checking compilation, evaluation order, conversions, and control flow rather than by guessing.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.8. `default` in the middle of switch

**Key idea.** This is a canonical interview snippet. The answer should be derived by checking compilation, evaluation order, conversions, and control flow rather than by guessing.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.9. `switch` expression without exhaustive branches

**Key idea.** This is a canonical interview snippet. The answer should be derived by checking compilation, evaluation order, conversions, and control flow rather than by guessing.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.10. `yield` vs `return`

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.11. Labeled `break` from nested loops

**Key idea.** Labels name statements so break/continue can target an outer statement. They are structured control flow, not arbitrary goto.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 32.12. `finally` overriding return

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.13. `Arrays.asList()` modification trap

**Key idea.** Arrays are objects with fixed length and runtime bounds/null checks. Array variables hold references, so assignment copies the reference, not the contents.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.14. Enhanced `for` reassignment trap

**Key idea.** Enhanced for-loops iterate over array elements or Iterable values. The loop variable receives a copy of the element value or reference.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 32.15. `var x = 1;` vs `var x = 1L;`

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 32.16. Pattern variable scope trap

**Key idea.** A name resolves to the nearest applicable declaration in scope. Shadowing hides another declaration; it does not override behavior dynamically.

**Interview angle.** Check whether they reason about flow-sensitive scope and dominance rather than treating pattern variables as ordinary locals.

### 32.17. Unicode escape in comment trap

**Key idea.** Unicode escapes are translated before lexical analysis, so they can affect comments, string literals, and line structure in surprising ways.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 32.18. Static method call through `null` reference

**Key idea.** A static method call written through a null reference can still work because the method is resolved from the compile-time type, though the style is misleading.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

## 33. Summary Tables

The tables act as quick recall material. They compress rules into checklists suitable for interview preparation and review.

### 33.1. Statement vs expression table

**Key idea.** An expression produces a value; a statement is executed for its effect. Java has expression statements, but not every statement can appear where a value is required.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 33.2. Initialization rules table

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 33.3. Definite assignment cheat sheet

**Key idea.** The compiler performs conservative flow analysis. It does not execute the code, but checks whether every legal control-flow path assigns a variable before use.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 33.4. Evaluation order cheat sheet

**Key idea.** Java specifies left-to-right evaluation for operands and arguments. Side effects happen in a defined order, which avoids C-style undefined behavior for these expressions.

**Interview angle.** Require step-by-step evaluation, not a memorized final number.

### 33.5. Operator precedence table

**Key idea.** Precedence controls grouping, not necessarily the chronological order of evaluation. Parentheses change grouping and often make interview answers safer.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 33.6. Assignment and compound assignment table

**Key idea.** Compound assignment includes an implicit narrowing conversion equivalent to `E1 = (T)((E1) op (E2))`, while evaluating the left side only once.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 33.7. String operation traps table

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 33.8. Scanner method behavior table

**Key idea.** Scanner can read tokens or whole lines. Mixing token methods such as `nextInt` with `nextLine` often leaves a pending newline.

**Interview angle.** Check whether they explain token vs line input and resource lifetime.

### 33.9. Loop control flow table

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 33.10. Traditional switch vs switch expression table

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 33.11. `break` vs `continue` vs `return` vs `yield` table

**Key idea.** `yield` provides the value of a switch expression branch from a block. It is not a method return and does not exit the surrounding method.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 33.12. Try/finally control-flow table

**Key idea.** A final variable may be assigned exactly once along each valid initialization path. Blank final fields must be initialized by constructors or field initializers.

**Interview angle.** Ask which action wins when return, throw, and finally interact.

### 33.13. `var` inference examples table

**Key idea.** `var` asks the compiler to infer a local variable’s static type from the initializer. The resulting variable is still statically typed.

**Interview angle.** Ask the candidate to classify the snippet first: compile-time error, runtime exception, or exact output. Then require a rule-based explanation.

### 33.14. Most common interview mistakes checklist

**Key idea.** This topic is a small language rule that can change whether code compiles, what it prints, or which exception/control-flow path is taken. Treat it as part of Java’s exact source-level semantics.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

## 34. Practical Interview Strategy

This closing section gives a repeatable reasoning procedure for basic Java snippets under interview pressure.

### 34.1. First ask: does it compile?

**Key idea.** Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 34.2. Then ask: what is initialized?

**Key idea.** Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition.

**Interview angle.** Look for precision about local variables, fields, array elements, and path-sensitive compiler analysis.

### 34.3. Then ask: what is evaluated first?

**Key idea.** Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 34.4. Then ask: is there boxing, unboxing, or promotion?

**Key idea.** Overload resolution happens at compile time. Exact match is preferred, then widening, then boxing, with varargs as a later fallback; ambiguity is possible.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 34.5. Then ask: is this a statement or expression?

**Key idea.** An expression produces a value; a statement is executed for its effect. Java has expression statements, but not every statement can appear where a value is required.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 34.6. Then ask: can control flow skip this assignment?

**Key idea.** Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition.

**Interview angle.** Look for knowledge that assignment has a value, and that compound assignment is not always equivalent to simple assignment.

### 34.7. Then ask: can `null` appear?

**Key idea.** Use this as a repeatable diagnostic step. Interview snippets are easier when solved by classification rather than by intuition.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 34.8. Then ask: is this old switch or new switch?

**Key idea.** Object creation is an expression: it evaluates constructor arguments, allocates, initializes superclass state, initializes fields/blocks, and runs the constructor body.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 34.9. Then ask: is there hidden fall-through?

**Key idea.** Traditional colon-style switch can continue into following cases unless control flow exits. This is intentional but error-prone.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

### 34.10. Then ask: is the result compile-time or runtime?

**Key idea.** Some rules are enforced by the compiler before the program can run; others produce runtime values or exceptions. Interview snippets should always be classified first.

**Interview angle.** A strong answer should name the relevant Java rule and apply it to a short snippet.

## Appendix A. Compact Summary Tables

### Statement vs Expression

| Construct | Produces a value? | Common trap |
|---|---:|---|
| Assignment expression | Yes | Can appear inside conditions if type is boolean |
| Method invocation | Sometimes, depending on return type | `void` cannot be used as a value |
| `if` statement | No | Cannot be used directly as an expression |
| `switch` statement | No | May fall through |
| `switch` expression | Yes | Must be exhaustive and produce/yield a value |
| Declaration | No | Introduces a name; initializer is not the declaration itself |

### Traditional Switch vs Switch Expression

| Feature | Traditional switch statement | Modern switch expression |
|---|---|---|
| Produces value | No | Yes |
| Fall-through | Yes with colon labels | No with arrow labels |
| Exhaustiveness | Usually not required | Required |
| Branch blocks | Ordinary statements | Blocks must `yield` a value when needed |
| Common bug | Missing `break` | Missing default/exhaustive coverage |

### Basic Debugging Checklist

1. Does it compile under the intended Java version?
2. Are all local variables definitely assigned?
3. What is evaluated first?
4. Which conversions happen?
5. Is object identity or value equality being tested?
6. Can `null` be unboxed or dereferenced?
7. Is control flow changed by `break`, `continue`, `return`, `throw`, `yield`, or `finally`?
8. Is the construct a statement or an expression?
9. Is the result a compile-time constant or a runtime value?
10. Is the API literal-based or regex-based?