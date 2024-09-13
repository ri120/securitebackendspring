package tn.barmegtech.workshopbarmejtechsecurite.service;

import org.springframework.stereotype.Service;
import tn.barmegtech.workshopbarmejtechsecurite.entites.sousAdmin;

import java.util.List;

public interface SousAdminService {
    List<sousAdmin> getAllSousAdmins();
    sousAdmin getSousAdminById(Long id);
    sousAdmin createSousAdmin(sousAdmin sousAdmin);
    void deleteSousAdmin(Long id);
}
