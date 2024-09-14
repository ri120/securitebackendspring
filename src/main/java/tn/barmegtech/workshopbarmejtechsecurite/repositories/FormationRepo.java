package tn.barmegtech.workshopbarmejtechsecurite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.FormationModel;

import java.util.List;


@Repository
public interface FormationRepo extends JpaRepository<FormationModel, Long> {
    List<FormationModel> findByCentreFormation(CentreFormationModel centreFormation);
}
