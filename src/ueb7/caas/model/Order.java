package ueb7.caas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4145270647762572912L;
    
    private User user;
    private Weekday day;
    private List<String> dishes;
    
    /**
     * create new order for a user
     * 
     * @param user
     */
    public Order(User user, Weekday day) {
        this.user = user;
        this.day = day;
        dishes = new ArrayList<String>();
    }
    
    /**
     * Add new dish to the order
     * 
     * @param dish
     */
    public void add(String dish) {
        dishes.add(dish);
    }
    
    /**
     * Remove a dish from the order
     * 
     * @param dish
     */
    public void remove(String dish) {
        int idx = 0;
        for (String item: dishes) {
            if (item.equals(dish)) {
                dishes.remove(idx);
            }
            idx++;
        }
    }
    
    /**
     * Return the number of dishes ordered
     * 
     * @return
     */
    public int size() {
        return dishes.size();
    }
    
    /**
     * get a list with all dishes ordered
     * 
     * @return
     */
    public List<String> getList() {
        return dishes;
    }
    
    /**
     * Gets the user who create the order
     * 
     * @return
     */
    public User getUser() {
        return user;
    }
    
    @Override
    public String toString() {
        String ret = user.getName() + " :\n";
        
        for (String dish: dishes) {
            ret += dish + "\n";
        }
        
        return ret;
    }
}
