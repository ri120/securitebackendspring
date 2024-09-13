package tn.barmegtech.workshopbarmejtechsecurite.auth; // Déclaration du package contenant cette classe.

import jakarta.persistence.*; // Importation des annotations de JPA pour la gestion des entités et des relations.
import lombok.AllArgsConstructor; // Importation de l'annotation AllArgsConstructor pour générer un constructeur avec tous les arguments.
import lombok.Builder; // Importation de l'annotation Builder pour permettre la création d'objets via un constructeur fluide.
import lombok.Data; // Importation de l'annotation Data pour générer automatiquement les getters, setters, equals, hashCode et toString.
import lombok.NoArgsConstructor; // Importation de l'annotation NoArgsConstructor pour générer un constructeur sans arguments.
import tn.barmegtech.workshopbarmejtechsecurite.entites.User; // Importation de la classe User pour la relation avec l'entité utilisateur.

import org.hibernate.annotations.CreationTimestamp; // Importation de l'annotation CreationTimestamp pour définir automatiquement la date de création.

import java.time.LocalDateTime; // Importation de la classe LocalDateTime pour manipuler les dates et heures.
import java.util.Calendar; // Importation de la classe Calendar pour effectuer des opérations sur les dates.
import java.util.Date; // Importation de la classe Date pour manipuler les dates.

@Data // Génère les getters, setters, equals, hashCode et toString pour la classe.
@Entity // Indique que cette classe est une entité JPA, c'est-à-dire qu'elle sera mappée à une table de la base de données.
@NoArgsConstructor // Génère un constructeur sans arguments.
@AllArgsConstructor // Génère un constructeur avec tous les arguments.
@Builder // Permet de créer des objets via un constructeur fluide (pattern builder).
@Table(name = "forgot_password_token") // Spécifie le nom de la table dans la base de données.
public class ForgotPasswordToken {

    @Id // Indique que ce champ est la clé primaire de l'entité.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indique que la clé primaire est générée automatiquement par la base de données.
    private Long id; // Identifiant unique du jeton de réinitialisation de mot de passe.

    private Integer token; // Valeur du jeton de réinitialisation de mot de passe.

    private Date expirationTime; // Heure d'expiration du jeton.

    private static final int EXPIRATION_TIME = 1; // Durée de validité du jeton en minutes.

    @OneToOne // Indique une relation un-à-un avec une autre entité.
    @JoinColumn(name = "user_id") // Spécifie le nom de la colonne dans la table qui fait référence à l'entité User.
    private User user; // Référence à l'utilisateur associé à ce jeton.

    @CreationTimestamp // Déclare que ce champ sera automatiquement rempli avec la date de création de l'entité.
    @Column(name = "created_at") // Spécifie le nom de la colonne dans la table.
    private LocalDateTime createdAt; // Date et heure de la création du jeton.

    // Méthode pour obtenir le temps d'expiration du jeton.
    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance(); // Crée une instance de Calendar pour manipuler les dates.
        calendar.setTimeInMillis(new Date().getTime()); // Définit le temps du calendrier à l'heure actuelle.
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME); // Ajoute la durée d'expiration au calendrier.
        return new Date(calendar.getTime().getTime()); // Retourne la date d'expiration sous forme d'objet Date.
    }
}
