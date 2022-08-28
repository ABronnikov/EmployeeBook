package input;

import java.util.Scanner;

public class ConsoleInput implements Input{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String askStr(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public long askLong(String question) {
        return Long.parseLong(askStr(question));
    }
}
