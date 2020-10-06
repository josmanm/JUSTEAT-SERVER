package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Menu;

/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public interface IMenuRepository {

    public String createMenu(Menu parMenu);

    public String uptadeMenu();

    public String deleteMenu();

    public String findMenu();

    public int connect();

    public void disconnect();

}
