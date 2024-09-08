package ca.cmpt213.asn5_1.service;

import ca.cmpt213.asn5_1.model.Tokimon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TokimonService {
    private final List<Tokimon> tokimonList = new ArrayList<>();

    public List<Tokimon> getAllTokimon() {
        return new ArrayList<>(tokimonList);
    }

    public Tokimon getTokimonById(Long id) {
        return tokimonList.stream()
                .filter(tokimon -> tokimon.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addTokimon(Tokimon tokimon) {
        if (tokimon.getId() == null) {
            tokimon.setId(++Tokimon.idCounter);
        }
        tokimonList.add(tokimon);
    }

    public Tokimon editTokimon(Long id, Tokimon updatedTokimon) {
        Optional<Tokimon> optionalTokimon = tokimonList.stream()
                .filter(tokimon -> tokimon.getId().equals(id))
                .findFirst();

        if (optionalTokimon.isPresent()) {
            Tokimon existingTokimon = optionalTokimon.get();
            existingTokimon.setName(updatedTokimon.getName());
            existingTokimon.setType(updatedTokimon.getType());
            existingTokimon.setRarity(updatedTokimon.getRarity());
            existingTokimon.setPictureUrl(updatedTokimon.getPictureUrl());
            existingTokimon.setHp(updatedTokimon.getHp());
            return existingTokimon;
        }

        return null;
    }

    public boolean deleteTokimon(Long id) {
        return tokimonList.removeIf(tokimon -> tokimon.getId().equals(id));
    }

    public void deleteAllTokimons() {
        tokimonList.clear();
    }
}
