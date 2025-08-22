package IO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {
    private static FileHandler instance;

    private FileHandler() {}

    public static synchronized FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }

    public List<String> readFile(String fileName) throws IOException {
        FileProcessor processor = FileProcessorFactory.createProcessor(fileName);
        return processor.read(fileName);
    }

    public void writeFile(String fileName, String content) throws IOException {
        FileProcessor processor = FileProcessorFactory.createProcessor(fileName);
        processor.write(fileName, content);
    }

    public void ensureOutputFiles(List<String> fileNames) throws IOException {
        for (String fileName : fileNames) {
            File file = new File(fileName);
            if (!file.exists()) {
                writeFile(fileName, "{}"); // Create file with empty JSON content
            }
        }
    }
}






