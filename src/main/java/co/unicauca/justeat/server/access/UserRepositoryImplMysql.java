package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.User;
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
 * @author SANTIAGO MUÑOZ, KEVIN ALARCON, JUAN LOPEZ, SANTIAGO CORDOBA, DANIEL
 * MUÑOZ
 */
public class UserRepositoryImplMysql implements IUserRepository {

    /**
     * Objeto de tipo Connection.
     */
    private Connection conn;

    /**
     * Metodo para crear usuarios, Este metodo se sobre escribe debido a que es
     * implementado de la interfaz IUserRepository.
     *
     * @param parUser Objeto de tipo User.
     * @return cadena de texto con el valor de getUserName.
     */
    @Override
    public String createUser(User parUser) {
        try {
            this.connect();
            String sql = "INSERT INTO Usuario(UserName,UserContraseña,UserNombre,UserApellido,UserCedula,UserCiudad,UserDireccion,UserCelular) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parUser.getUserName());
            pstmt.setString(2, parUser.getUserContrasena());
            pstmt.setString(3, parUser.getUserNombre());
            pstmt.setString(4, parUser.getUserApellido());
            pstmt.setString(5, parUser.getUserCedula());
            pstmt.setString(6, parUser.getUserCiudad());
            pstmt.setString(7, parUser.getUserDireccion());
            pstmt.setString(8, parUser.getUserCelular());

            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(IUserRepository.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return parUser.getUserName();
    }

    /**
     * Metodo encargado de realizar la conexion con la base de datos.
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
            Logger.getLogger(UserRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Administrador en la base de datos", ex);
        }
        return -1;
    }

    /**
     * Metodo encargado de desconectar la aplicacion de la base de datos.
     */
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

    /**
     * Metodo encargado de encontrar un usuario
     *
     * @param parUserName cadena de texto, servira para encontrar un usuario
     * especifico.
     * @return Objeto de tipo User.
     */
    @Override
    public User findUser(String parUserName) {
        User user = null;
        this.connect();
        try {
            String sql = "SELECT * from Usuario where UserName=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, parUserName);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                user = new User();
                user.setUserName(res.getString("UserName"));
                user.setUserContrasena(res.getString("UserContraseña"));
                user.setUserNombre(res.getString("UserNombre"));
                user.setUserApellido(res.getString("UserApellido"));
                user.setUserCedula(res.getString("UserCedula"));
                user.setUserCiudad(res.getString("UserCiudad"));
                user.setUserDireccion(res.getString("UserDireccion"));
                user.setUserCelular(res.getString("UserCelular"));
            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar el Uusuario de la base de datos", ex);
        }
        return user;
    }

}
