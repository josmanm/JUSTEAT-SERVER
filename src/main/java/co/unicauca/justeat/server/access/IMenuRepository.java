/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Menu;

/**
 *
 * @author juang
 */
public interface IMenuRepository {

    public String createMenu(Menu parMenu);

    public String uptadeMenu();

    public String deleteMenu();

    public String findMenu();

    public int connect();

    public void disconnect();

}
