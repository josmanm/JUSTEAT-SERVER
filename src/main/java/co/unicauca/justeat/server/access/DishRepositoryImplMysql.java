package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Dish;
import co.unicauca.justeat.commons.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANTIAGO MUÑOZ KEVIN ALARCON JUAN JOSE LOPEZ SANTIAGO CORDOBA DANIEL
 * MUÑOZ
 */
public class DishRepositoryImplMysql implements IDishRepository {

    /**
     * Objeto de tipo Connection, encargado de realizar la Conexion con Mysql.
     */
    public Connection conn;
    public Connection conn2;

    /**
     * Metodo encargado de crear un plato.
     *
     * @param parDish Objeto de tipo Dish.
     * @return cadena de texto con el valor de platoId.
     */
    @Override
    public String createDish(Dish parDish) {
        try {
            this.connect();
            String sql = "INSERT INTO plato(PlatoId, PlatNombre, PlatDescripcion, PlatPrecio) VALUES (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parDish.getPlatoId());
            pstmt.setString(2, parDish.getPlanNom());
            pstmt.setString(3, parDish.getPlaDesc());
            pstmt.setDouble(4, parDish.getPlaPrecio());
            pstmt.executeUpdate();
            String sql2 = "INSERT INTO Tiene (MenuId,PlatoId) VALUES (?,?) ";
            PreparedStatement pstmt2 = conn2.prepareStatement(sql2);
            pstmt2.setString(1, "1122");
            pstmt2.setString(2, parDish.getPlatoId());
            pstmt2.executeUpdate();

            pstmt.close();
            pstmt2.close();

            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return (parDish.getPlatoId());
    }

    @Override
    public String deleteDish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String uptadeDish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String findDish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo que se encarga de realizar la conexion con la base de datos.
     *
     * @return 1, si la conexion fue exitosa, -1 si la conexion fue fallida.
     */
    public int connect() {
        try {
            Class.forName(Utilities.loadProperty("server.db.driver"));
            //crea una instancia de la controlador de la base de datos
            String url = Utilities.loadProperty("server.db.url");
            String username = Utilities.loadProperty("server.db.username");
            String pwd = Utilities.loadProperty("server.db.password");
            conn = DriverManager.getConnection(url, username, pwd);
            conn2 = DriverManager.getConnection(url, username, pwd);
            return 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Restaurante de la base de datos", ex);
        }
        return -1;
    }

    /**
     * Metodo encargado de desconectar la aplicacion de la base de datos.
     */
    private void disconnect() {
        try {
            conn.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

}
