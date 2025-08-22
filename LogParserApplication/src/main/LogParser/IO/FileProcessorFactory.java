package IO;

public class FileProcessorFactory {
    public static FileProcessor createProcessor(String fileName) {
        if (fileName.endsWith(".txt")) {
            return new TextFileProcessor();
        } else if (fileName.endsWith(".json")) {
            return new JsonFileProcessor();
        }

        throw new UnsupportedOperationException("File type not supported for: " + fileName);
    }
}
