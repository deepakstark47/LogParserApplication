package IO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextFileProcessor implements FileProcessor {
    @Override
    public List<String> read(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }

    @Override
    public void write(String fileName, String content) throws IOException {
        Files.writeString(Paths.get(fileName), content);
    }
}
