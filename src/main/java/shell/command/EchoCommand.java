package shell.command;

import java.util.List;

public class EchoCommand implements Command {
    @Override
    public void execute(List<String> args) {
        int lastIdex = args.size() - 1;
        for (int i = 1; i < args.size(); i++) {
            System.out.println(args.get(i));
            if (i != lastIdex)
                System.out.println();
        }
    }
}
