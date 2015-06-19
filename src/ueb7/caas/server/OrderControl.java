package ueb7.caas.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import ueb7.caas.model.Order;
import ueb7.caas.model.User;
import ueb7.caas.model.Weekday;


public class OrderControl implements OrderInterface {
    private List<Order> orders;
    
    public OrderControl() {
        super();
        orders = new ArrayList<Order>();
    }
    
    @Override
    public void addDish(User user, Weekday day, String dish) throws RemoteException {
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
    public int noOfDishes() throws RemoteException {
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

}
