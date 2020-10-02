/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.justeat.server.app;

import co.unicauca.justeat.server.infra.JustEatServerSocket;


/**
 *
 * @author SANTIAGO MUÑOZ
 */
public class JustEatApplication {
        public static void main(String args[]){
        JustEatServerSocket server = new JustEatServerSocket();
        server.start();
    }
}
