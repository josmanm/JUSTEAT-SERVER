package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Menu;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANTIAGO MUÑOZ, KEVIN ALARCON, JUAN LOPEZ ,SANTIAGO CORDOBA, DANIEL
 * MUÑOZ
 */
public class MenuRepositoryImplMysql implements IMenuRepository {

    /**
     * Objeto de tipo Connection, encargado de realizar la Conexion con Mysql.
     */
    private Connection conn;

    /**
     * Metodo encargado de crear un menu, Este metodo se sobre escribe debido a
     * que es implementado de la interfaz IMenuRepository.
     *
     * @param parMenu
     * @return
     */
    @Override
    public String createMenu(Menu parMenu) {
        try {
            this.connect();
            String sql = "INSERT INTO Menu(MenuId, MenuNombre, MenuFechaVisualizacion VALUES (?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, parMenu.getMenuId());
                pstmt.setString(2, parMenu.getMenuNom());
                pstmt.setDate(3, (Date) parMenu.getMenuFecVi());

                pstmt.executeUpdate();
            }
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(IOrderRepository.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return (parMenu.getMenuId());
    }

    @Override
    public String uptadeMenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteMenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String findMenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int connect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void disconnect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
