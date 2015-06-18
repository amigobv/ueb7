package ueb7.caas.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Menu class
 * 
 * @author s1310307036
 *
 */
public class DayMenu {
    private Weekdays day;
    private List<Category> groups;
    
    public DayMenu(Weekdays day) {
        this.day = day;
        groups = new ArrayList<Category>();
    }
    
    
    /**
     * Returns the name of the Menu.
     * 
     * @return
     */
    public Weekdays getDay(){
        return day;
    }
    
    /**
     * Adds a group to the menu.
     * 
     * @param name
     */
    public void addGroup(String name) {
        Category group = new Category(name);
        if (!groups.contains(group)) {
            groups.add(group);
        }
    }
    
    /**
     * Adds a dish to a specific group in menu.
     * 
     * @param groupName
     * @param dish
     */
    public void addDish(String groupName, String dish) {
        addGroup(groupName);
        
        Category group = findGroupByName(groupName);
        
        if (group != null) {
            group.addDish(dish); 
        }
    }
    
    public void removeDish(String groupName, String name) {
        Category group = findGroupByName(groupName);
        
        if (group != null) {
            group.removeDish(name); 
        }
    }
    
    private Category findGroupByName(String name) {
        for (Category group: groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        
        return null;    
    }
    
    /**
     * Menu iterator.
     * 
     * @return
     */
    public Iterator<Category> iterator() {
        return groups.iterator();
    }
    
    /**
     * return the number of dishes from the menu
     * 
     * @return
     */
    public int noOfDishes() {
        int size = 0;
        
        for (Category group: groups) {
            size += group.size();
        }
        
        return size;
    }
    
    public String toString() {
        String ret = day.name() + " :\n";
        
        for (Category group: groups) {
            ret += group.toString();
        }
        
        return ret;
    }
    
}
