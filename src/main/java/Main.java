import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("$ ");
      String input = scanner.nextLine();
      Parser parser = new Parser(input);
      List<String> arguments = parser.parse();

      String command = arguments.get(0);

      switch (command) {
        case "echo" -> {
          int lastIdx = arguments.size() - 1;

          for (int i = 1; i < arguments.size(); i++) {
            System.out.print(arguments.get(i));

            if (i != lastIdx) System.out.print(" ");
          }

          System.out.println();
        }
        case "exit" -> {
          scanner.close();
          System.exit(0);
        }
        default -> {
          System.out.println(input + ": command not found");
        }
      }
    }
  }
}
