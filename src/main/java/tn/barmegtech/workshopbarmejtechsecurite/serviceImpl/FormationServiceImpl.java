package tn.barmegtech.workshopbarmejtechsecurite.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.FormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.repositories.FormationRepo;
import tn.barmegtech.workshopbarmejtechsecurite.service.CentreFormationService;
import tn.barmegtech.workshopbarmejtechsecurite.service.FormationService;

import java.util.List;

@Service
public class FormationServiceImpl implements FormationService {

    @Autowired
    private FormationRepo formationRepo;

    @Autowired
    private CentreFormationService centreFormationService;

    @Override
    public List<FormationModel> getFormationsParCentre(Long centreFormationId) {
        CentreFormationModel centreFormation = centreFormationService.trouverCentreParId(centreFormationId);
        return formationRepo.findByCentreFormation(centreFormation);
    }


    @Override
    public FormationModel ajouterFormation(Long centreFormationId, FormationModel formation) {
        CentreFormationModel centreFormation = centreFormationService.trouverCentreParId(centreFormationId);
        formation.setCentreFormation(centreFormation);
        return formationRepo.save(formation);
    }
}
