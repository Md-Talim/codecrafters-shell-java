import java.util.Scanner;

import shell.Shell;;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Shell shell = new Shell();

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            shell.run(input);

            if (shell.isExitCommand()) {
                break;
            }
        }

        scanner.close();
    }
}
