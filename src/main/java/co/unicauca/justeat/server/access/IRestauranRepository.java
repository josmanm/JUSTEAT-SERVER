
package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Restaurant;



/**
 * Interface del respositorio de Restaurante
 * @author SANTIAGO MUÑOZ
 */
public interface IRestauranRepository {
        /**
     * Busca un Restauran por su Id
     * @param resId id del restaurante
     * @return  objeto de tipo Customer
     */
     public Restaurant findRestaurant(int resId);
    /**
     * Crea un  nuevo restaurante
     * @param parRestauran objeto tipo restaurante
     * @return 
     */
    public int createRestaurant(Restaurant parRestauran);
}
