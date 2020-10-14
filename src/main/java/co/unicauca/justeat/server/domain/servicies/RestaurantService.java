package co.unicauca.justeat.server.domain.servicies;

import co.unicauca.justeat.commons.domain.Restaurant;
import co.unicauca.justeat.commons.infra.JsonError;
import co.unicauca.justeat.server.access.IRestauranRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SANTIAGO MUÑOZ, KEVIN ALARCON, JUAN LOPEZ, SANTIAGO CORDOBA, DANIEL
 * MUÑOZ
 */
public class RestaurantService {

    /**
     * repositorio de Restaurante, Objeto de tipo IRestauranRepository.
     */
    IRestauranRepository repo;

    /**
     * Constructor por defecto.
     */
    public RestaurantService() {
    }

    /**
     * Constructor parametrizado, Hace inyeccion de dependencias.
     *
     * @param repo repositorio de tipo IRestaurantRepository.
     */
    public RestaurantService(IRestauranRepository repo) {
        this.repo = repo;
    }

    /**
     * Metodo encargado buscar un restaurante usando la interfaz
     * IRestaurantRepository.
     *
     * @param parResId cadena de texto con la cual se buscara un restaurante.
     * @return objeto tipo Restaurante
     */
    public Restaurant findRestaurant(String parResId) {
        return repo.findRestaurant(parResId);
    }

    /**
     * Crea un nuevo restaurante, Aplica validaciones de negocio.
     *
     * @param parRest Objeto de tipo Restaurant.
     * @return llamado al metodo createRestaurant.
     */
    public String CreateRestaurant(Restaurant parRest) {
        List<JsonError> errors = new ArrayList<>();
        if (parRest.getResCiudad().isEmpty() || parRest.getResNom().isEmpty() || parRest.getResDireccion().isEmpty()
                || parRest.getResCiudad().isEmpty() || parRest.getResTematicaComida().isEmpty()) {
            errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }

        return repo.createRestaurant(parRest);
    }

    /**
     * Metodo encargado de obtener una lista de todos los restaurantes
     * existentes.
     *
     * @return llamado a metodo findAllRestaurant.
     */
    public List<Restaurant> ListRestaurant() {
        List<JsonError> errors = new ArrayList<>();
        if (!repo.findAllRestaurant().isEmpty()) {
            if (!errors.isEmpty()) {
                errors.add(new JsonError("400", "BAD_REQUEST", "ERROR AL GENERAR PEDIDO SQL"));
            }
        }
        return repo.findAllRestaurant();
    }

}
