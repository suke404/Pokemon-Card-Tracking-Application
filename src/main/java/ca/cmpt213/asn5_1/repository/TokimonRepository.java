package ca.cmpt213.asn5_1.repository;

import ca.cmpt213.asn5_1.model.Tokimon;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.InputStream;
import java.net.URL;


@Repository
public class TokimonRepository {
    private static final String FILE_PATH = "data/tokimoncards.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Tokimon> tokimonList;

    public TokimonRepository() {
        try {
            URL resource = getClass().getClassLoader().getResource(FILE_PATH);
            if (resource == null) {
                File file = new File(getClass().getClassLoader().getResource("").getPath() + FILE_PATH);
                file.getParentFile().mkdirs();
                file.createNewFile();
                tokimonList = new ArrayList<>();
                saveToFile();
            } else {
                InputStream is = resource.openStream();
                tokimonList = objectMapper.readValue(is, new TypeReference<List<Tokimon>>() {});
                is.close();
            }
        } catch (IOException e) {
            tokimonList = new ArrayList<>();
        }
    }

    public List<Tokimon> findAll() {
        return tokimonList;
    }

    public Optional<Tokimon> findById(Long id) {
        return tokimonList.stream().filter(tokimon -> tokimon.getId().equals(id)).findFirst();
    }


    public void save(Tokimon tokimon) {
        tokimonList.add(tokimon);
        saveToFile();
    }

    public void update(Tokimon tokimon) {
        findById(tokimon.getId()).ifPresent(existingTokimon -> {
            int index = tokimonList.indexOf(existingTokimon);
            tokimonList.set(index, tokimon);
            saveToFile();
        });
    }

    public void deleteById(Long id) {
        tokimonList.removeIf(tokimon -> tokimon.getId().equals(id));
        saveToFile();
    }

    private void saveToFile() {
        try {
            URL resource = getClass().getClassLoader().getResource(FILE_PATH);
            File file;
            if (resource == null) {
                file = new File(getClass().getClassLoader().getResource("").getPath() + FILE_PATH);
            } else {
                file = new File(resource.getPath());
            }
            objectMapper.writeValue(file, tokimonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
