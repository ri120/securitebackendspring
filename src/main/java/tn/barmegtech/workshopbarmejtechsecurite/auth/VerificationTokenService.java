package tn.barmegtech.workshopbarmejtechsecurite.auth;

import org.springframework.http.ResponseEntity;

import tn.barmegtech.workshopbarmejtechsecurite.Dto.Response; // Importation de la classe Response du package Dto pour gérer les réponses HTTP.
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.securitemodel.VerificationToken; // Importation de la classe VerificationToken pour manipuler les tokens de vérification.
import tn.barmegtech.workshopbarmejtechsecurite.entites.User; // Importation de la classe User pour manipuler les utilisateurs.

/**
 * Interface pour la gestion des tokens de vérification des utilisateurs.
 */
public interface VerificationTokenService {

   /**
    * Sauvegarde le token de vérification associé à un utilisateur.
    * @param user L'utilisateur auquel le token est associé.
    * @param token Le token de vérification à sauvegarder.
    */
   void saveUserVerificationToken(User user, String token);

   /**
    * Valide le token de vérification.
    * @param token Le token à valider.
    * @return Un message indiquant si le token est valide ou non.
    */
   String validateToken(String token);

   /**
    * Vérifie le token de vérification et retourne une réponse.
    * @param token Le token à vérifier.
    * @return Une réponse HTTP contenant le résultat de la vérification.
    */
   ResponseEntity<Response> verifyEmail(String token);

   /**
    * Génère un nouveau token de vérification basé sur un ancien token.
    * @param oldToken L'ancien token de vérification.
    * @return Un nouveau token de vérification.
    */
   VerificationToken generateNewVerificationToken(String oldToken);
}
