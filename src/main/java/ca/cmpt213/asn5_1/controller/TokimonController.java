package ca.cmpt213.asn5_1.controller;

import ca.cmpt213.asn5_1.model.Tokimon;
import ca.cmpt213.asn5_1.service.TokimonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tokimon")
public class TokimonController {
    @Autowired
    private TokimonService tokimonService;

    @GetMapping("/all")
    public ResponseEntity<List<Tokimon>> getAllTokimon() {
        return ResponseEntity.ok(tokimonService.getAllTokimon());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tokimon> getTokimonById(@PathVariable Long id) {
        Tokimon tokimon = tokimonService.getTokimonById(id);
        if (tokimon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(tokimon);
    }

    @PostMapping("/add")
    public ResponseEntity<Tokimon> addTokimon(@RequestBody Tokimon tokimon) {
        tokimonService.addTokimon(tokimon);
        return ResponseEntity.status(HttpStatus.CREATED).body(tokimon);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Tokimon> editTokimon(@PathVariable Long id, @RequestBody Tokimon updatedTokimon) {
        Tokimon tokimon = tokimonService.editTokimon(id, updatedTokimon);
        if (tokimon == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(tokimon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTokimon(@PathVariable Long id) {
        boolean deleted = tokimonService.deleteTokimon(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllTokimons() {
        tokimonService.deleteAllTokimons();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
