package tn.barmegtech.workshopbarmejtechsecurite.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.repositories.CentreFormationRepo;
import tn.barmegtech.workshopbarmejtechsecurite.service.CentreFormationService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CentreFormationServiceImpl implements CentreFormationService {

    private static final Logger logger = LoggerFactory.getLogger(CentreFormationServiceImpl.class);

    @Autowired
    private CentreFormationRepo centreFormationRepo;

    @Override
    public List<CentreFormationModel> getAllCentreFormations() {
        logger.info("Fetching all CentreFormations");
        return centreFormationRepo.findAll();
    }

    @Override
    public CentreFormationModel getCentreFormationById(Long id) {
        logger.info("Fetching CentreFormation with ID: {}", id);
        return centreFormationRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("CentreFormation not found with ID: " + id));
    }

    @Override
    public CentreFormationModel createCentreFormation(CentreFormationModel centreFormation) {
        logger.info("Creating CentreFormation: {}", centreFormation);
        // Optionally, add validation here
        return centreFormationRepo.save(centreFormation);
    }

    @Override
    public void deleteCentreFormation(Long id) {
        logger.info("Deleting CentreFormation with ID: {}", id);
        if (!centreFormationRepo.existsById(id)) {
            throw new NoSuchElementException("CentreFormation not found with ID: " + id);
        }
        centreFormationRepo.deleteById(id);
    }
}
