/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.justeat.server.domain.servicies;

import co.unicauca.justeat.commons.domain.Restaurant;
import co.unicauca.justeat.server.access.IRestauranRepository;


/**
 *
 * @author SANTIAGO MUÑOZ
 */
public class RestaurantService {
    /**
     * repositorio de Restaurantes
     */
    IRestauranRepository repo;
    
    public RestaurantService(){
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
    public Restaurant findRestaurant(int  parResId) {
        return repo.findRestaurant(parResId);
    }
    /**
     * Crea un nuevo customer. Aplica validaciones de negocio
     * @param parRest
     * @return 
     */
    public String CreateRestauran(Restaurant parRest){
        return null;
        
    }
    
}