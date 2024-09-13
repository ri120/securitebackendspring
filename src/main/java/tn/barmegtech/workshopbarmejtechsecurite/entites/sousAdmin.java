package tn.barmegtech.workshopbarmejtechsecurite.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tn.barmegtech.workshopbarmejtechsecurite.model.CentreFormationModel;
import tn.barmegtech.workshopbarmejtechsecurite.model.SessionModel;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder


@DiscriminatorValue("sousAdmin")
public class sousAdmin extends User {
	
	private String spcialite;
	@ManyToOne
	private CentreFormationModel centreFormation;

	//Explication : Chaque session est attribuée à un sousAdmin, qui en est responsable.
	@ManyToOne
	private sousAdmin gestionnaire;


}
