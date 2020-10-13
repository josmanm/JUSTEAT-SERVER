package co.unicauca.justEat.server.access;

import co.unicauca.justeat.commons.infra.Utilities;
import co.unicauca.justeat.server.access.IRestauranRepository;
import co.unicauca.justeat.server.access.IUserRepository;
import co.unicauca.justeat.server.access.RestauranRepositoryImplArrays;
import co.unicauca.justeat.server.access.RestaurantRepositoryImplMysql;
import co.unicauca.justeat.server.access.UserRepositoryImplMysql;

/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public class Factory {

    private static Factory instance;

    private Factory() {
    }

    /**
     * Clase singleton
     *
     * @return
     */
    public static Factory getInstance() {

        if (instance == null) {
            instance = new Factory();
        }
        return instance;

    }

    /**
     * Método que crea una instancia concreta de la jerarquia
     * IRestauranRepository
     *
     * @return una clase hija de la abstracción IRepositorioClientes
     */
    public IRestauranRepository getRepository() {
        String type = Utilities.loadProperty("restaurant.repository");
        if (type.isEmpty()) {
            type = "mysql";
        }
        IRestauranRepository result = null;

        switch (type) {
            case "default":
                result = new RestauranRepositoryImplArrays();
                break;
            case "mysql":
                result = new RestaurantRepositoryImplMysql();
                break;
        }
        return result;
    }
    
    public IUserRepository getRepositoryUser(){
        String type = Utilities.loadProperty("restaurant.repository");
        if (type.isEmpty()) {
            type = "mysql";
        }
        IUserRepository result = null;

        switch (type) {
            case "mysql":
                result = new UserRepositoryImplMysql();
                break;
        }
        return result;
    }
}
