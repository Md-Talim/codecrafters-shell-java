package shell.command;

public abstract class BuiltinCommand implements Command {
    protected final String name;

    protected BuiltinCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
