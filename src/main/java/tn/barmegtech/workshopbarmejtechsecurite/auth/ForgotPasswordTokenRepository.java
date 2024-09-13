package tn.barmegtech.workshopbarmejtechsecurite.auth; // Déclaration du package contenant cette interface.

import java.util.Optional; // Importation de la classe Optional pour gérer les valeurs qui peuvent être nulles.

import org.springframework.data.jpa.repository.JpaRepository; // Importation de JpaRepository pour fournir des méthodes CRUD pour l'entité.
import org.springframework.data.jpa.repository.Query; // Importation de l'annotation Query pour définir des requêtes personnalisées.

import tn.barmegtech.workshopbarmejtechsecurite.entites.User; // Importation de la classe User pour l'utilisation dans les requêtes.

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Long> {
    // Déclaration de l'interface qui étend JpaRepository pour l'entité ForgotPasswordToken.
    // JpaRepository fournit des méthodes CRUD pour l'entité et gère les opérations sur la base de données.

    @Query("select fpt from ForgotPasswordToken fpt where fpt.token = ?1 and fpt.user = ?2 ")
        // Déclaration d'une requête JPQL (Java Persistence Query Language) pour rechercher un jeton de réinitialisation de mot de passe.
        // La requête sélectionne un objet ForgotPasswordToken où le jeton correspond à la valeur fournie et l'utilisateur correspond à l'utilisateur fourni.
    Optional<ForgotPasswordToken> findByTokenAndUser(Integer token, User user);
    // Méthode pour trouver un ForgotPasswordToken par son jeton et l'utilisateur associé.
    // Utilise Optional pour indiquer que le résultat peut être nul.
}
