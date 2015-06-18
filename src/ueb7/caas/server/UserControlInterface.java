package ueb7.caas.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ueb7.caas.model.Admin;
import ueb7.caas.model.User;

public interface UserControlInterface extends Remote {
    
    /**
     * Add new user
     * 
     * @param name - username
     * @param pw - password
     * @param type - type of the user (Guest, Admin)
     */
    void addUser(String name, String pw) throws RemoteException;
    
    /**
     * Add new user
     * 
     * @param name - username
     * @param pw - password
     * @param type - type of the user (Guest, Admin)
     */
    void addAdmin(String name, String pw) throws RemoteException;
    
    /**
     * 
     * @param name
     * @return
     */
    User getUserByName(String name) throws RemoteException;

    /**
     * Execute user login operations for  and return the logged user otherwise throws exception
     * 
     * @param name
     * @param password
     * @return
     */
    User login(String name, String password) throws Exception, RemoteException;
    
    
    /**
     * Execute user logout operations
     */
    void logout() throws RemoteException;
    
    /**
     * Returns the logged user
     * 
     * @return logged user
     */
    User getLoggedUser() throws RemoteException;
    
    /**
     * Returns if there is a logged user into the software
     * 
     * @return true if a user is logged otherwise false
     */
    boolean isLogged() throws RemoteException;

    /**
     * check if the user exists
     * 
     * @param user
     * @return
     */
    boolean contains(String user) throws RemoteException;
    
    /**
     * Remove user from the system
     * 
     * @param name
     */
    void removeUserByName(String name) throws RemoteException;
    
    /**
     * Returns a list with all existing users
     * 
     * @return list with users
     */
    List<User> getUsers() throws RemoteException;
    
    /**
     * Lock user
     * 
     * @param name
     */
    void lock(String name)throws RemoteException;
    
    /**
     * 
     * @return
     */
    int noOfUsers() throws RemoteException;

}
