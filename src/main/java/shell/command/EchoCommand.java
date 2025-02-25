package shell.command;

import java.io.IOException;
import java.util.List;

import shell.io.FileUtils;
import shell.io.Redirection;

public class EchoCommand implements Command {
    @Override
    public void execute(List<String> args, Redirection redirection) {
        List<String> arguments = args.subList(1, args.size());
        String output = String.join(" ", arguments);

        if (redirection != null) {
            try {
                FileUtils.writeToFile(output + "\n", redirection.getFile());
            } catch (IOException e) {
                System.err.println("echo: " + e.getMessage());
            }
        } else {
            System.out.println(output);
        }
    }
}
