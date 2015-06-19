package ueb7.caas.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ueb7.caas.model.Admin;
import ueb7.caas.model.User;

public class UserControl implements UserInterface {
    private Map<String, User> users;
    
    public UserControl() {
        super();
        users = new HashMap<String, User>();
        
        loadUsers();
    }
    
    /**
     * load default users
     */
    private void loadUsers() {
        users.put("admin", new Admin("admin", "admin"));
        users.put("guest", new User("guest", "guest"));
    }
    
    @Override
    public synchronized void addUser(String name, String pw) throws RemoteException {
        users.put(name, new User(name, pw));
    }
    
    @Override
    public synchronized void addAdmin(String name, String pw) throws RemoteException {
        users.put(name, new Admin(name, pw));
    }
    
    @Override
    public User getUserByName(String name) throws RemoteException {
        if (!users.containsKey(name)) {
            return null;
        }
        
        return users.get(name);
    }

    @Override
    public synchronized User login(String name, String password) throws Exception, RemoteException {
        if (!users.containsKey(name)) {
            throw new User.InvalidUsernameException();
        }

        User user = users.get(name);

        if (user.getPassword().compareTo(password) != 0)
            throw new User.InvalidPasswordException();
        
        return user;
    }

    @Override
    public synchronized boolean contains(String user) throws RemoteException {
        return users.containsKey(user);
    }
    
    @Override
    public synchronized void removeUserByName(String name) throws RemoteException {
        if (users.containsKey(name)) {
            users.remove(name);
        }
    }
    
    @Override
    public synchronized List<User> getUsers() throws RemoteException {
        List<User> list = new ArrayList<>();
        
        for (Map.Entry<String, User> entry : users.entrySet())
        {
            list.add(entry.getValue());
        }
        
        return list;
    }
    
    @Override
    public synchronized void lock(String name) throws RemoteException {
        User user = users.get(name);
        
        user.lock();
    }
    
    @Override
    public synchronized int noOfUsers() throws RemoteException {
        return users.size();
    }
}
