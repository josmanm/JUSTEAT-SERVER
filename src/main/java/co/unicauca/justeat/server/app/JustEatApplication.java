package co.unicauca.justeat.server.app;

import co.unicauca.justeat.server.infra.RestaurantServerSocket;


/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public class JustEatApplication {
        public static void main(String args[]){
        RestaurantServerSocket server = new RestaurantServerSocket();
        server.start();
    }
}
