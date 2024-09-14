package tn.barmegtech.workshopbarmejtechsecurite.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;

import java.util.Optional;

public interface CentreFormationRepo extends JpaRepository<CentreFormationModel, Long> {

    Optional<CentreFormationModel> findByEmail(String email);}