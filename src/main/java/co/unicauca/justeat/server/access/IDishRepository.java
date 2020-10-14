package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Dish;

/**
 * Interface del repositorio de platos.
 *
 * @author SANTIAGO MUÑOZ, KEVIN ALARCON, JUAN LOPEZ ,SANTIAGO CORDOBA, DANIEL
 * MUÑOZ
 */
public interface IDishRepository {

    /**
     * Crea un nuevo plato.
     *
     * @param parDish Objeto de tipo Dish.
     * @return valor especifico (platoId)
     */
    public String createDish(Dish parDish);

    public String deleteDish();

    public String uptadeDish();

    public String findDish();
}
