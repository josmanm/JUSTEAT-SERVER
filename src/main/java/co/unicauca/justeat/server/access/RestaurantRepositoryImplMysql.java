package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Restaurant;
import co.unicauca.justeat.commons.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class RestaurantRepositoryImplMysql implements IRestauranRepository {

    /**
     * Coneccion con Mysql
     */
    public Connection conn;

    public RestaurantRepositoryImplMysql() {

    }
    
     @Override
    public Restaurant findRestaurant(int resId) {
        Restaurant restaurant = null;
        this.connect();
        try {
            String sql = "SELECT * from Restaurante where id=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, resId + "");
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                restaurant = new Restaurant();
                restaurant.setResId(Integer.parseInt(res.getString("restId")));
                restaurant.setAdminId(Integer.parseInt(res.getString("adminId")));
                restaurant.setResNom(res.getString("restNombre"));
                restaurant.setResDireccion(res.getString("restDireccion"));
                restaurant.setResCiudad(res.getString("restCiudad"));
                restaurant.setResTematicaComida(res.getString("restTematicaComida"));
            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar el restaurante de la base de datos", ex);
        }
        return restaurant;
    }
    
    @Override
    public String createRestaurant(Restaurant parRestauran) {
        try {
            this.connect();
            String sql = "INSERT INTO restaurante(RestId,AdminId,RestNombre,RestDireccion,RestCiudad,RestTematicaComida) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, parRestauran.getResId());
            pstmt.setInt(2, parRestauran.getAdminId());
            pstmt.setString(3, parRestauran.getResNom());
            pstmt.setString(4, parRestauran.getResDireccion());
            pstmt.setString(5, parRestauran.getResCiudad());
            pstmt.setString(6, parRestauran.getResTematicaComida());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return Integer.toString(parRestauran.getResId());
    }

    /**
     * Permite hacer la conexion con la base de datos
     *
     * @return
     */
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
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Restaurante de la base de datos", ex);
        }
        return -1;
    }

    private void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

    @Override
    public String deleteRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String updateRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
