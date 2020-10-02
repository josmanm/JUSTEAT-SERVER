/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.justeat.server.access;



import co.unicauca.justeat.commons.domain.Restaurant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SANTIAGO MUÑOZ
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
    }
    /**
     * 
     * @param resId
     * @return 
     */
    @Override
    public Restaurant findRestaurant(int resId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String createRestaurant(Restaurant parRestauran) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
