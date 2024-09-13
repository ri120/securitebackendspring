package tn.barmegtech.workshopbarmejtechsecurite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.service.CentreFormationService;

import java.util.List;


@RestController
@RequestMapping("/api/centreformations")
public class CentreFormationController {

    @Autowired
    private CentreFormationService centreFormationService;

    @GetMapping
    public List<CentreFormationModel> getAllCentreFormations() {
        return centreFormationService.getAllCentreFormations();
    }

    @GetMapping("/{id}")
    public CentreFormationModel getCentreFormationById(@PathVariable Long id) {
        return centreFormationService.getCentreFormationById(id);
    }

    @PostMapping("/create")
    public CentreFormationModel createCentreFormation(@RequestBody CentreFormationModel centreFormation) {
        return centreFormationService.createCentreFormation(centreFormation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCentreFormation(@PathVariable Long id) {
        centreFormationService.deleteCentreFormation(id);
        return ResponseEntity.ok("CentreFormation deleted successfully");
    }
}
