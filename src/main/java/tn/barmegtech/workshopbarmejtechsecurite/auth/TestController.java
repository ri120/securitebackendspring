package tn.barmegtech.workshopbarmejtechsecurite.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour les tests.
 */
@RestController
@RequestMapping("/test") // Définir la base du chemin pour toutes les requêtes dans ce contrôleur.
public class TestController {

    /**
     * Méthode pour afficher un message avec une partie du chemin URL.
     * @param request Partie de l'URL à afficher dans le message.
     * @return Message concaténé avec la partie de l'URL.
     */
    @GetMapping("/gassen/{request}") // Mapping pour la requête GET avec une partie variable dans l'URL.
    public String Affichemessge(@PathVariable("request") String request) {
        // Retourne le message concatené avec la valeur de la partie variable de l'URL.
        return request + "bonjour";
    }
}
