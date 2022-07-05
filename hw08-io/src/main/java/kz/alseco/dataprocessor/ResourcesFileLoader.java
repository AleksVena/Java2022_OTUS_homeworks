package kz.alseco.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kz.alseco.model.Measurement;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final  List<Measurement> measurements;

    private static final Type LIST_TYPE = new TypeToken<List<Measurement>>(){}.getType();

    public ResourcesFileLoader(String fileName) throws FileProcessException {
        try {
            var resource = getClass().getClassLoader().getResource(fileName);
            if (resource == null) {
                throw new FileProcessException("Файл '" + fileName + "' не найден в ресурсах");
            }
            var jsonContent = Files.readString(Path.of(resource.toURI()));
            this.measurements = new Gson().fromJson(jsonContent, LIST_TYPE);
        } catch (URISyntaxException | IOException e) {
            throw new FileProcessException(e);
        }
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        return measurements;
    }
}
