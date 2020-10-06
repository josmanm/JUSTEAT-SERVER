/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.justeat.server.access;

/**
 *
 * @author juang
 */
public interface IOrderRepository {
    public String createOrder();
    public String updateOrder();
    public String deleteOrder();
    public String findOrder();

}
