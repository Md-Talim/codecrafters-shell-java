package shell.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();

    public void register(String name, Command command) {
        commands.put(name, command);
    }

    public Optional<Command> get(String name) {
        return Optional.ofNullable(commands.get(name));
    }
}
