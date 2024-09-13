package tn.barmegtech.workshopbarmejtechsecurite.auth; // Déclaration du package dans lequel se trouve cette classe.

import jakarta.servlet.http.HttpServletRequest; // Importation de la classe HttpServletRequest pour manipuler les requêtes HTTP.
import jakarta.servlet.http.HttpServletResponse; // Importation de la classe HttpServletResponse pour manipuler les réponses HTTP.
import jakarta.validation.Valid; // Importation de l'annotation Valid pour valider les objets.
import lombok.RequiredArgsConstructor; // Importation de Lombok pour générer un constructeur avec des arguments pour tous les champs final.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.AuthenticationRequest; // Importation de la classe AuthenticationRequest pour les demandes d'authentification.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.AuthenticationResponse; // Importation de la classe AuthenticationResponse pour les réponses d'authentification.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.superAdminDto; // Importation de la classe EleveDto pour les données utilisateur.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.Response; // Importation de la classe Response pour les réponses générales.

import org.springframework.http.ResponseEntity; // Importation de la classe ResponseEntity pour gérer les réponses HTTP.
import org.springframework.web.bind.annotation.PostMapping; // Importation de l'annotation PostMapping pour mapper les requêtes POST.
import org.springframework.web.bind.annotation.RequestBody; // Importation de l'annotation RequestBody pour indiquer que le paramètre est lié au corps de la requête HTTP.
import org.springframework.web.bind.annotation.RequestMapping; // Importation de l'annotation RequestMapping pour définir le chemin de base pour toutes les méthodes du contrôleur.
import org.springframework.web.bind.annotation.RestController; // Importation de l'annotation RestController pour indiquer que cette classe est un contrôleur REST.

import java.io.IOException; // Importation de l'exception IOException pour gérer les erreurs d'entrée/sortie.


@RestController // Indique que cette classe est un contrôleur REST, ce qui signifie que les méthodes renvoient des données sous forme de JSON ou XML.
@RequestMapping("/api/v1/auth") // Définie le chemin de base pour toutes les méthodes de ce contrôleur. Ici, toutes les requêtes commenceront par "/api/v1/auth".
@RequiredArgsConstructor // Génère un constructeur avec des arguments pour tous les champs final.
public class AuthenticationController {

  private final AuthenticationService service; // Déclaration du service d'authentification, injecté par le constructeur.
  private final UserService userService; // Déclaration du service utilisateur, injecté par le constructeur.

  @PostMapping("/register") // Spécifie que cette méthode répond aux requêtes POST envoyées à "/api/v1/auth/register".
  public ResponseEntity<Response> register(
          @RequestBody @Valid superAdminDto userRequest, // Reçoit les données de la requête sous forme d'objet EleveDto, avec validation automatique.
          HttpServletRequest request // Reçoit l'objet HttpServletRequest pour obtenir des informations supplémentaires sur la requête.
  )  {
    return service.register(userRequest, request); // Appelle la méthode register du service d'authentification et renvoie une réponse HTTP encapsulée dans un ResponseEntity.
  }

  @PostMapping("/authenticate") // Spécifie que cette méthode répond aux requêtes POST envoyées à "/api/v1/auth/authenticate".
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request // Reçoit les données de la requête sous forme d'objet AuthenticationRequest.
  ) {
    return ResponseEntity.ok(service.authenticate(request)); // Appelle la méthode authenticate du service d'authentification et renvoie une réponse HTTP avec le statut 200 OK.
  }

  @PostMapping("/refresh-token") // Spécifie que cette méthode répond aux requêtes POST envoyées à "/api/v1/auth/refresh-token".
  public void refreshToken(
          HttpServletRequest request, // Reçoit l'objet HttpServletRequest pour obtenir des informations supplémentaires sur la requête.
          HttpServletResponse response // Reçoit l'objet HttpServletResponse pour envoyer la réponse HTTP.
  ) throws IOException { // Déclare que cette méthode peut lancer une IOException.
    service.refreshToken(request, response); // Appelle la méthode refreshToken du service d'authentification pour traiter la demande de rafraîchissement de jeton.
  }
}
