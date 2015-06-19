package ueb7.caas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Menu class
 * 
 * @author s1310307036
 *
 */
public class DayMenu implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -189719834099965048L;

    private Weekday day;
    private List<Category> categories;

    public DayMenu(Weekday day) {
        this.day = day;
        categories = new ArrayList<Category>();
    }

    /**
     * Returns the name of the Menu.
     * 
     * @return
     */
    public Weekday getDay() {
        return day;
    }

    /**
     * Adds a group to the menu.
     * 
     * @param category
     */
    public void addCategory(String category) {
        Category cat = new Category(category);
        if (!categories.contains(cat)) {
            categories.add(cat);
        }
    }

    /**
     * remove a group from the menu.
     * 
     * @param category
     */
    public void removeCategory(String category) {
        Category cat = findGroupByName(category);
        if (cat == null) {
            return;
        }

        List<String> dishes = cat.getDishes();
        if (dishes == null) {
            return;
        }

        for (int i = 0; i < dishes.size(); i++) {
            dishes.remove(i);
        }
    }

    /**
     * Adds a dish to a specific group in menu.
     * 
     * @param category
     * @param dish
     */
    public void addDish(String category, String dish) {
        addCategory(category);

        Category group = findGroupByName(category);

        if (group != null) {
            group.addDish(dish);
        }
    }

    public void removeDish(String category, String dish) {
        Category group = findGroupByName(category);

        if (group != null) {
            group.removeDish(dish);
        }
    }

    private Category findGroupByName(String name) {
        for (Category group : categories) {
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
        return categories.iterator();
    }

    /**
     * return the number of dishes from the menu
     * 
     * @return
     */
    public int noOfDishes() {
        int size = 0;

        for (Category group : categories) {
            size += group.size();
        }

        return size;
    }

    public List<Category> getMenu() {
        return categories;
    }

    @Override
    public String toString() {
        String ret = day.name() + " :\n";

        for (Category group : categories) {
            ret += group.toString();
        }

        return ret;
    }

    @Override
    public boolean equals(Object o) {
        return day.toString().equals(((DayMenu) o).day.toString());
    }
}
