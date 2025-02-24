import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Shell {
    interface BuiltinCommand {
        void execute();
    }

    private Navigator navigator;
    private final Map<String, BuiltinCommand> builtins = new HashMap<>();
    private List<String> arguments = new ArrayList<>();
    private String command;
    List<String> pathList;

    Shell() {
        String $path = System.getenv("PATH");
        pathList = Arrays.asList($path.split(":"));
        navigator = new Navigator();

        builtins.put("echo", () -> {
            int lastIdx = arguments.size() - 1;
            for (int i = 1; i < arguments.size(); i++) {
                System.out.print(arguments.get(i));

                if (i != lastIdx)
                    System.out.print(" ");
            }
            System.out.println();
        });

        builtins.put("exit", () -> { return; });

        builtins.put("pwd", () -> { System.out.println(navigator.getWorkingDirectory()); });

        builtins.put("cd", () -> {
            // Set to home directory if no arguments provided
            if (arguments.size() < 2) {
                navigator.setWorkingDirectory("~");
                return;
            }

            String directory = arguments.get(1);
            navigator.setWorkingDirectory(directory);
        });

        builtins.put("type", () -> {
            if (arguments.size() < 2) {
                System.out.println("Usage: type <command>");
                return;
            }

            String commandName = arguments.get(1);
            if (builtins.containsKey(commandName)) {
                System.out.println(commandName + " is a shell builtin");
            } else {
                String location = getLocation(commandName);

                if (location != null) {
                    System.out.println(commandName + " is " + location);
                } else {
                    System.out.println(commandName + ": not found");
                }
            }
        });
    }

    private void execute() {
        BuiltinCommand builtinCommand = builtins.get(command);

        if (builtinCommand != null) {
            builtinCommand.execute();
        } else {
            ProcessBuilder pb = new ProcessBuilder(arguments);

            try {
                Process process = pb.start();
                InputStream is = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println(command + ": command not found");
            }
        }
    }

    private String getLocation(String program) {
        for (String location : pathList) {
            Path path = Paths.get(location, program);

            if (Files.isExecutable(path)) {
                return path.toString();
            }
        }

        return null;
    }

    public boolean isExitCommand() {
        return command.startsWith("exit");
    }

    public void run(String line) {
        Parser parser = new Parser(line);
        this.arguments = parser.parse();
        this.command = arguments.get(0);

        execute();
    }
}
