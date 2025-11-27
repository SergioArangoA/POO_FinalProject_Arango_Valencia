package ui;

import java.util.Scanner;

/**
 - Implementation of the IOUser abstract class for Console interactions.
 - Implemented this class using java.util.Scanner to handle standard input/output.
 */
public class IOUserConsole extends IOUser {

    /**
     - A single, static Scanner instance for reading console input.
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     - Implementation of the writeLine method for the console.
     - @param message The object to be printed 
     */
    @Override
    public void writeLine(Object message) {
        System.out.println(message);
    }

    /**
     - Reads a String from the console.
     - @param prompt The message to display
     - @return The user input 
     */
    @Override
    public String readLine(String prompt) {
        String formattedPrompt = formatPrompt(prompt);
        System.out.print(formattedPrompt);
        return SCANNER.nextLine();
    }

    /**
     - Reads an integer with validation.
  
     - Implemented a loop with a try-catch block to prevent the program from crashing if the user enters text instead of a number.
     * @param prompt The message to display 
     * @return A valid integer 
     */
    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                // reuse the readLine method to get the input
                String input = readLine(prompt);
                
                // Try to parse the integer
                int number = Integer.parseInt(input);
                return number;
            } catch (NumberFormatException e) {
                // If parsing fails, warn the user and the loop repeats
                writeLine("Error: Debe ingresar un número entero válido.");
            }
        }
    }

    /**
     * Reads a single character with validation.
     * I validate that the input length is exactly 1 to ensure it's a valid option ('c' or 'm').
     * @param prompt The message to display 
     * @return A single character 
     */
    @Override
    public char readChar(String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                
                // Validate exactly one character
                if (input != null && input.length() == 1) {
                    return input.charAt(0);
                } else {
                    writeLine("Error: Debe ingresar un único carácter (ej: 'c' o 'm').");
                }
            } catch (Exception e) {
                writeLine("Error leyendo el carácter. Intente de nuevo.");
            }
        }
    }
}