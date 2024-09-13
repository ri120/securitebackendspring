package tn.barmegtech.workshopbarmejtechsecurite.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.barmegtech.workshopbarmejtechsecurite.Email.ChangePasswordResetRequest;
import tn.barmegtech.workshopbarmejtechsecurite.entites.User;

/**
 * Contrôleur REST pour les opérations liées aux utilisateurs.
 */
@RestController
@RequestMapping("/api/v1/users") // Définit la base de l'URL pour toutes les requêtes dans ce contrôleur.
@RequiredArgsConstructor // Génère un constructeur avec tous les arguments nécessaires pour les dépendances.
@CrossOrigin(origins = "http://localhost:4200") // Permet les requêtes CORS depuis le frontend local.
public class UserController {

    private final UserService service; // Service pour gérer les opérations liées aux utilisateurs.
    private final PasswordResetTokenService passwordResetTokenService; // Service pour gérer les réinitialisations de mot de passe.

    /**
     * Récupère les informations d'un utilisateur par email.
     * @param email L'email de l'utilisateur à récupérer.
     * @return Les informations de l'utilisateur.
     */
    @GetMapping("/{email}")
    public User fetchUser(@PathVariable String email) {
        return service.fetchUser(email);
    }

    /**
     * Envoie un email pour vérifier l'email fourni pour la réinitialisation du mot de passe.
     * @param email L'email pour lequel envoyer la demande de réinitialisation.
     * @return Réponse indiquant si l'email a été envoyé avec succès.
     */
    @PostMapping("/resetrequestpassword/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        return passwordResetTokenService.verifyEmail(email);
    }

    /**
     * Vérifie le code OTP envoyé pour la réinitialisation du mot de passe.
     * @param otp Le code OTP à vérifier.
     * @param email L'email associé au code OTP.
     * @return Réponse indiquant si l'OTP est valide ou non.
     */
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        return passwordResetTokenService.verifyOtp(otp, email);
    }

    /**
     * Permet à un utilisateur de changer son mot de passe après vérification de l'OTP.
     * @param changePassword Les détails de la demande de changement de mot de passe.
     * @param email L'email de l'utilisateur qui change son mot de passe.
     * @return Réponse indiquant si le mot de passe a été changé avec succès.
     */
    @PostMapping("/resetPassword/{email}")
    public ResponseEntity<String> changePasswordHandler(
            @RequestBody ChangePasswordResetRequest changePassword,
            @PathVariable String email
    ) {
        return passwordResetTokenService.changePasswordHandler(changePassword, email);
    }
}
