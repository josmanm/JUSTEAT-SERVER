package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Admin;

/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public interface IAdminRepository {

    public String createAdmin(Admin parAdmin);

    public String updateAdmin();

    public String deleteAdmin();

    public Admin findAdmin();
    
    public int connect();
    
    public void disconnect();    


}
