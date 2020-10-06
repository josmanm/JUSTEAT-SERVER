package co.unicauca.justeat.server.access;

/**
 *
 * @author SANTIAGO MUÑOZ
 *         KEVIN ALARCON
 *         JUAN JOSE LOPEZ
 *         SANTIAGO CORDOBA
 *         DANIEL MUÑOZ
 */
public interface IOrderRepository {
    public String createOrder();
    public String updateOrder();
    public String deleteOrder();
    public String findOrder();

}
