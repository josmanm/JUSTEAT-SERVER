/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Admin;

/**
 *
 * @author juang
 */
public interface IAdminRepository {

    public String createAdmin(Admin parAdmin);

    public String updateAdmin();

    public String deleteAdmin();

    public Admin findAdmin();
    
    public int connect();
    
    public void disconnect();    


}
