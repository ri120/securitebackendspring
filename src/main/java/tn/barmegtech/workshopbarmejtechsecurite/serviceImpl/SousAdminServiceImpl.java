package tn.barmegtech.workshopbarmejtechsecurite.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.entites.sousAdmin;
import tn.barmegtech.workshopbarmejtechsecurite.repository.sousAdminRepository;
import tn.barmegtech.workshopbarmejtechsecurite.service.SousAdminService;
import java.util.NoSuchElementException;

import java.util.List;
@Service
public class SousAdminServiceImpl implements SousAdminService {
  @Autowired
    private sousAdminRepository sousAdminRepo;
    @Override
    public List<sousAdmin> getAllSousAdmins() {

        return sousAdminRepo.findAll();
    }

    @Override
    public sousAdmin getSousAdminById(Long id) {
        return sousAdminRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("SousAdmin not found"));
    }

    @Override
    public sousAdmin createSousAdmin(sousAdmin sousAdmin) {
        return sousAdminRepo.save(sousAdmin);
    }

    @Override
    public void deleteSousAdmin(Long id) {
        sousAdminRepo.deleteById(id);
    }
}
