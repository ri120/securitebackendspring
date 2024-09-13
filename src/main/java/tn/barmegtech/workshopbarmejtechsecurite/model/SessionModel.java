package tn.barmegtech.workshopbarmejtechsecurite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tn.barmegtech.workshopbarmejtechsecurite.entites.sousAdmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class SessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateDebut;
    private Date dateFin;
    private String etat;

    @OneToOne
    @JoinColumn(name = "centre_de_formation_id", unique = true)
    private CentreFormationModel centreDeFormation;

//*Explication : La colonne centre_de_formation_id dans la table SessionModel sera utilisée pour stocker la référence unique au centre de formation associé. Le unique = true impose une contrainte d'unicité,
 //garantissant qu'une session ne peut être associée qu'à un seul centre de formation à la fois
    @ManyToMany
    @JoinTable(
            name = "session_formation",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "formation_id")
    )
    private List<FormationModel> formations = new ArrayList<>();

    @ManyToMany
    private List<FormateurModel> formateurs;

    @ManyToMany
    private List<CandidatModel> candidats;



    @ManyToOne
    private sousAdmin gestionnaire;

}

