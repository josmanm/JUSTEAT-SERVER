package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Admin;
import co.unicauca.justeat.commons.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public class AdminRepositoryImplMysql implements IAdminRepository {

    private Connection conn;

    @Override
    public String createAdmin(Admin parAdmin) {
        try {
            this.connect();
            String sql = "INSERT INTO Administrador(Adminid, AdminNombre, AdminApellido) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, parAdmin.getAdminid());
            pstmt.setString(2, parAdmin.getAdminNombre());
            pstmt.setString(3, parAdmin.getAdminApellido());

            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(IAdminRepository.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return Integer.toString(parAdmin.getAdminid());
    }

    @Override
    public String updateAdmin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteAdmin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Admin findAdmin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int connect() {
        try {
            Class.forName(Utilities.loadProperty("server.db.driver"));
            //crea una instancia de la controlador de la base de datos
            String url = Utilities.loadProperty("server.db.url");
            String username = Utilities.loadProperty("server.db.username");
            String pwd = Utilities.loadProperty("server.db.password");
            conn = DriverManager.getConnection(url, username, pwd);
            return 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdminRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Administrador en la base de datos", ex);
        }
        return -1;
    }

    @Override
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

}
