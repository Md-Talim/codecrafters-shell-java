package shell.autocomplete.impl;

import java.util.Set;
import java.util.stream.Collectors;

import shell.Shell;
import shell.autocomplete.CompletionResolver;

public enum BuiltinCompletionResolver implements CompletionResolver {
    INSTANCE;

    @Override
    public Set<String> getCompletions(Shell shell, String beginning) {
        return shell.getBuiltinCommands()
                .keySet()
                .stream()
                .filter((name) -> name.startsWith(beginning))
                .collect(Collectors.toSet());
    }
}
