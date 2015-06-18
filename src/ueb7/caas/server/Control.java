package ueb7.caas.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ueb7.caas.model.Admin;
import ueb7.caas.model.Order;
import ueb7.caas.model.User;
import ueb7.caas.model.Weekdays;

public class Control implements ControlInterface {
    private Map<String, User> users;
    private User loggedUser;
    private List<Order> orders;
    
    public Control() {
        super();
        users = new HashMap<String, User>();
        loggedUser = null;
        orders = new ArrayList<Order>();
        
        loadUsers();
    }
    
    @Override
    public void addDish(User user, Weekdays day, String dish) throws RemoteException {
        Order order = getOrderByUser(user);
        
        if (order == null) {
            order = new Order(user, day);
            orders.add(order);
        }
        
        order.add(dish);
    }
    
    @Override
    public void removeDish(User user, String dish) throws RemoteException {
        Order order = getOrderByUser(user);
        order.remove(dish);
    }
    
    @Override
    public List<String> getOrder(User user) throws RemoteException{
        Order order = getOrderByUser(user);
        return order.getList();
    } 
    
    @Override
    public int noOfOrders() throws RemoteException {
        return orders.size();
    }
    
    private Order getOrderByUser(User user) {
        for (Order order: orders) {
            if (order.getUser().equals(user)) {
                return order;
            }
        }
        
        return null;
    }
    
    
    /**
     * load default users
     */
    private void loadUsers() {
        users.put("admin", new Admin("admin", "admin"));
        users.put("guest", new User("guest", "guest"));
    }

    @Override
    public void addUser(String name, String pw) throws RemoteException {
        users.put(name, new User(name, pw));
    }
    
    @Override
    public void addAdmin(String name, String pw) throws RemoteException {
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
    public User login(String name, String password) throws Exception, RemoteException {
        if (!users.containsKey(name)) {
            throw new User.InvalidUsernameException();
        }

        User user = users.get(name);

        if (user.getPassword().compareTo(password) != 0)
            throw new User.InvalidPasswordException();
        
        loggedUser = user;
        
        return loggedUser;
    }
    
    
    @Override
    public void logout() throws RemoteException {
        loggedUser = null;
    }
    
    @Override
    public User getLoggedUser() throws RemoteException {
        return loggedUser;
    }
    
    @Override
    public boolean isLogged() throws RemoteException {
        return loggedUser != null;
    }

    @Override
    public boolean contains(String user) throws RemoteException {
        return users.containsKey(user);
    }
    
    @Override
    public void removeUserByName(String name) throws RemoteException {
        if (users.containsKey(name)) {
            users.remove(name);
        }
    }
    
    @Override
    public List<User> getUsers() throws RemoteException {
        List<User> list = new ArrayList<>();
        
        for (Map.Entry<String, User> entry : users.entrySet())
        {
            list.add(entry.getValue());
        }
        
        return list;
    }
    
    @Override
    public void lock(String name) throws RemoteException {
        User user = users.get(name);
        
        user.lock();
    }
    
    @Override
    public int noOfUsers() throws RemoteException {
        return users.size();
    }
}
