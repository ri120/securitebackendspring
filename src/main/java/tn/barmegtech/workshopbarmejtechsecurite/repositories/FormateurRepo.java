package tn.barmegtech.workshopbarmejtechsecurite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.FormateurModel;

import java.util.List;

@Repository
public interface FormateurRepo extends JpaRepository<FormateurModel, Long> {
        List<FormateurModel> findByCentreFormation(CentreFormationModel centreFormation);
    }

