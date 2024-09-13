package tn.barmegtech.workshopbarmejtechsecurite.auth;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.barmegtech.workshopbarmejtechsecurite.Email.ChangePasswordResetRequest;
import tn.barmegtech.workshopbarmejtechsecurite.Email.EmailDetails;
import tn.barmegtech.workshopbarmejtechsecurite.Email.EmailService;
import tn.barmegtech.workshopbarmejtechsecurite.entites.User;
import tn.barmegtech.workshopbarmejtechsecurite.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final UserRepository userRepository; // Repository pour accéder aux informations des utilisateurs.
    private final ForgotPasswordTokenRepository forgotPasswordRepository; // Repository pour accéder aux jetons de réinitialisation de mot de passe.
    private final EmailService emailService; // Service pour envoyer des emails.
    private final PasswordEncoder passwordEncoder; // Encodeur pour sécuriser les mots de passe.

    /**
     * Envoie un email de vérification avec un OTP (One-Time Password) pour la réinitialisation du mot de passe.
     * @param email L'adresse email à vérifier.
     * @return Une réponse HTTP indiquant si l'email a été envoyé avec succès.
     */
    public ResponseEntity<String> verifyEmail(String email) {
        // Recherche de l'utilisateur par email. Lève une exception si l'utilisateur n'est pas trouvé.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        // Génération d'un OTP aléatoire.
        int token = otpGenerator();

        // Création des détails de l'email à envoyer.
        EmailDetails mailBody = EmailDetails
                .builder()
                .to(email)
                .subject("OTP for Forgot Password request")
                .messageBody("This is the OTP for your Forgot Password request : " + token)
                .build();

        // Création du jeton de réinitialisation de mot de passe avec une date d'expiration.
        ForgotPasswordToken fp = ForgotPasswordToken
                .builder()
                .token(token)
                .expirationTime(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // Expiration après 24 heures.
                .user(user)
                .build();

        // Envoi de l'email contenant l'OTP.
        emailService.sendSimpleMail(mailBody);

        // Sauvegarde du jeton dans la base de données.
        forgotPasswordRepository.save(fp);

        // Retourne une réponse HTTP indiquant que l'email a été envoyé avec succès.
        return ResponseEntity.ok("Email sent for verification successfully");
    }

    /**
     * Vérifie le code OTP fourni et l'adresse email associée.
     * @param token Le code OTP à vérifier.
     * @param email L'adresse email associée au code OTP.
     * @return Une réponse HTTP indiquant si le code OTP est valide ou a expiré.
     */
    public ResponseEntity<String> verifyOtp(Integer token, String email) {
        // Recherche de l'utilisateur par email. Lève une exception si l'utilisateur n'est pas trouvé.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        // Recherche du jeton de réinitialisation de mot de passe par token et utilisateur.
        ForgotPasswordToken fp = forgotPasswordRepository.findByTokenAndUser(token, user)
                .orElseThrow(() -> new RuntimeException("Invalid OTP for email " + email));

        // Vérification si le jeton a expiré.
        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getId()); // Suppression du jeton expiré.
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }

        // Retourne une réponse HTTP indiquant que le code OTP a été vérifié avec succès.
        return ResponseEntity.ok("OTP verified");
    }

    /**
     * Gère la demande de changement de mot de passe en vérifiant les mots de passe fournis et en les mettant à jour.
     * @param changePassword La demande de changement de mot de passe contenant les nouveaux mots de passe.
     * @param email L'adresse email de l'utilisateur pour mettre à jour le mot de passe.
     * @return Une réponse HTTP indiquant si le mot de passe a été changé avec succès.
     */
    public ResponseEntity<String> changePasswordHandler(
            ChangePasswordResetRequest changePassword,
            String email
    ) {
        // Vérification si les mots de passe fournis correspondent.
        boolean areEqual = (changePassword.getNewPassword()).equals(changePassword.getConfirmationPassword());
        if (!areEqual) {
            return new ResponseEntity<>("Passwords do not match. Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        // Encodage du nouveau mot de passe.
        String encodedPassword = passwordEncoder.encode(changePassword.getNewPassword());
        userRepository.updatePassword(email, encodedPassword); // Mise à jour du mot de passe dans la base de données.

        // Retourne une réponse HTTP indiquant que le mot de passe a été changé avec succès.
        return ResponseEntity.ok("Password has been successfully changed!");
    }

    /**
     * Génère un OTP (One-Time Password) aléatoire à 6 chiffres.
     * @return Un code OTP aléatoire.
     */
    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999); // Génération d'un OTP entre 100000 et 999999.
    }
}
