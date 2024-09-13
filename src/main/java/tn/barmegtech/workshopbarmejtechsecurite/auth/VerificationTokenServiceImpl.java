package tn.barmegtech.workshopbarmejtechsecurite.auth;

import java.util.Calendar; // Importation de la classe Calendar pour manipuler les dates.
import java.util.UUID; // Importation de la classe UUID pour générer des tokens uniques.

import org.springframework.http.ResponseEntity; // Importation de la classe ResponseEntity pour gérer les réponses HTTP.
import org.springframework.stereotype.Service; // Annotation pour indiquer que cette classe est un service Spring.

import jakarta.servlet.http.HttpServletRequest; // Importation de la classe HttpServletRequest pour obtenir des informations sur la requête HTTP.
import lombok.RequiredArgsConstructor; // Annotation pour générer un constructeur avec des arguments pour les champs final.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.Response; // Importation de la classe Response du package Dto pour structurer les réponses HTTP.
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.securitemodel.VerificationToken; // Importation de la classe VerificationToken pour manipuler les tokens de vérification.
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.securitemodel.VerificationTokenRepository; // Importation du repository pour accéder aux tokens de vérification.
import tn.barmegtech.workshopbarmejtechsecurite.entites.User; // Importation de la classe User pour manipuler les utilisateurs.
import tn.barmegtech.workshopbarmejtechsecurite.repository.UserRepository; // Importation du repository pour accéder aux utilisateurs.
import static tn.barmegtech.workshopbarmejtechsecurite.auth.UserService.applicationUrl; // Importation statique de la méthode applicationUrl pour générer des URLs.

@Service // Indique que cette classe est un service Spring
@RequiredArgsConstructor // Génère un constructeur avec tous les arguments nécessaires pour les champs final
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository tokenRepository; // Repository pour accéder aux tokens de vérification.
    private final UserRepository userRepository; // Repository pour accéder aux utilisateurs.
    private final HttpServletRequest servletRequest; // Requête HTTP pour obtenir des informations sur la requête.

    @Override
    public void saveUserVerificationToken(User user, String token) {
        // Crée un nouvel objet VerificationToken avec le token et l'utilisateur, puis le sauvegarde dans le repository.
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token) {
        // Récupère le token de vérification depuis le repository.
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid verification token"; // Retourne un message si le token est invalide.
        }

        User user = verificationToken.getUser(); // Obtient l'utilisateur associé au token.

        Calendar calendar = Calendar.getInstance(); // Crée une instance de Calendar pour obtenir la date actuelle.
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "Token already expired"; // Retourne un message si le token est expiré.
        }
        userRepository.save(user); // Sauvegarde l'utilisateur dans le repository.
        return "valid"; // Retourne "valid" si le token est encore valide.
    }

    @Override
    public ResponseEntity<Response> verifyEmail(String token) {
        // Crée une URL pour obtenir un nouveau token de vérification en cas de besoin.
        String url = applicationUrl(servletRequest) + "/api/v1/verify/resend-verification-token?token=" + token;

        // Récupère le token de vérification depuis le repository.
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken.getUser().isEnabled()) {
            // Retourne une réponse indiquant que l'email a déjà été vérifié si l'utilisateur est activé.
            return ResponseEntity.ok().body(
                    Response.builder()
                            .responseMessage("This account has already been verified, please login")
                            .email(verificationToken.getUser().getEmail())
                            .build()
            );
        }

        // Valide le token et obtient le résultat de la validation.
        String verificationResult = this.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) {
            // Retourne une réponse indiquant que l'email a été vérifié avec succès.
            return ResponseEntity.ok().body(
                    Response.builder()
                            .responseMessage("Email verified successfully. Now you can login to your account")
                            .email(verificationToken.getUser().getEmail())
                            .build()
            );
        }
        // Retourne une réponse indiquant que le lien de vérification est invalide avec un lien pour obtenir un nouveau token.
        return ResponseEntity.ok().body(
                Response.builder()
                        .responseMessage("Invalid verification link, <a href=\"" + url + "\"> Get a new verification link. </a>")
                        .email(verificationToken.getUser().getEmail())
                        .build()
        );
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        // Récupère le token de vérification existant depuis le repository.
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        var verificationTokenTime = new VerificationToken(); // Crée un nouvel objet VerificationToken pour obtenir une nouvelle expiration.
        // Génère un nouveau token unique et définit sa nouvelle date d'expiration.
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(verificationTokenTime.getTokenExpirationTime());
        // Sauvegarde le nouveau token dans le repository et le retourne.
        return tokenRepository.save(verificationToken);
    }
}
