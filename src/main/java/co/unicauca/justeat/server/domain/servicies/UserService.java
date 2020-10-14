package co.unicauca.justeat.server.domain.servicies;

import co.unicauca.justeat.commons.domain.User;
import co.unicauca.justeat.commons.infra.JsonError;
import co.unicauca.justeat.server.access.IUserRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SANTIAGO MUÑOZ, KEVIN ALARCON, JUAN LOPEZ, SANTIAGO CORDOBA, DANIEL
 * MUÑOZ
 */
public class UserService {

    /**
     * repositorio de Usuarios, Objeto de tipo IUserRepository.
     */
    IUserRepository repo;

    /**
     * Contructor por defecto.
     */
    public UserService() {
    }

    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *
     * @param repo repositorio de tipo ICustomerRepository
     */
    public UserService(IUserRepository repo) {
        this.repo = repo;
    }

    /**
     * Metodo encargado de buscar un Restaurante.
     *
     * @param parUserName
     * @return objeto tipo Restaurante
     */
    public User findUser(String parUserName) {
        return repo.findUser(parUserName);
    }

    /**
     * Metodo encargado de crear un nuevo Usuario (User).
     *
     * @param parUserName objeto de tipo User.
     * @return llamado a funcion createUser() de la interfaz IUserRepository.
     */
    public String CreateUser(User parUserName) {
        List<JsonError> errors = new ArrayList<>();
        if (parUserName.getUserName().isEmpty() || parUserName.getUserNombre().isEmpty() || parUserName.getUserApellido().isEmpty()
                || parUserName.getUserContrasena().isEmpty() || parUserName.getUserCedula().isEmpty()) {
            errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }
        return repo.createUser(parUserName);
    }
}
