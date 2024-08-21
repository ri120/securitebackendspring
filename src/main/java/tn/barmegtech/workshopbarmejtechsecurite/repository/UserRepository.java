package tn.barmegtech.workshopbarmejtechsecurite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.barmegtech.workshopbarmejtechsecurite.entites.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
