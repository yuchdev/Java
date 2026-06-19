package net.atatat.s02_basicops.ss03_var_naming;

@SuppressWarnings({"unused", "ConstantValue", "NonAsciiCharacters"})
public class Main {

    /**
     * Demonstrates various aspects of variable naming, input/output operations,
     * and basic usage of the `Scanner` class for reading input in Java. This method explores:
     * 1. **Variable Naming**:
     *    - Provides examples of permissible variable names, including basic, intricate, and Unicode names.
     *    - Highlights the use of unconventional variable names that are valid despite their appearance.
     *    - Mentions compatibility with keywords such as `var` for backward compatibility.
     * 2. **Output Operations**:
     *    - Demonstrates different ways to print data to the console using `System.out.print` and `System.out.println`.
     *    - Explains the difference between printing with and without ending with a newline character.
     * 3. **Input Operations**:
     *    - Explores the usage of the `Scanner` class to read input from the standard input stream.
     *    - Shows examples of reading single words and full lines using `next()` and `nextLine()` respectively.
     *    - Discusses how whitespace characters (like spaces and newlines) are handled during input reading.
     *    - Illustrates processing multi-line input using a while loop and a linked list to store values.
     * 4. **General Notes**:
     *    - Covers specific nuances, like handling whitespace when using `next()` vs. `nextLine()`.
     *    - Highlights potential use cases for reading multiline inputs such as files or continuous streams.
     * This method serves as an educational reference for understanding variable naming rules,
     * basic console input/output, handling Unicode in variable names, and how to properly utilize the Scanner class.
     */
    public static void main(String[] args) {

                // Variable naming
        int simpleName = 0;

        char $imple_ugly_name = 'X';

        String unicodeИame = "Squatting slavs in tracksuits";

        // Wrong: 0name, all containing !@#...
        System.out.println("Simple name: " + simpleName);
        System.out.println("Ugly but working variable: " + $imple_ugly_name);
        System.out.println("Unicode but working variable: " + unicodeИame);

        // 'var' variable name allowed despite being a keyword since Java10, for compatibility
        int var = 0;
        System.out.println("Simple var name: " + var);

        //
        // Simple output

        // Print line and finish with EOL
        System.out.println("Die, world, die!");

        // Print literal
        System.out.print("A");
        System.out.print(true);
        System.out.print(100);
        System.out.print('\n');

        //
        // Simple input

        // One of the simplest ways to get values from the standard input
        // is java.util.Scanner class.
        // Scanner class has a number of overloaded constructors which includes
        // public Scanner(InputStream source)
        // This Scanner produces values scanned from the specified input stream
        // Bytes from the stream are converted into characters f default charset
        Scanner scan = new Scanner(System.in);

        // Example from Jetbrains Academy
        String s = "stub";
        LinkedList<String> guestList = new LinkedList<>();
        while(!s.isEmpty()){
            s = scan.next();
            guestList.add(s);
        }

        for (String guest: guestList) {
            System.out.println("Guest: " + guest);
        }

        // Read from Scanner
        System.out.println("Enter name of TODO");
        String name = scan.next();
        if(name.equals("TODO")){
            System.out.println("TODO" + name);
        }
        else{
            System.out.println("TODO");
        }

        // Important notice: in Java whitespace includes everything that looks empty when printed:
        // tab, the newline character, and other non-printing characters
        System.out.println("Try some string and EOL characters: ");
        String tryIt = scan.next();
        System.out.println("TODO" + tryIt);

        // You can use other method, which includes all spaces
        String otherName = scan.nextLine();
        System.out.println("TODO" + otherName);

        // Reading from multiline input, like files:
        // TODO: example from https://hyperskill.org/learn/step/9055

    }
}
