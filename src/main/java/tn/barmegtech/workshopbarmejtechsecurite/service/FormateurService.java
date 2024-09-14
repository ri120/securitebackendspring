package tn.barmegtech.workshopbarmejtechsecurite.service;


import tn.barmegtech.workshopbarmejtechsecurite.model.FormateurModel;

import java.util.List;

public interface FormateurService {
    List<FormateurModel> getFormateursParCentre(Long centreFormationId);
    FormateurModel ajouterFormateur(Long centreFormationId, FormateurModel formateur);
}
