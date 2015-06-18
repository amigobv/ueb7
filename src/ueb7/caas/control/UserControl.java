package ueb7.caas.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ueb7.caas.model.Admin;
import ueb7.caas.model.User;


/**
 * Class to simulate a database to hold the users and the orders
 * 
 * @author s1310307036
 *
 */
public class UserControl {
    private Map<String, User> users;
    private User loggedUser;

    /**
     * create user management control
     * 
     */
    public UserControl()  {
        users = new HashMap<String, User>();
        loggedUser = null;
        
        loadUsers();
    }
    
    /**
     * load default users
     */
    private void loadUsers() {
        users.put("admin", new Admin("admin", "admin"));
        users.put("guest", new User("guest", "guest"));
    }

    /**
     * Add new user
     * 
     * @param name - username
     * @param pw - password
     * @param type - type of the user (Guest, Admin)
     */
    public void addUser(String name, String pw) {
        users.put(name, new User(name, pw));
    }
    
    /**
     * Add new user
     * 
     * @param name - username
     * @param pw - password
     * @param type - type of the user (Guest, Admin)
     */
    public void addAdmin(String name, String pw) {
        users.put(name, new Admin(name, pw));
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public User getUserByName(String name) {
        if (!users.containsKey(name)) {
            return null;
        }
        
        return users.get(name);
    }

    /**
     * Execute user login operations for  and return the logged user otherwise throws exception
     * 
     * @param name
     * @param password
     * @return
     */
    public User login(String name, String password) throws Exception {
        if (!users.containsKey(name)) {
            throw new User.InvalidUsernameException();
        }

        User user = users.get(name);

        if (user.getPassword().compareTo(password) != 0)
            throw new User.InvalidPasswordException();
        
        loggedUser = user;
        
        return loggedUser;
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

    /**
     * check if the user exists
     * 
     * @param user
     * @return
     */
    public boolean contains(String user) {
        return users.containsKey(user);
    }
    
    /**
     * Remove user from the system
     * 
     * @param name
     */
    public void removeUserByName(String name) {
        if (users.containsKey(name)) {
            users.remove(name);
        }
    }
    
    /**
     * Returns a list with all existing users
     * 
     * @return lidt with users
     */
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        
        for (Map.Entry<String, User> entry : users.entrySet())
        {
            list.add(entry.getValue());
        }
        
        return list;
    }
    
    /**
     * Lock user
     * 
     * @param name
     */
    public void lock(String name) {
        User user = users.get(name);
        
        user.lock();
    }
    
    /**
     * 
     * @return
     */
    public int noOfUsers() {
        return users.size();
    }
}
