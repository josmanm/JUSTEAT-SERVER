package co.unicauca.justeat.server.access;



import co.unicauca.justeat.commons.domain.Restaurant;
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
public class RestauranRepositoryImplArrays  implements IRestauranRepository{
    /**
     * Array de lista de Restaurantes
     */
    private static List<Restaurant> restaurants;
    
    public RestauranRepositoryImplArrays(){
          if (restaurants == null){
            restaurants = new ArrayList();
        }
        
        if (restaurants.size() == 0){
            inicializar();
        }
    }
    
    public void inicializar(){
        restaurants.add(new Restaurant("100", "99", "Sabroso", "Carrera 40 1-36", "Popayan", "China"));
        restaurants.add(new Restaurant("101", "998", "Deli", "Carrera 42 1-36", "Popayan", "De mar"));
        restaurants.add(new Restaurant("102", "997", "La20", "Carrera 403 12-36", "Popayan", "Pollo asado"));
    }
    /**
     * 
     * @param resId
     * @return 
     */
    @Override
    public Restaurant findRestaurant(String resId) {
              for (Restaurant restaurant : restaurants) {
            if (restaurant.getResId().equals(resId)) {
                return restaurant;
            }
        }
        return null;
    }

    @Override
    public String createRestaurant(Restaurant parRestauran) {
          restaurants.add(parRestauran);
        return parRestauran.getResId() + "";
    }

    @Override
    public String deleteRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String updateRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
