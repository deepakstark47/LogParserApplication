package IO;

import java.io.IOException;
import java.util.List;

public interface FileProcessor {
    List<String> read(String fileName) throws IOException;
    void write(String fileName, String content) throws IOException;
}
