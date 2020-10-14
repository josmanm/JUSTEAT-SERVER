package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Menu;

/**
 * Interface del repositorio de menu.
 *
 * @author SANTIAGO MUÑOZ, KEVIN ALARCON, JUAN LOPEZ ,SANTIAGO CORDOBA, DANIEL
 * MUÑOZ
 */
public interface IMenuRepository {

    /**
     *Crea un nuevo menu.
     * @param parMenu Objeto de tipo Menu.
     * @return valor especifico, MenuId.
     */
    public String createMenu(Menu parMenu);

    public String uptadeMenu();

    public String deleteMenu();

    public String findMenu();

    public int connect();

    public void disconnect();

}
