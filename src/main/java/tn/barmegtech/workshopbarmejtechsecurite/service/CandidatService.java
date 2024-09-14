package tn.barmegtech.workshopbarmejtechsecurite.service;

import tn.barmegtech.workshopbarmejtechsecurite.model.CandidatModel;

import java.util.List;

public interface CandidatService {
    List<CandidatModel> getCandidatsParCentre(Long centreFormationId);
    CandidatModel ajouterCandidat(Long centreFormationId, CandidatModel candidat);
}
