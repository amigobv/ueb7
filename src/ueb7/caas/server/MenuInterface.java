package ueb7.caas.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ueb7.caas.model.Category;
import ueb7.caas.model.DayMenu;
import ueb7.caas.model.Weekday;

public interface MenuInterface extends Remote {
    /**
     * Add a new dish to the menu
     * 
     * @param day
     * @param category
     * @param dish
     * @throws RemoteException
     */
    void addDish(Weekday day, String category, String dish) throws RemoteException;
    
    /**
     * Remove a particular dish from the menu
     * 
     * @param day
     * @param dish
     * @throws RemoteException
     */
    void removeDish(Weekday day, String category, String dish) throws RemoteException;
    
    /**
     * Add new category to the menu
     * 
     * @param category
     * @throws RemoteException
     */
    void addCategory(String category) throws RemoteException;
    
    /**
     * Remove category from the menu
     * 
     * @param category
     * @throws RemoteException
     */
    void removeCategory(String category) throws RemoteException;
    
    /**
     * Get the entire menu for a particular day
     * 
     * @param day
     * @return
     * @throws RemoteException
     */
    List<Category> getDayMenu(Weekday day) throws RemoteException;
    
    /**
     * Returns a list with the menu for all days of the week
     * 
     * @return
     * @throws RemoteException
     */
    List<DayMenu> getWeekMenu() throws RemoteException;
}
