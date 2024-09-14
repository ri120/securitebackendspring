package tn.barmegtech.workshopbarmejtechsecurite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.barmegtech.workshopbarmejtechsecurite.model.SessionModel;
import tn.barmegtech.workshopbarmejtechsecurite.service.SessionService;

import java.util.List;


@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public List<SessionModel> getAllSessions() {
        return sessionService.getAllSessions();
    }



    @GetMapping("/{id}")
    public SessionModel getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id);
    }

    @PostMapping("/create")
    public SessionModel createSession(@RequestBody SessionModel session) {
        return sessionService.createSession(session);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok("Session deleted successfully");
    }
}
