package tn.barmegtech.workshopbarmejtechsecurite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.barmegtech.workshopbarmejtechsecurite.model.CandidatModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;

import java.util.List;

@Repository
public interface CandidatRepo  extends JpaRepository<CandidatModel, Long> {
    List<CandidatModel> findByCentreFormation(CentreFormationModel centreFormation);
}


