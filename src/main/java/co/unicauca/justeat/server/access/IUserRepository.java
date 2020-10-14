package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.User;

/**
 *
 * @author SANTIAGO MUÑOZ KEVIN ALARCON JUAN JOSE LOPEZ SANTIAGO CORDOBA DANIEL
 * MUÑOZ
 */
public interface IUserRepository {

    /**
     * Metodo encargado de encontrar un usuariot
     *
     * @param id Id del restaurante
     * @return Objeto restaurant
     */
    public User findUser(String id);

    /**
     * Metodo para crear usuarios,
     *
     * @param user Objeto de tipo User.
     * @return cadena de texto con el valor de getUserName.
     */
    public String createUser(User user);
}
