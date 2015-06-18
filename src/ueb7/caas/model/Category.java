package ueb7.caas.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Group of dishes.
 * 
 * @author s1310307036
 *
 */
public class Category {
    private String name;
    private List<String> dishes;
    
    public Category(String name) {
        this.name = name;
        dishes = new ArrayList<String>();
    }
    
    /**
     * Return the name of the group.
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * Add a new dish to the group.
     *
     * @param dish
     */
    public void addDish(String dish) {
        dishes.add(dish);
    }
    
    /**
     * Return the number of dishes in group
     * 
     * @return
     */
    public int size() {
        return dishes.size();
    }
    
    /**
     * Get a item at a particular index.
     * 
     * @param index
     * @return
     */
    public String getDishAt(int index) {
        String item = "";
        
        if (index < dishes.size()) {
            item = dishes.get(index);
        }
        
        return item;
    }
    
    /**
     * Remove item from group.
     * 
     * @param dish
     * @return
     */
    public boolean removeDish(String dish) {
        return dishes.remove(dish);
    }
    
    /**
     * Group iterator.
     * 
     * @return
     */
    public Iterator<String> iterator() {
        return dishes.iterator();
    }
    
    @Override
    public boolean equals(Object o) {
        return name.equals(((Category) o).name);
    }
    
    public String toString() {
        String ret = "";
        for(String item: dishes) {
            ret += name + " - " + item + "\n";
        }
        
        return ret;
    }
}
