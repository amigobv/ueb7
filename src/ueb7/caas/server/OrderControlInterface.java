package ueb7.caas.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ueb7.caas.model.Order;
import ueb7.caas.model.User;
import ueb7.caas.model.Weekdays;

public interface OrderControlInterface extends Remote {
    /**
     * Add dish to order
     * 
     * @param user
     * @param day
     * @param dish
     * @throws RemoteException
     */
    void addDish(User user, Weekdays day, String dish) throws RemoteException;
    
    /**
     * Remove Dis from order
     * 
     * @param user
     * @param dish
     * @throws RemoteException
     */
    void removeDish(User user, String dish) throws RemoteException;
    
    /**
     * Get a list with all ordered dished
     * 
     * @param user
     * @return
     * @throws RemoteException
     */
    List<String> getOrder(User user) throws RemoteException;
    
    /**
     * Number of ordered dishes
     * 
     * @return
     * @throws RemoteException
     */
    int noOfOrders() throws RemoteException;
}
