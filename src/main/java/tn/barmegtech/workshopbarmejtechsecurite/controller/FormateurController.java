package tn.barmegtech.workshopbarmejtechsecurite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.barmegtech.workshopbarmejtechsecurite.model.FormateurModel;
import tn.barmegtech.workshopbarmejtechsecurite.service.FormateurService;

import java.util.List;

@RestController
@RequestMapping("/api/formateurs")
public class FormateurController {

    @Autowired
    private FormateurService formateurService;

    @GetMapping("/{centreId}")
    public ResponseEntity<List<FormateurModel>> getFormateurs(@PathVariable Long centreId) {
        return ResponseEntity.ok(formateurService.getFormateursParCentre(centreId));
    }

    @PostMapping("/{centreId}")
    public ResponseEntity<FormateurModel> ajouterFormateur(@PathVariable Long centreId, @RequestBody FormateurModel formateur) {
        return ResponseEntity.ok(formateurService.ajouterFormateur(centreId, formateur));
    }
}
