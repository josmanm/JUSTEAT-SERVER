
package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Restaurant;
import java.util.List;



/**
 *Interface del respositorio de restaurantes
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public interface IRestauranRepository {
        /**
     * Busca un Restaurant por su Id
     * @param resId id del restaurante
     * @return  objeto de tipo Customer
     */
     public Restaurant findRestaurant(String resId);
    /**
     * Crea un  nuevo restaurante
     * @param parRestauran objeto tipo restaurante
     * @return 
     */
    public String createRestaurant(Restaurant parRestauran);
    
    public List<Restaurant> findAllRestaurant();
    
    public String deleteRestaurant();
    public String updateRestaurant();
}
