package ui;

import java.util.Scanner;

/**
 - Console implementation of IOUser 
 */
public class IOUserConsole extends IOUser {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public void writeLine(Object message) {
        System.out.println(message);
    }

    @Override
    public String readLine(String prompt) {
        String formattedPrompt = formatPrompt(prompt);
        System.out.print(formattedPrompt);
        return SCANNER.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                int number = Integer.parseInt(input);
                return number;
            } catch (NumberFormatException e) {
                writeLine("Error: Debe ingresar un número entero válido.");
            }
        }
    }

    @Override
    public double readDouble(String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                String sanitizedInput = input.replace(',', '.');
                return Double.parseDouble(sanitizedInput);
            } catch (NumberFormatException e) {
                writeLine("Error: Debe ingresar un número decimal válido.");
            }
        }
    }

    @Override
    public char readChar(String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                // Validate exactly one character / Validar exactamente un carácter
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