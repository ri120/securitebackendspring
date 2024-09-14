package tn.barmegtech.workshopbarmejtechsecurite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.PermissionType;
import tn.barmegtech.workshopbarmejtechsecurite.service.CentreFormationService;

import java.util.List;

@RestController
@RequestMapping("/api/centre-formation")
public class CentreFormationController {

    @Autowired
    private CentreFormationService centreFormationService;

    @PostMapping("/{id}/autoriser")
    public ResponseEntity<String> autoriserAcces(@PathVariable Long id, @RequestBody PermissionType permissionType) {
        boolean estAutorise = centreFormationService.autoriserAcces(id, permissionType);

        if (estAutorise) {
            return ResponseEntity.ok("Accès autorisé pour le centre de formation.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Autorisation refusée.");
        }
    }

    @GetMapping("/{id}/verifier/{permissionType}")
    public ResponseEntity<Boolean> verifierPermission(@PathVariable Long id, @PathVariable PermissionType permissionType) {
        boolean aPermission = centreFormationService.verifierPermission(id, permissionType);
        return ResponseEntity.ok(aPermission);
    }
}
