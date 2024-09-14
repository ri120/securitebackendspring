package tn.barmegtech.workshopbarmejtechsecurite.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.exception.UnauthorizedException;
import tn.barmegtech.workshopbarmejtechsecurite.model.CandidatModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.PermissionType;
import tn.barmegtech.workshopbarmejtechsecurite.repositories.CandidatRepo;
import tn.barmegtech.workshopbarmejtechsecurite.service.CandidatService;
import tn.barmegtech.workshopbarmejtechsecurite.service.CentreFormationService;

import java.util.List;


@Service
public class CandidatServiceImpl implements CandidatService {

    @Autowired
    private CandidatRepo candidatRepo;

    @Autowired
    private CentreFormationService centreFormationService;

    @Override
    public List<CandidatModel> getCandidatsParCentre(Long centreFormationId) {
        CentreFormationModel centreFormation = centreFormationService.trouverCentreParId(centreFormationId);
        return candidatRepo.findByCentreFormation(centreFormation);
    }



    @Override
    public CandidatModel ajouterCandidat(Long centreFormationId, CandidatModel candidat) {
        if (!centreFormationService.verifierPermission(centreFormationId, PermissionType.GESTION_CANDIDATS)) {
            throw new UnauthorizedException("Vous n'avez pas la permission de gérer les candidats.");
        }
        CentreFormationModel centreFormation = centreFormationService.trouverCentreParId(centreFormationId);
        candidat.setCentreFormation(centreFormation);
        return candidatRepo.save(candidat);
    }
}
