package tn.barmegtech.workshopbarmejtechsecurite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.barmegtech.workshopbarmejtechsecurite.entites.sousAdmin;
import tn.barmegtech.workshopbarmejtechsecurite.service.SousAdminService;

import java.util.List;

@RestController
@RequestMapping("/api/sousadmins")
public class SousAdminController {

    @Autowired
    private SousAdminService sousAdminService;

    @GetMapping
    public List<sousAdmin> getAllSousAdmins() {
        return sousAdminService.getAllSousAdmins();
    }

    @GetMapping("/{id}")
    public sousAdmin getSousAdminById(@PathVariable Long id) {
        return sousAdminService.getSousAdminById(id);
    }

    @PostMapping("/create")
    public sousAdmin createSousAdmin(@RequestBody sousAdmin sousAdmin) {
        return sousAdminService.createSousAdmin(sousAdmin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSousAdmin(@PathVariable Long id) {
        sousAdminService.deleteSousAdmin(id);
        return ResponseEntity.ok("SousAdmin deleted successfully");
    }
}
