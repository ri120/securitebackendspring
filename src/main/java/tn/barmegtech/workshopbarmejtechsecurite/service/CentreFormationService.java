package tn.barmegtech.workshopbarmejtechsecurite.service;


import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;

import java.util.List;

public interface CentreFormationService {
    List<CentreFormationModel> getAllCentreFormations();
    CentreFormationModel getCentreFormationById(Long id);
    CentreFormationModel createCentreFormation(CentreFormationModel centreFormation);
    void deleteCentreFormation(Long id);
}
