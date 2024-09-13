package tn.barmegtech.workshopbarmejtechsecurite.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CentreFormationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String adresse;

    private String telephone;

    private String email;



// un centre de formation peut avoir plusieurs sessions, il est préférable de changer la relation en @ManyToOne :
    @ManyToOne
    @JoinColumn(name = "centre_de_formation_id")
    private CentreFormationModel centreDeFormation;

}
