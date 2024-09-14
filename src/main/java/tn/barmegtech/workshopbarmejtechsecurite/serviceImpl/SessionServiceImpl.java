package tn.barmegtech.workshopbarmejtechsecurite.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.model.SessionModel;
import tn.barmegtech.workshopbarmejtechsecurite.repositories.SessionRepo;
import tn.barmegtech.workshopbarmejtechsecurite.service.SessionService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepo sessionRepo;
    @Override
    public List<SessionModel> getAllSessions() {
        return sessionRepo.findAll();
    }


    @Override
    public SessionModel getSessionById(Long id) {
        return sessionRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Session not found"));
    }

    @Override
    public SessionModel createSession(SessionModel session) {
        return sessionRepo.save(session);
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepo.deleteById(id);
    }
}
