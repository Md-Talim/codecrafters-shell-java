package shell.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils {
    public static void writeToFile(String content, String filePath) throws IOException {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(content);
        }
    }
}
