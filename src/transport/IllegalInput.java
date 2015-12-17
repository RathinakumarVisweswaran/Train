package transport;

/**
 * Created by rathinakumar on 12/7/15.
 */
public class IllegalInput extends Exception{
    String input;

    public IllegalInput(String input) {
        this.input = input;
    }

    public void printMessage()
    {
        System.err.println("The given Input format is not supported - "+input);
    }

    public String getInput() {
        return input;
    }
}
