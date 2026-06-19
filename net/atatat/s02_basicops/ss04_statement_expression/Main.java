package net.atatat.s02_basicops.ss04_statement_expression;

@SuppressWarnings({"unused", "ConstantValue", "NonAsciiCharacters"})
public class Main {

    /**
     * Demonstrates and explains the concepts of statements, expressions, declarations,
     * expression lists, and resource lists in Java.
     * - A statement represents a complete command in Java, which performs an action
     * and ends with a semicolon (`;`). Examples include variable assignments and method calls.
     * - An expression evaluates to a value and can be a part of statements. For instance,
     * arithmetic operations that return a result are considered expressions.
     * - A declaration defines a variable, method, class, or other entity within the program.
     * It introduces these entities to be used later in the code.
     * - An expression list is a comma-separated collection of expressions, commonly used
     * in scenarios like array initializations or method arguments.
     * - A resource list is used in try-with-resources constructs, managing resources that implement
     * the `AutoCloseable` interface, such as streams or readers. Resources in this list
     * are automatically closed after usage to ensure proper resource management.
     * <p>
     * This method provides examples and explanations to clarify these fundamental
     * programming concepts.
     */
    public static void main(String[] args) {

        // Statement, expression, declaration
        // A statement is a complete command that does something. It ends with a semicolon ;
        // Example of statement (including ;)
        int a = 1;
        int b = 2;
        int c = a + b;
        System.out.println("Statement result: " + c);

        // An expression is something that evaluates to a value. It can be part of a statement.
        int d = (a + b) * c;
        System.out.println("Expression result: " + d);

        // A declaration introduces a variable, method, class, etc. into the program.
        int e;
        e = 3;
        float f = 4.0f;
        System.out.println("Declaration result: e=" + e + ", f=" + f);

        // An expression list is a comma-separated list of expressions, used in contexts like
        // method calls, array initializers, etc.
        // Example of expression list (comma-separated)
        int[] g = {1, 2, 3}; // Array initializer
        System.out.println("Expression list result: " + g[0] + ", " + g[1] + ", " + g[2]);

        // A resource list appears in try-with-resources statements.
        // It contains variables (resources) that implement AutoCloseable.
        // (AutoCloseable is an interface that allows an object to be closed automatically)
        // E.g.
        {
            String data = "Line 1\nLine 2\nLine 3";

            // Use try-with-resources to manage the BufferedReader
            // (a standard library class used to read data from a `String` as if it were a stream)
            try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception exception) {
                System.out.println("Error reading data from StringReader: " + exception.getMessage());
                System.out.println("Stack trace:");
                //noinspection CallToPrintStackTrace
                exception.printStackTrace();
            }
            // The BufferedReader and StringReader are automatically closed here
        }
        // At the same time it's a statement list
        // (a series of statements inside a block `{}`)
    }
}
