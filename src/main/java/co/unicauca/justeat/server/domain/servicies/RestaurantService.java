package co.unicauca.justeat.server.domain.servicies;

import co.unicauca.justeat.commons.domain.Restaurant;
import co.unicauca.justeat.commons.infra.JsonError;
import co.unicauca.justeat.commons.infra.Utilities;
import co.unicauca.justeat.server.access.IRestauranRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public class RestaurantService {

    /**
     * repositorio de Restaurantes
     */
    IRestauranRepository repo;

    public RestaurantService() {
    }

    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *
     * @param repo repositorio de tipo ICustomerRepository
     */
    public RestaurantService(IRestauranRepository repo) {
        this.repo = repo;
    }

    /**
     * Buscar un Restaurante
     *
     * @param parResId
     * @return objeto tipo Restaurante
     */
    public Restaurant findRestaurant(int parResId) {
        return repo.findRestaurant(parResId);
    }

    /**
     * Crea un nuevo customer. Aplica validaciones de negocio
     *
     * @param parRest
     * @return
     */
    public String CreateRestaurant(Restaurant parRest) {
        List<JsonError> errors = new ArrayList<>();
        if (parRest.getResCiudad().isEmpty() || parRest.getResNom().isEmpty() || parRest.getResDireccion().isEmpty()
                || parRest.getResCiudad().isEmpty() || parRest.getResTematicaComida().isEmpty() || Integer.toString(parRest.getResId()).isEmpty()) {
            errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if (!Utilities.isNumeric(Integer.toString(parRest.getResId()))) {
            errors.add(new JsonError("400", "BAD_REQUEST", "El id del restaurante debe contener solo digitos. "));
        }

        if (!Utilities.isNumeric(Integer.toString(parRest.getAdminId()))) {
            errors.add(new JsonError("400", "BAD_REQUEST", "El id del administrador debe contener solo digitos. "));
        }
        
        if(!errors.isEmpty()){
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }

        return repo.createRestaurant(parRest); 
    }

}
