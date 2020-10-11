/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.User;

/**
 *
 * @author SANTIAGO MUÑOZ
 */
public interface IUserRepository {
       /**
     * Buscar un Usuaaario utlizando un socket
     * @param id Id del restaurante
     * @return Objeto restaurant
     * @throws Exception 
     */
    public User findUser(String id) throws Exception;
    
    /**
     * Crea un Usario
     * @param user 
     * @return
     * @throws Exception 
     */
    
    public String createUser(User user) throws Exception;
}
