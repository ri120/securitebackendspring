package tn.barmegtech.workshopbarmejtechsecurite.service;


import tn.barmegtech.workshopbarmejtechsecurite.model.FormationModel;

import java.util.List;

public interface FormationService {
    List<FormationModel> getFormationsParCentre(Long centreFormationId);
    FormationModel ajouterFormation(Long centreFormationId, FormationModel formation);
}
