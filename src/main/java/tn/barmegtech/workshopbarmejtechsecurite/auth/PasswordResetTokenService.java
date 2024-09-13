package tn.barmegtech.workshopbarmejtechsecurite.auth; // Déclaration du package contenant cette interface.

import org.springframework.http.ResponseEntity; // Importation de la classe ResponseEntity pour encapsuler les réponses HTTP.

import tn.barmegtech.workshopbarmejtechsecurite.Email.ChangePasswordResetRequest; // Importation de la classe ChangePasswordResetRequest pour la demande de réinitialisation de mot de passe.

public interface PasswordResetTokenService {
   // Déclaration de l'interface pour gérer les services liés aux jetons de réinitialisation de mot de passe.

   /**
    * Vérifie l'adresse e-mail fournie pour la réinitialisation du mot de passe.
    * @param email L'adresse e-mail à vérifier.
    * @return Une réponse HTTP contenant un message indiquant si la vérification a réussi ou échoué.
    */
   ResponseEntity<String> verifyEmail(String email);
   // Méthode pour vérifier si l'adresse e-mail fournie est valide pour la réinitialisation du mot de passe.

   /**
    * Vérifie le code OTP fourni pour l'adresse e-mail spécifiée.
    * @param otp Le code OTP à vérifier.
    * @param email L'adresse e-mail associée au code OTP.
    * @return Une réponse HTTP contenant un message indiquant si la vérification du code OTP a réussi ou échoué.
    */
   ResponseEntity<String> verifyOtp(Integer otp, String email);
   // Méthode pour vérifier un code OTP fourni pour l'adresse e-mail spécifiée.

   /**
    * Gère la demande de changement de mot de passe.
    * @param changePasswordResetRequest Les détails de la demande de réinitialisation de mot de passe.
    * @param email L'adresse e-mail associée à la demande.
    * @return Une réponse HTTP contenant un message indiquant si le changement de mot de passe a réussi ou échoué.
    */
   ResponseEntity<String> changePasswordHandler(
           ChangePasswordResetRequest changePasswordResetRequest,
           String email
   );
   // Méthode pour traiter une demande de réinitialisation de mot de passe en utilisant les détails fournis.
}
