package ui;

/**
 - Abstract base class for all User Input/Output (I/O) operations.
 
 - It defines the abstract methods that all
   specific UI implementations provide.
 */
public abstract class IOUser {

    /**
     - A protected, non-abstract method (shared logic)
     - that formats a prompt string for display.
     */
    protected String formatPrompt(String prompt) {
        
        return ">> " + prompt + " ";
    }

    /**
     - Abstract method. Displays a message to the user.
     
     - @param message The message to be shown.
     */
    public abstract void writeLine(Object message);

    /**
     - Abstract method. Reads a line of text (String) from the user.
     - @param prompt The message to show the user (e.g., "Enter license plate:").
     - @return The String provided by the user.
     */
    public abstract String readLine(String prompt);

    /**
     - Abstract method. Reads a valid integer (int) from the user.
     - @param prompt The message to show the user (e.g., "Select option:").
     - @return A valid integer.
     */
    public abstract int readInt(String prompt);

    /**
     - Abstract method. Reads a valid double from the user.
     - @param prompt The message to show the user (e.g., "Enter rate:").
     - @return A valid double.
     */
    public abstract double readDouble(String prompt);

    
    public abstract char readChar(String prompt);
  }