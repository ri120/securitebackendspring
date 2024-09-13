package tn.barmegtech.workshopbarmejtechsecurite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class FormationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private int duree; // durée de la formation en jours
//Explication : Une table d'association session_formation sera créée pour gérer la relation entre SessionModel et FormationModel,
// avec les colonnes session_id et formation_id pour les clés étrangères.
    @ManyToMany
    @JoinTable(
            name = "session_formation",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "formation_id")
    )
    private List<FormationModel> formations = new ArrayList<>();

}
