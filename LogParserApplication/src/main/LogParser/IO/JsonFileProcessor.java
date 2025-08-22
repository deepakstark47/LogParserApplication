package IO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonFileProcessor implements FileProcessor {
    private final Gson gson;

    public JsonFileProcessor() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<String> read(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }

    @Override
    public void write(String fileName, String content) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            Object json = gson.fromJson(content, Object.class);
            gson.toJson(json, writer);
        }
    }
}
