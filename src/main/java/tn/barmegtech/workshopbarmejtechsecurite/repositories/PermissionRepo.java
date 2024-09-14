package tn.barmegtech.workshopbarmejtechsecurite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.Permission;

import java.util.List;

public interface PermissionRepo extends JpaRepository<Permission, Long> {
    List<Permission> findByCentreFormation(CentreFormationModel centreFormation);
}