package ueb7.caas.control;

import ueb7.caas.model.User;


/**
 * Class to simulate a database to hold the users and the orders
 * 
 * @author s1310307036
 *
 */
public class UserManagement {
    private User loggedUser;

    /**
     * create user management control
     * 
     */
    public UserManagement()  {
        loggedUser = null;
    }
    
    public void setLoggedUser(User user) {
        loggedUser = user;
    }
    
    
    /**
     * Execute user logout operations
     */
    public void logout() {
        loggedUser = null;
    }
    
    /**
     * Returns the logged user
     * 
     * @return logged user
     */
    public User getLoggedUser() {
        return loggedUser;
    }
    
    /**
     * Returns if there is a logged user into the software
     * 
     * @return true if a user is logged otherwise false
     */
    public boolean isLogged() {
        return loggedUser != null;
    }
}
