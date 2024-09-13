package tn.barmegtech.workshopbarmejtechsecurite.service;

import tn.barmegtech.workshopbarmejtechsecurite.model.SessionModel;

import java.util.List;

public interface SessionService {
    List<SessionModel> getAllSessions();
    List<SessionModel> getSessionsByCentreFormation(Long centreFormationId);
    SessionModel getSessionById(Long id);
    SessionModel createSession(SessionModel session);
    void deleteSession(Long id);
}
