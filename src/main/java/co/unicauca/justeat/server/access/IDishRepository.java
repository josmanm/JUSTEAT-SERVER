package co.unicauca.justeat.server.access;

import co.unicauca.justeat.commons.domain.Dish;

/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public interface IDishRepository {
    public String createDish(Dish parDish);
    public String deleteDish();
    public String uptadeDish();
    public String findDish();
}
