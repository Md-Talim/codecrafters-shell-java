package shell.command;

import java.util.List;

import shell.io.Redirection;

public class ExitCommand extends BuiltinCommand {
    private final Runnable exitHandler;

    public ExitCommand(Runnable exitHandler) {
        super("exit");
        this.exitHandler = exitHandler;
    }

    @Override
    public void execute(List<String> _args, Redirection _redirection) {
        exitHandler.run();
    }
}
