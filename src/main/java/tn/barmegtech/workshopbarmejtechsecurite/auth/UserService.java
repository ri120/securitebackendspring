package tn.barmegtech.workshopbarmejtechsecurite.auth;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import tn.barmegtech.workshopbarmejtechsecurite.Email.ChangePasswordRequest;
import tn.barmegtech.workshopbarmejtechsecurite.entites.User;
import tn.barmegtech.workshopbarmejtechsecurite.repository.UserRepository;

/**
 * Service pour la gestion des utilisateurs.
 */
@Service
@RequiredArgsConstructor // Génère un constructeur avec tous les arguments nécessaires pour les dépendances.
public class UserService {

    private final PasswordEncoder passwordEncoder; // Encodeur de mot de passe pour la gestion des mots de passe.
    private final UserRepository repository; // Répository pour accéder aux données des utilisateurs.

    /**
     * Permet à un utilisateur de changer son mot de passe.
     * @param request La demande de changement de mot de passe contenant les mots de passe actuels et nouveaux.
     * @param connectedUser L'utilisateur actuellement connecté.
     */
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        // Récupère l'utilisateur actuellement connecté.
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // Vérifie si le mot de passe actuel est correct.
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // Vérifie si les deux nouveaux mots de passe sont identiques.
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }

        // Met à jour le mot de passe de l'utilisateur.
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Sauvegarde le nouvel mot de passe dans la base de données.
        repository.save(user);
    }

    /**
     * Récupère un utilisateur par son email.
     * @param email L'email de l'utilisateur à récupérer.
     * @return L'utilisateur correspondant à l'email, ou null si l'utilisateur n'existe pas.
     */
    public User fetchUser(String email) {
        return repository.existsByEmail(email)
                ? repository.findByEmail(email).get()
                : null;
    }

    /**
     * Génère l'URL de l'application basée sur la requête HTTP.
     * @param request La requête HTTP.
     * @return L'URL complète de l'application.
     */
    public static String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    // Exemples de méthodes commentées pour la gestion des comptes et la conversion d'entités en DTO.
    /*
    public List<UserDto> listvendeur(ModeleRole role) {
        List<User> users = repository.listusersByRole(role.getRole());
        return users.stream().map(x -> UserDto.formUser(x)).collect(Collectors.toList());
    }

    public List<StagiaireDTO> entityToDto(List<Stagiaire> listStagiaire) {
        return listStagiaire.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }
    */
}
