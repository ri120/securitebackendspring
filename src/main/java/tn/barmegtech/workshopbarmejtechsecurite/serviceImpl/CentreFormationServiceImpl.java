package tn.barmegtech.workshopbarmejtechsecurite.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.Permission;
import tn.barmegtech.workshopbarmejtechsecurite.model.PermissionType;
import tn.barmegtech.workshopbarmejtechsecurite.repositories.CentreFormationRepo;
import tn.barmegtech.workshopbarmejtechsecurite.repositories.PermissionRepo;
import tn.barmegtech.workshopbarmejtechsecurite.service.CentreFormationService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CentreFormationServiceImpl implements CentreFormationService {



    @Autowired
    private CentreFormationRepo centreFormationRepo;


    @Autowired
    private PermissionRepo permissionRepository;

    @Override
    public CentreFormationModel trouverCentreParId(Long id) {
        return centreFormationRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Centre non trouvé"));
    }

    @Override
    public CentreFormationModel trouverParEmail(String email) {
        return centreFormationRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Centre non trouvé"));
    }

    @Override
    public boolean autoriserAcces(Long centreFormationId, PermissionType permissionType) {
        CentreFormationModel centreFormation = trouverCentreParId(centreFormationId);
        Permission permission = new Permission();
        permission.setCentreFormation(centreFormation);
        permission.setType(permissionType);

        permissionRepository.save(permission);
        return true;
    }

    @Override
    public boolean verifierPermission(Long centreFormationId, PermissionType permissionType) {
        CentreFormationModel centreFormation = trouverCentreParId(centreFormationId);
        List<Permission> permissions = permissionRepository.findByCentreFormation(centreFormation);

        return permissions.stream()
                .anyMatch(permission -> permission.getType().equals(permissionType));
    }
}
