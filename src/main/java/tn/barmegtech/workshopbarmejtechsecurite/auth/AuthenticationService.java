package tn.barmegtech.workshopbarmejtechsecurite.auth; // Déclaration du package contenant cette classe.

import static tn.barmegtech.workshopbarmejtechsecurite.auth.UserService.applicationUrl; // Importation statique de la méthode applicationUrl depuis UserService.

import java.io.IOException; // Importation de l'exception IOException pour gérer les erreurs d'entrée/sortie.
import java.util.HashMap; // Importation de la classe HashMap pour utiliser des cartes clé-valeur.

import org.springframework.context.ApplicationEventPublisher; // Importation de la classe ApplicationEventPublisher pour publier des événements dans l'application.
import org.springframework.http.HttpHeaders; // Importation de la classe HttpHeaders pour manipuler les en-têtes HTTP.
import org.springframework.http.HttpStatus; // Importation de la classe HttpStatus pour définir les statuts des réponses HTTP.
import org.springframework.http.ResponseEntity; // Importation de la classe ResponseEntity pour encapsuler les réponses HTTP.
import org.springframework.security.authentication.AuthenticationManager; // Importation de la classe AuthenticationManager pour gérer l'authentification.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Importation de la classe UsernamePasswordAuthenticationToken pour créer des jetons d'authentification.
import org.springframework.security.crypto.password.PasswordEncoder; // Importation de la classe PasswordEncoder pour encoder les mots de passe.
import org.springframework.stereotype.Service; // Importation de l'annotation Service pour indiquer que cette classe est un service Spring.

import com.fasterxml.jackson.databind.ObjectMapper; // Importation de la classe ObjectMapper pour manipuler des objets JSON.

import jakarta.annotation.PostConstruct; // Importation de l'annotation PostConstruct pour exécuter du code après la construction du bean.
import jakarta.servlet.http.HttpServletRequest; // Importation de la classe HttpServletRequest pour manipuler les requêtes HTTP.
import jakarta.servlet.http.HttpServletResponse; // Importation de la classe HttpServletResponse pour manipuler les réponses HTTP.
import lombok.RequiredArgsConstructor; // Importation de l'annotation RequiredArgsConstructor pour générer un constructeur avec des arguments pour tous les champs final.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.AuthenticationRequest; // Importation de la classe AuthenticationRequest pour les demandes d'authentification.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.AuthenticationResponse; // Importation de la classe AuthenticationResponse pour les réponses d'authentification.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.superAdminDto; // Importation de la classe EleveDto pour les données utilisateur.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.RegisterRequeste; // Importation de la classe RegisterRequeste pour les demandes d'inscription.
import tn.barmegtech.workshopbarmejtechsecurite.Dto.Response; // Importation de la classe Response pour les réponses générales.
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.JwtService; // Importation de la classe JwtService pour gérer les jetons JWT.
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.securitemodel.Token; // Importation de la classe Token pour les jetons d'authentification.
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.securitemodel.TokenRepository; // Importation de la classe TokenRepository pour accéder au dépôt de jetons.
import tn.barmegtech.workshopbarmejtechsecurite.configsecurite.securitemodel.TokenType; // Importation de la classe TokenType pour définir les types de jetons.
import tn.barmegtech.workshopbarmejtechsecurite.entites.superAdmin; // Importation de la classe Eleve pour les entités utilisateur de type Eleve.
import tn.barmegtech.workshopbarmejtechsecurite.entites.Erole; // Importation de la classe Erole pour définir les rôles des utilisateurs.
import tn.barmegtech.workshopbarmejtechsecurite.entites.User; // Importation de la classe User pour les entités utilisateur.
import tn.barmegtech.workshopbarmejtechsecurite.listner.RegistrationCompleteEvent; // Importation de la classe RegistrationCompleteEvent pour les événements d'inscription.
import tn.barmegtech.workshopbarmejtechsecurite.repository.UserRepository; // Importation de la classe UserRepository pour accéder au dépôt d'utilisateurs.

@Service // Indique que cette classe est un service Spring, ce qui signifie qu'elle est gérée par le conteneur Spring.
@RequiredArgsConstructor // Génère un constructeur avec des arguments pour tous les champs final de cette classe.
public class AuthenticationService {

    private final UserRepository repository; // Déclaration du dépôt d'utilisateurs, injecté par le constructeur.
    private final TokenRepository tokenRepository; // Déclaration du dépôt de jetons, injecté par le constructeur.
    private final PasswordEncoder passwordEncoder; // Déclaration du gestionnaire de l'encodage des mots de passe, injecté par le constructeur.
    private final JwtService jwtService; // Déclaration du service JWT, injecté par le constructeur.
    private final AuthenticationManager authenticationManager; // Déclaration du gestionnaire d'authentification, injecté par le constructeur.
    private final ApplicationEventPublisher publisher; // Déclaration du publieur d'événements, injecté par le constructeur.

    // Méthode pour enregistrer un nouvel utilisateur
    public ResponseEntity<Response> register(RegisterRequeste userRequest, final HttpServletRequest request) {

        // Vérifie si un utilisateur avec le même e-mail existe déjà dans la base de données.
        boolean userExists = repository.findAll()
                .stream()
                .anyMatch(user -> userRequest.getEmail().equalsIgnoreCase(user.getEmail()));

        if (userExists) {
            // Si l'utilisateur existe, renvoie une réponse HTTP 400 Bad Request avec un message d'erreur.
            return ResponseEntity.badRequest().body(Response.builder()
                    .responseMessage("User with provided email already exists!")
                    .build());
        }
/*  if (userRequest.getRole().equalsIgnoreCase("1")) {

    var user = User.builder()
       .firstName(userRequest.getFirstName())
       .lastName(userRequest.getLastName())
        .email(userRequest.getEmail())
        .password(passwordEncoder.encode(userRequest.getPassword()))
        .role(Role.VENDEUR)
        .phone(userRequest.getPhone())
        .adress(userRequest.getAdress())
        //.role(request.getRole())
        .build();
    var savedUser = repository.save(user);

    //publisher.publishEvent(new RegistrationCompleteEvent(savedUser, applicationUrl(request)));

    return new ResponseEntity<>(
            Response.builder()

                    .responseMessage("Success! Please, check your email to complete your registration")
                    .email(savedUser.getEmail())

                    .build(),
            HttpStatus.CREATED
    );}*/
        // Enregistre un nouvel utilisateur si le rôle est "superAdmin".
        if (userRequest instanceof superAdminDto) {
            superAdmin user = new superAdmin();
            user = superAdminDto.Toentite((superAdminDto) userRequest); // Convertit EleveDto en entité Eleve.
            user.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Encode le mot de passe avant de le sauvegarder.
            user.setRole(Erole.superAdmin); // Définit le rôle de l'utilisateur comme ELEVE.
            var savedUser = repository.save(user); // Sauvegarde l'utilisateur dans la base de données.
            publisher.publishEvent(new RegistrationCompleteEvent(savedUser, applicationUrl(request))); // Publie un événement d'inscription complétée.

            // Renvoie une réponse HTTP 201 Created avec un message de succès.
            return new ResponseEntity<>(
                    Response.builder()
                            .responseMessage("Success! Please, check your email to complete your registration")
                            .email(savedUser.getEmail())
                            .build(),
                    HttpStatus.CREATED
            );
        }
        return null; // Renvoie null si aucun des cas précédents n'est satisfait.
    }

    // Méthode pour créer un utilisateur administrateur par défaut après la construction du bean.
    @PostConstruct
    public void createDefaultAdmin() {
        User userADM = new User(); // Création d'un nouvel utilisateur administrateur.
        String email50 = "adm@mail.com"; // E-mail de l'utilisateur administrateur par défaut.

        // Vérifie si l'utilisateur administrateur avec cet e-mail existe déjà.
        if (!repository.existsByEmail(email50)) {
            userADM.setEmail("adm@mail.com"); // Définit l'e-mail de l'utilisateur administrateur.
            userADM.setFullname("ahmed naili"); // Définit le nom complet de l'utilisateur administrateur.
            userADM.setPassword(passwordEncoder.encode("adm")); // Encode le mot de passe avant de le sauvegarder.
            userADM.setRole(Erole.sousAdmin); // Définit le rôle de l'utilisateur comme PROFFSSEUR.
            repository.save(userADM); // Sauvegarde l'utilisateur administrateur par défaut dans la base de données.
        }
    }

    // Méthode pour authentifier un utilisateur et générer des jetons JWT.
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authentifie l'utilisateur en utilisant le gestionnaire d'authentification.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), // Utilise l'e-mail pour l'authentification.
                        request.getPassword() // Utilise le mot de passe pour l'authentification.
                )
        );

        // Récupère l'utilisateur par e-mail ou lance une exception si l'utilisateur n'existe pas.
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var claims = new HashMap<String, Object>(); // Crée un nouveau HashMap pour stocker les revendications du jeton.
        claims.put("fullname", user.getFullname()); // Ajoute le nom complet de l'utilisateur aux revendications du jeton.
        claims.put("userId", user.getId()); // Ajoute l'identifiant de l'utilisateur aux revendications du jeton.

        var jwtToken = jwtService.generateToken(claims, user); // Génère un jeton JWT avec les revendications et l'utilisateur.
        var refreshToken = jwtService.generateRefreshToken(user); // Génère un jeton de rafraîchissement pour l'utilisateur.
        saveUserToken(user, jwtToken); // Sauvegarde le jeton JWT dans la base de données.

        // Renvoie une réponse d'authentification contenant les jetons d'accès et de rafraîchissement.
        return AuthenticationResponse.builder()
                .accessToken(jwtToken) // Définit le jeton d'accès dans la réponse.
                .refreshToken(refreshToken) // Définit le jeton de rafraîchissement dans la réponse.
                .build();
    }

    // Méthode pour sauvegarder un jeton utilisateur dans la base de données.
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user) // Associe le jeton à l'utilisateur.
                .token(jwtToken) // Définit le jeton JWT.
                .tokenType(TokenType.BEARER) // Définit le type de jeton comme BEARER.
                .expired(false) // Définit le jeton comme non expiré.
                .revoked(false) // Définit le jeton comme non révoqué.
                .build();
        tokenRepository.save(token); // Sauvegarde le jeton dans la base de données.
    }

    // Méthode pour révoquer tous les jetons valides d'un utilisateur.
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId()); // Récupère tous les jetons valides de l'utilisateur.
        if (validUserTokens.isEmpty())
            return; // Si aucun jeton valide n'est trouvé, retourne sans rien faire.

        validUserTokens.forEach(token -> {
            token.setExpired(true); // Marque le jeton comme expiré.
            token.setRevoked(true); // Marque le jeton comme révoqué.
        });
        tokenRepository.saveAll(validUserTokens); // Sauvegarde les jetons révoqués dans la base de données.
    }

    // Méthode pour rafraîchir le jeton d'accès d'un utilisateur.
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // Récupère l'en-tête d'autorisation de la requête.
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return; // Si l'en-tête est absent ou ne commence pas par "Bearer ", retourne sans rien faire.
        }
        refreshToken = authHeader.substring(7); // Extrait le jeton de rafraîchissement de l'en-tête.
        userEmail = jwtService.extractUsername(refreshToken); // Extrait l'e-mail de l'utilisateur à partir du jeton de rafraîchissement.

        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow(); // Récupère l'utilisateur par e-mail ou lance une exception si l'utilisateur n'existe pas.
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user); // Génère un nouveau jeton d'accès.
                revokeAllUserTokens(user); // Révoque tous les jetons valides de l'utilisateur.
                saveUserToken(user, accessToken); // Sauvegarde le nouveau jeton d'accès dans la base de données.
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken) // Définit le jeton d'accès dans la réponse.
                        .refreshToken(refreshToken) // Définit le jeton de rafraîchissement dans la réponse.
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse); // Écrit la réponse JSON dans le flux de sortie.
            }
        }
    }
}
