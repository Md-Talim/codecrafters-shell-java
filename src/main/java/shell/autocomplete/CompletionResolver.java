package shell.autocomplete;

import java.util.Set;

import shell.Shell;

@FunctionalInterface
public interface CompletionResolver {
    Set<String> getCompletions(Shell shell, String beginning);
}
