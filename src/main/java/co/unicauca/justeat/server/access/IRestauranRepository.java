package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Restaurant;
import java.util.List;

/**
 * Interface del respositorio de restaurantes
 *
 * @author SANTIAGO MUÑOZ KEVIN ALARCON JUAN JOSE LOPEZ SANTIAGO CORDOBA DANIEL
 * MUÑOZ
 */
public interface IRestauranRepository {

    /**
     * Metodo para obtener un Restaurante por su Id
     *
     * @param resId id del restaurante
     * @return objeto de tipo Restaurant.
     */
    public Restaurant findRestaurant(String resId);

    /**
     * Crea un nuevo restaurante
     *
     * @param parRestauran objeto tipo restaurante
     * @return retorna un valor de especifico del parametro parRestaurant (ResId).
     */
    public String createRestaurant(Restaurant parRestauran);

    /**
     * Metodo para obtener una lista de todos los restaurantes.
     *
     * @return lista de tipo Restaurant.
     */
    public List<Restaurant> findAllRestaurant();

    public String deleteRestaurant();

    public String updateRestaurant();
}
