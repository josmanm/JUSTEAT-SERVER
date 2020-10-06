package co.unicauca.justeat.server.app;

import co.unicauca.justeat.server.infra.JustEatServerSocket;


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
        JustEatServerSocket server = new JustEatServerSocket();
        server.start();
    }
}
