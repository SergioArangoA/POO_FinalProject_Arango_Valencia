package ui;

import java.util.Scanner;

/**
 - Console (Terminal) implementation of the IOUser abstract class.
 */
public class IOUserConsole extends IOUser {

    /**
     - A single, shared, static, and final Scanner for all console input.
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     - Provides the concrete implementation for 'writeLine'.
     - Prints the message to the console with a new line.
     */
    @Override
    public void writeLine(Object message) {
        System.out.println(message);
    }

    /**
     - Provides the concrete implementation for 'readLine'.
     - Uses the inherited 'formatPrompt' and 'print' (no newline)
     - to keep the user's cursor on the same line.
     */
    @Override
    public String readLine(String prompt) {
        String formattedPrompt = formatPrompt(prompt);
        System.out.print(formattedPrompt);
        return SCANNER.nextLine();
    }

    /**
     - Provides the robust implementation for 'readInt'.
     - This method will loop until a valid integer is entered.
     - It catches NumberFormatException.
     */
    @Override
    public int readInt(String prompt) {
        // Loop indefinitely until a valid return occurs
        while (true) {
            try {
                // 1. Read the input as text
                String input = readLine(prompt);
                // 2. Try to parse it as an integer
                int number = Integer.parseInt(input);
                // 3. If successful, return the number
                return number;
            } catch (NumberFormatException e) {
                // 4. If parsing fails, show an error and the loop repeats
                writeLine("Error: You must enter a valid integer.");
            }
        }
    }

    /**
     - Provides the robust implementation for 'readDouble'.
     - Will loop until a valid double is entered.
     - It replaces commas with dots for international compatibility.
     */
    @Override
    public double readDouble(String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                // Sanitize input: replace ',' with '.'
                String sanitizedInput = input.replace(',', '.');
                double number = Double.parseDouble(sanitizedInput);
                return number;
            } catch (NumberFormatException e) {
                writeLine("Error: You must enter a valid decimal number (e.g., 15.50).");
            }
        }
    }
}