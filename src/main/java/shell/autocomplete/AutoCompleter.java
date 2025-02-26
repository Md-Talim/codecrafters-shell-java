package shell.autocomplete;

import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import shell.Shell;
import shell.autocomplete.impl.BuiltinCompletionResolver;
import shell.autocomplete.impl.ExecutableCompletionResolver;

public class AutoCompleter {
    // Comparator to sort completion candidates:
    // shortest first, then lexicographically
    public static final Comparator<String> SHORTEST_FIRST = Comparator
            .comparingInt(String::length)
            .thenComparing(String::compareTo);

    // Enum representing autocomplete results:
    // NONE (no match), FOUND (single match), MORE (multiple matches)
    public enum Result {
        NONE, FOUND, MORE
    }

    // List of resolvers used for autocompletion
    // (built-in commands + executables from PATH)
    @Getter
    public final List<CompletionResolver> resolvers = List.of(
            BuiltinCompletionResolver.INSTANCE,
            ExecutableCompletionResolver.INSTANCE);

    /**
     * Attempts to autocomplete the current input line based on available commands
     * and executables.
     *
     * @param shell    The shell instance that provides built-in commands and
     *                 environment details.
     * @param line     A {@code StringBuilder} containing the current user input,
     *                 which may be modified
     *                 if an autocomplete match is found.
     * @param bellRang A boolean indicating whether the autocomplete bell has
     *                 already rung,
     *                 used to determine whether to display multiple completions.
     * @return A {@link Result} indicating the outcome of the autocomplete attempt:
     *         {@code NONE} if no matches were found,
     *         {@code FOUND} if a single match was found and applied,
     *         {@code MORE} if multiple matches exist and additional input is
     *         needed.
     */
    public Result autocomplete(Shell shell, StringBuilder line, boolean bellRang) {
        final var beginning = line.toString();
        if (beginning.isBlank()) {
            return Result.FOUND; // If input is blank, no need for autocomplete
        }

        // Gather all possible completions from resolvers
        // Store candidates in a sorted order
        final var candidates = resolvers.stream()
                .map((resolver) -> resolver.getCompletions(shell, beginning))
                .flatMap(Set::stream)
                .map((candidate) -> candidate.substring(beginning.length()))
                .collect(Collectors.toCollection(() -> new TreeSet<>(SHORTEST_FIRST)));

        if (candidates.isEmpty()) {
            return Result.NONE; // No matches found
        }

        if (candidates.size() == 1) {
            // If there is exactly one match, append it to the line and return FOUND
            final var candidate = candidates.first();
            writeCandidate(line, candidate, false);
            return Result.FOUND;
        }

        // If multiple matches exist, find the shared prefix
        final var prefix = findSharedPrefix(candidates);
        if (!prefix.isEmpty()) {
            writeCandidate(line, prefix, true);
            return Result.MORE; // Indicate more options available
        }

        // If there are multiple matches but no common prefix,
        // display all options if bell was already rung
        if (bellRang) {
            System.out.print(candidates.stream().map(beginning::concat).collect(Collectors.joining(" ", "\n", "\n")));
            System.out.print("$ ");
            System.out.print(beginning);
            System.out.flush();
        }

        return Result.MORE; // Indicate more completions exist
    }

    /**
     * Finds the longest shared prefix among the candidates
     *
     * @param candidates
     * @return
     */
    private static String findSharedPrefix(SequencedSet<String> candidates) {
        final var first = candidates.getFirst();
        if (first.isEmpty()) {
            return "";
        }

        var end = 0;
        for (; end < first.length(); end++) {
            var oneIsNotMatching = false;
            final var iterator = candidates.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                final var candidate = iterator.next();
                if (!first.subSequence(0, end).equals(candidate.subSequence(0, end))) {
                    oneIsNotMatching = true;
                    break;
                }
            }
            if (oneIsNotMatching) {
                end -= 1;
                break;
            }
        }
        return first.substring(0, end);
    }

    /***
     * Writes the completed text to the user input line
     *
     * @param line
     * @param candidate
     * @param hasMore
     */
    private void writeCandidate(StringBuilder line, String candidate, boolean hasMore) {
        line.append(candidate);
        System.out.print(candidate);
        if (!hasMore) {
            line.append(' ');
            System.out.print(' '); // Add a space at the end of the completion
        }
    }
}
