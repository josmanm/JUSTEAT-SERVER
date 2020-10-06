package co.unicauca.justEat.server.access;

import co.unicauca.justeat.commons.infra.Utilities;
import co.unicauca.justeat.server.access.IRestauranRepository;
import co.unicauca.justeat.server.access.RestauranRepositoryImplArrays;
import co.unicauca.justeat.server.access.RestaurantRepositoryImplMysql;

/**
 * Fabrica que se encarga de instanciar un repositorio concreto
 *
 * @author Libardo, Julio
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
        String type = Utilities.loadProperty("customer.repository");
        if (type.isEmpty()) {
            type = "default";
        }
        IRestauranRepository result = null;

        switch (type) {
            case "default":
                result = new RestauranRepositoryImplArrays();
                break;
            case "mysql":
                result = new RestaurantRepositoryImplMysql();
        }
        return result;

    }
}
