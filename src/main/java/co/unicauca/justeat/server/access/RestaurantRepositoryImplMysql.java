package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Restaurant;
import co.unicauca.justeat.commons.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SANTIAGO MUÑOZ KEVIN ALARCON JUAN JOSE LOPEZ SANTIAGO CORDOBA DANIEL
 * MUÑOZ
 */
public class RestaurantRepositoryImplMysql implements IRestauranRepository {

    /**
     * Objeto de tipo Connection, encargado de realizar la Conexion con Mysql.
     */
    public Connection conn;

    /**
     *Constructor por defecto.
     */
    public RestaurantRepositoryImplMysql() {

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
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

    /**
     * Metodo encargado de Eliminar un Restaurante.
     * @return 
     */
    @Override
    public String deleteRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo encargado de actualizar la informacion sobre el Restaurante.
     * @return 
     */
    @Override
    public String updateRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Metodo encargado de crear un restaurante, Este metodo se sobre escribe debido a que es implementado de la interfaz IRestaurantRepository.
     * @param parRestauran Objeto de tipo Restaurante, Este objeto servira para extraer informacion.
     * @return retorna un valor de especifico del parametro parRestaurant (ResId).
     */
    @Override
    public String createRestaurant(Restaurant parRestauran) {
        try {
            this.connect();
            String sql = "INSERT INTO restaurante(RestId,UserName,RestNombre,RestDireccion,RestCiudad,RestTematicaComida) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parRestauran.getResId());
            pstmt.setString(2, parRestauran.getUserName());
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
        return (parRestauran.getResId());
    }

    /**
     * Metodo encargado de encontrar los restaurantes.
     * @param resId cadena de texto, sirve para buscar un restaurante especifico.
     * @return objeto de tipo restaurante, con la informacion de la busqueda.
     */
    @Override
    public Restaurant findRestaurant(String resId) {
        Restaurant restaurant = null;
        try {
            this.connect();
            String sql = "SELECT * from Restaurante where RestNombre=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, resId);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                restaurant = new Restaurant();
                restaurant.setResId(res.getString("restId"));
                restaurant.setUserName(res.getString("UserName"));
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

    
    /**
     * Metodo encargado de obtener una lista de todos los restaurantes.
     * @return Se retorna una lista con los resultados de la busqueda. 
     */
    @Override
    public List<Restaurant> findAllRestaurant() {
        List<Restaurant> objList = new ArrayList<Restaurant>();
        this.connect();
        Restaurant objRestaurant = new Restaurant();
        try {
            String sql = "SELECT * FROM restaurante;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                objRestaurant.setResId(res.getString("restId"));
                objRestaurant.setUserName(res.getString("UserName"));
                objRestaurant.setResNom(res.getString("restNombre"));
                objRestaurant.setResDireccion(res.getString("restDireccion"));
                objRestaurant.setResCiudad(res.getString("restCiudad"));
                objRestaurant.setResTematicaComida(res.getString("restTematicaComida"));
                objList.add(objRestaurant);
                objRestaurant = new Restaurant();
            }
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar el restaurante de la base de datos", ex);
        }
        return objList;
    }

}
