package tn.barmegtech.workshopbarmejtechsecurite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.barmegtech.workshopbarmejtechsecurite.model.SessionModel;

import java.util.List;

public interface SessionRepo extends JpaRepository<SessionModel, Long> {
        // Méthode correcte pour rechercher les sessions par centre de formation
        List<SessionModel> findByCentreDeFormation_Id(Long centreFormationId);
}
