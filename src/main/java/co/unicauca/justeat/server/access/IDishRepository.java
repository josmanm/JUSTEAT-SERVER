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
public interface IDishRepository {
    public String createCustomer();
    public String deleteCustomer();
    public String uptadeCustomer();
    public String findCustomer();
}
