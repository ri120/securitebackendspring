package tn.barmegtech.workshopbarmejtechsecurite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.barmegtech.workshopbarmejtechsecurite.model.CandidatModel;
import tn.barmegtech.workshopbarmejtechsecurite.service.CandidatService;

import java.util.List;

@RestController
@RequestMapping("/api/candidats")
public class CandidatController {

    @Autowired
    private CandidatService candidatService;

    @GetMapping("/{centreId}")
    public ResponseEntity<List<CandidatModel>> getCandidats(@PathVariable Long centreId) {
        return ResponseEntity.ok(candidatService.getCandidatsParCentre(centreId));
    }

    @PostMapping("/{centreId}")
    public ResponseEntity<CandidatModel> ajouterCandidat(@PathVariable Long centreId, @RequestBody CandidatModel candidat) {
        return ResponseEntity.ok(candidatService.ajouterCandidat(centreId, candidat));
    }
}
