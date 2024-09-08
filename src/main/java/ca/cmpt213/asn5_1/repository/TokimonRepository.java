package ca.cmpt213.asn5_1.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TokimonRepository {
    private static final String FILE_PATH = "data/tokimoncards.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Tokimon> tokimonList;

    public TokimonRepository() {
        try {
            tokimonList = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Tokimon>>() {});
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
            objectMapper.writeValue(new File(FILE_PATH), tokimonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
