package co.unicauca.justeat.server.domain.servicies;

import co.unicauca.justeat.commons.domain.Dish;
import co.unicauca.justeat.commons.infra.JsonError;
import co.unicauca.justeat.server.access.IDishRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author juang
 */
public class DishService {
    IDishRepository repoDish;

    /**
     * Constructor parametrizado, Hace inyeccion de dependencias.
     * @param repoDish 
     */
    public DishService(IDishRepository repoDish) {
        this.repoDish = repoDish;
    }

    /**
     * Constructor por defecto.
     */
    public DishService() {
    }
    
    public String CreateDish(Dish parDish) {
        List<JsonError> errors = new ArrayList<>();
        if (parDish.getPlatoId().isEmpty() || parDish.getPlanNom().isEmpty() || parDish.getPlaDesc().isEmpty() || parDish.getPlaPrecio() == 0) {
            errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }
        return repoDish.createDish(parDish);
    }
    
    
    
    
}
