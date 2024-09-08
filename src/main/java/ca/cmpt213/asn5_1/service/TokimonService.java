package ca.cmpt213.asn5_1.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokimonService {
    @Autowired
    private TokimonRepository tokimonRepository;

    public List<Tokimon> getAllTokimon() {
        return tokimonRepository.findAll();
    }

    public Tokimon getTokimonById(Long id) {
        return tokimonRepository.findById(id).orElse(null);
    }

    public void addTokimon(Tokimon tokimon) {
        tokimonRepository.save(tokimon);
    }

    public Tokimon editTokimon(Long id, Tokimon updatedTokimon) {
        if (tokimonRepository.findById(id).isPresent()) {
            updatedTokimon.setId(id);
            tokimonRepository.update(updatedTokimon);
            return updatedTokimon;
        } else {
            return null;
        }
    }

    public boolean deleteTokimon(Long id) {
        if (tokimonRepository.findById(id).isPresent()) {
            tokimonRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
