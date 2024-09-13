package tn.barmegtech.workshopbarmejtechsecurite.auth;

import java.io.UnsupportedEncodingException; // Importation pour gérer les exceptions liées à l'encodage des caractères.
import static tn.barmegtech.workshopbarmejtechsecurite.auth.UserService.applicationUrl; // Importation statique de la méthode applicationUrl pour générer des URLs.

import org.springframework.http.ResponseEntity; // Importation de ResponseEntity pour gérer les réponses HTTP.
import org.springframework.web.bind.annotation.GetMapping; // Importation de GetMapping pour définir des endpoints HTTP GET.
import org.springframework.web.bind.annotation.RequestMapping; // Importation de RequestMapping pour définir la racine de l'endpoint.
import org.springframework.web.bind.annotation.RequestParam; // Importation de RequestParam pour obtenir les paramètres de requête.

import jakarta.mail.MessagingException; // Importation pour gérer les exceptions liées aux emails.
import jakarta.servlet.http.HttpServletRequest; // Importation pour obtenir des informations sur la requête HTTP.
import lombok.RequiredArgsConstructor; // Annotation pour générer un constructeur avec tous les arguments nécessaires pour les champs final.
import org.springframework.web.bind.annotation.RestController;
import tn.barmegtech.workshopbarmejtechsecurite.Dto.Response; // Importation de la classe Response pour structurer les réponses HTTP.
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.securitemodel.VerificationToken; // Importation de la classe VerificationToken pour manipuler les tokens de vérification.
import tn.barmegtech.workshopbarmejtechsecurite.listner.RegistrationCompleteEventListener; // Importation pour gérer l'envoi des emails de vérification.

@RestController // Indique que cette classe est un contrôleur REST.
@RequiredArgsConstructor // Génère un constructeur avec tous les arguments nécessaires pour les champs final.
@RequestMapping("/api/v1/verify") // Définit la racine de l'endpoint pour les méthodes de ce contrôleur.
public class VerifyTokenController {

    private final VerificationTokenService tokenService; // Service pour gérer les tokens de vérification.
    private final RegistrationCompleteEventListener eventListener; // Listener pour gérer l'envoi des emails de vérification.

    @GetMapping("/email") // Endpoint pour vérifier l'email avec le token fourni.
    public ResponseEntity<Response> verifyEmail(@RequestParam String token) {
        // Appelle le service de vérification de token et retourne la réponse.
        return tokenService.verifyEmail(token);
    }

    @GetMapping("/resend-verification-token") // Endpoint pour renvoyer un nouveau token de vérification.
    public String resendVerificationToken(
            @RequestParam("token") String oldToken, // Paramètre de requête pour l'ancien token.
            final HttpServletRequest request // Requête HTTP pour obtenir l'URL de l'application.
    ) throws MessagingException, UnsupportedEncodingException {
        // Génère un nouveau token de vérification basé sur l'ancien token.
        VerificationToken verificationToken = tokenService.generateNewVerificationToken(oldToken);
        // Envoie un email avec le nouveau lien de vérification.
        resendVerificationTokenEmail(applicationUrl(request), verificationToken);
        // Retourne un message indiquant que le nouveau lien de vérification a été envoyé.
        return "A new verification link has been sent to your email, check to activate your verification";
    }

    private void resendVerificationTokenEmail(
            String applicationUrl, // URL de l'application pour générer le lien de vérification.
            VerificationToken verificationToken // Nouveau token de vérification à inclure dans le lien.
    ) throws MessagingException, UnsupportedEncodingException {
        // Crée le lien de vérification en utilisant l'URL de l'application et le nouveau token.
        String url = applicationUrl + "/api/v1/verify/email?token=" + verificationToken.getToken();
        // Envoie l'email de vérification avec le lien généré.
        eventListener.sendVerificationEmail(url);
    }
}
