package shell.command;

import java.util.List;

import shell.io.Redirection;

public interface Command {
    void execute(List<String> args, Redirection redirection);
}
