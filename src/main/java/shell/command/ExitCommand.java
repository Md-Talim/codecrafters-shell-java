package shell.command;

import java.util.List;

public class ExitCommand extends BuiltinCommand {
    private final Runnable exitHandler;

    public ExitCommand(Runnable exitHandler) {
        super("exit");
        this.exitHandler = exitHandler;
    }

    @Override
    public void execute(List<String> _args) {
        exitHandler.run();
    }
}
