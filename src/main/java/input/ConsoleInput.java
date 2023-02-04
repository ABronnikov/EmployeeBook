package input;

import java.util.Scanner;
import util.Constants;

public class ConsoleInput implements Input {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public String askStr(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public long askLong(String question) {
        String line = askStr(question);
        return line.matches("\\d+") ? Long.parseLong(line) : Constants.INCORRECT_INDEX;
    }
}
