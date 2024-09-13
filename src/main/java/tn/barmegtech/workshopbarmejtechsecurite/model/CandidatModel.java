package tn.barmegtech.workshopbarmejtechsecurite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

public class CandidatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String telephone;
//Explication : Ces relations permettent d'associer plusieurs formateurs et candidats à chaque session.
    @ManyToMany
    private List<FormateurModel> formateurs;

    @ManyToMany
    private List<CandidatModel> candidats;

}
