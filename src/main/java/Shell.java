import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Shell {
  interface BuiltinCommand {
    void execute();
  }

  private Executable executable = new Executable();
  private final Map<String, BuiltinCommand> builtins = new HashMap<>();
  private List<String> arguments = new ArrayList<>();
  private final String command;

  Shell(String line) {
    Parser parser = new Parser(line);
    this.arguments = parser.parse();
    this.command = arguments.get(0);

    builtins.put(
        "echo",
        () -> {
          int lastIdx = arguments.size() - 1;
          for (int i = 1; i < arguments.size(); i++) {
            System.out.print(arguments.get(i));

            if (i != lastIdx) System.out.print(" ");
          }
          System.out.println();
        });

    builtins.put(
        "type",
        () -> {
          if (arguments.size() < 2) {
            System.out.println("Usage: type <command>");
            return;
          }

          String commandName = arguments.get(1);
          if (builtins.containsKey(commandName)) {
            System.out.println(commandName + " is a shell builtin");
          } else {
            String location = executable.getLocation(commandName);

            if (location != null) {
              System.out.println(commandName + " is " + location);
            } else {
              System.out.println(commandName + ": not found");
            }
          }
        });

    builtins.put(
        "exit",
        () -> {
          return;
        });
  }

  private void executeBuiltin() {
    BuiltinCommand builtinCommand = builtins.get(command);

    if (builtinCommand != null) {
      builtinCommand.execute();
    } else {
      System.out.println(command + ": command not found");
    }
  }

  public void execute() {
    executeBuiltin();
  }

  public boolean isExitCommand() {
    return command.startsWith("exit");
  }
}
