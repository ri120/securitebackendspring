package tn.barmegtech.workshopbarmejtechsecurite.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.FormateurModel;
import tn.barmegtech.workshopbarmejtechsecurite.repositories.FormateurRepo;
import tn.barmegtech.workshopbarmejtechsecurite.service.CentreFormationService;
import tn.barmegtech.workshopbarmejtechsecurite.service.FormateurService;

import java.util.List;


@Service
public class FormateurServiceImpl implements FormateurService {

    @Autowired
    private FormateurRepo formateurRepo;

    @Autowired
    private CentreFormationService centreFormationService;

    @Override
    public List<FormateurModel> getFormateursParCentre(Long centreFormationId) {
        CentreFormationModel centreFormation = centreFormationService.trouverCentreParId(centreFormationId);
        return formateurRepo.findByCentreFormation(centreFormation);
    }



    @Override
    public FormateurModel ajouterFormateur(Long centreFormationId, FormateurModel formateur) {
        CentreFormationModel centreFormation = centreFormationService.trouverCentreParId(centreFormationId);
        formateur.setCentreFormation(centreFormation);
        return formateurRepo.save(formateur);
    }
}
