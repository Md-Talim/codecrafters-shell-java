package shell.command;

import java.util.List;

import shell.io.Redirection;

public class ExitCommand extends BuiltinCommand {
    // private final Runnable exitHandler;

    public ExitCommand() {
        super("exit");
        // this.exitHandler = exitHandler;
    }

    @Override
    public void execute(List<String> _args, Redirection _redirection) {
        System.exit(0);
    }
}
