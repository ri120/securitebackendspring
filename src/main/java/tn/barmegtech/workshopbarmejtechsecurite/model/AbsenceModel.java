package tn.barmegtech.workshopbarmejtechsecurite.model;

import jakarta.persistence.*;

import java.time.LocalDate;

public class AbsenceModel {
    @Entity
    public class Absence {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDate dateAbsence;

        @ManyToOne
        @JoinColumn(name = "candidat_id")
        private CandidatModel candidat;
        @ManyToOne
        @JoinColumn(name = "formateur_id")
        private FormateurModel formateur;

    }

}
