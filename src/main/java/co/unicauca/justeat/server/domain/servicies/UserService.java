/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.justeat.server.domain.servicies;

import co.unicauca.justeat.commons.domain.User;
import co.unicauca.justeat.commons.infra.JsonError;
import co.unicauca.justeat.server.access.IUserRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SANTIAGO MUÑOZ
 */
public class UserService {
    
    /**
     * repositorio de Restaurantes
     */
    IUserRepository repo;

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
     * Buscar un Restaurante
     *
     * @param parUserName
     * @return objeto tipo Restaurante
     * @throws java.lang.Exception
     */
    public User findUser(String parUserName){
        return repo.findUser(parUserName);
    }

    /**
     * Crea un nuevo customer.Aplica validaciones de negocio
     *
     * @param parUserName
     * @return
     * @throws java.lang.Exception
     */
    public String CreateUser(User parUserName){
        List<JsonError> errors = new ArrayList<>();
        if (parUserName.getUserName().isEmpty() || parUserName.getUserNombre().isEmpty() || parUserName.getUserApellido().isEmpty()
                || parUserName.getUserContrasena().isEmpty() || parUserName.getUserCedula().isEmpty()) {
            errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if(!errors.isEmpty()){
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }
        return repo.createUser(parUserName); 
    }
}
