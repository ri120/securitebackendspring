package tn.barmegtech.workshopbarmejtechsecurite.service;


import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.PermissionType;



public interface CentreFormationService {
    CentreFormationModel trouverCentreParId(Long id);
    CentreFormationModel trouverParEmail(String email);
    boolean autoriserAcces(Long centreFormationId, PermissionType permissionType);
    boolean verifierPermission(Long centreFormationId, PermissionType permissionType);
}
