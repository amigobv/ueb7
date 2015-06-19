package ueb7.caas.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import ueb7.caas.model.Category;
import ueb7.caas.model.DayMenu;
import ueb7.caas.model.Weekday;

public class MenuControl implements MenuInterface {
    private List<DayMenu> menus;
    
    public MenuControl() {
        super();
        menus = new ArrayList<DayMenu>();
        
        loadMenu();
    }
    
    private void loadMenu() {
        generateDefaultContent();
    }
    
    private void generateDefaultContent() {
        generateMenu(Weekday.MONDAY);
        generateMenu(Weekday.TUESDAY);
        generateMenu(Weekday.WEDNESDAY);
        generateMenu(Weekday.THURSDAY);
        generateMenu(Weekday.FRIDAY);
    }

    private void generateMenu(Weekday day)  {
        try {
            switch(day) {
                case MONDAY:
                    addDish(day, "Suppen", "Frittatensuppe");
                    addDish(day, "Suppen", "Gemüsesuppe");
    
                    addDish(day, "Aus der Pfanne und vom Grill",
                            "Rindergeschnetzeltes mit Sojasprossen,Bambussprossen dazu Eiernudel");
                    addDish(day, "Aus der Pfanne und vom Grill",
                            "Faschierte Laibchen Bella Italia mit frischen Kräutern, PetersilienerdÄpfel");
    
                    addDish(day, "Vegetarisch", "Weisser Spargel mit Sauce Hollandaise mit Kartoffel");
                    addDish(day, "Vegetarisch", "Pasta all' verdura, mit Parmesan und Blattsalaten");
    
                    addDish(day, "Desert", "Apfelstrudel mit Vanilleschaum");
                    addDish(day, "Desert", "Frisches Joghurt mit Himbeermousse");
                break;
                
                case TUESDAY:
                    addDish(day, "Suppen", "Kürbiscremesuppe");
                    addDish(day, "Suppen", "Knoblauchcremesuppe");
    
                    addDish(day, "Aus der Pfanne und vom Grill", "Lasagne al forno auf Tomaten-Basilikumsauce");
                    addDish(day, "Aus der Pfanne und vom Grill",
                            "sterreichisches Steak vom Beiried mit Speckbohnen und PetersilienerdÄpfel");
    
                    addDish(day, "Vegetarisch", "Weisser Spargel mit Sauce Hollandaise mit Kartoffel");
                    addDish(day, "Vegetarisch", "Asiatisches Gemüse auf Reisbeet");
    
                    addDish(day, "Desert", "Schoko-Muffin mit flüssigem Kern");
                    addDish(day, "Desert", "Topfennockerl auf Erdbeerspiegel");
                break;
                
                case WEDNESDAY:
                    addDish(day, "Suppen", "Zwiebelsuppe");
                    addDish(day, "Suppen", "Lebersuppe");
    
                    addDish(day, "Aus der Pfanne und vom Grill", "Putenfilet auf Champignonsauce dazu Kroketten");
                    addDish(day, "Aus der Pfanne und vom Grill", "Spaghetti Carbonara mit frischen Blattsalaten");
    
                    addDish(day, "Vegetarisch", "Schinken oder Krautfleckerl ");
                    addDish(day, "Vegetarisch", "Vegan: Spaghetti all' Arrabiata mit frischen Blattsalaten");
    
                    addDish(day, "Desert", "Waldviertler Mohnschnitte");
                    addDish(day, "Desert", "Vanille-Banana-Schoko Dessert");
                break;
                
                case THURSDAY:
                    addDish(day, "Suppen", "Spargelcremesuppe");
                    addDish(day, "Suppen", "Kohlrabisuppe");
    
                    addDish(day, "Aus der Pfanne und vom Grill", "Pasta mit cremiger Spargel Sauce");
                    addDish(day, "Aus der Pfanne und vom Grill", "Saftiges Naturschnitzerl vom Schwein mit PetersilienerdÄpfel ");
    
                    addDish(day, "Vegetarisch", "Frühlingsrolle mit Reis und Chilidip");
                    addDish(day, "Vegetarisch", "Pasta mit cremiger Spargel Sauce");
    
                    addDish(day, "Desert", "Apfelschlangerl");
                    addDish(day, "Desert", "Zitronen-Ingwer-Minz-Joghurttorte");
                break;
                
                case FRIDAY:
                    addDish(day, "Suppen", "Kohlrabisuppe");
                    addDish(day, "Suppen", "Hühnersuppe");
    
                    addDish(day, "Aus der Pfanne und vom Grill", "Grillteller mit Pommes und Gemüse");
                    addDish(day, "Aus der Pfanne und vom Grill", "Pasta mit Schweinskarree-Streifen in Pfeffersauce");
    
                    addDish(day, "Vegetarisch", "Topfenknödel in Butterbräsel dazu Vanillesauce");
                    addDish(day, "Vegetarisch", "Kartoffelpuffer mit Joghurtdip und Blattsalat");
    
                    addDish(day, "Desert", "Topfenstrudel ");
                    addDish(day, "Desert", "Buchteln mit Powidlmarmelade und Vanilleschaum");
                break;
                
                default:
                break;  
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public synchronized void addDish(Weekday day, String category, String dish) throws RemoteException {
        DayMenu menu = getMenuByDay(day);
        
        if (menu == null) {
            menu = createDayMenu(day);
        }

        menu.addDish(category, dish);
    }
    
    /**
     * creates a new empty menu of a day
     * 
     * @param day
     * @return
     */
    private synchronized DayMenu createDayMenu(Weekday day)  {
        DayMenu menu = getMenuByDay(day);
        
        if (menu == null) {
            menu = new DayMenu(day);
            menus.add(menu);
        }
        
        return menu;
    };
    
    /**
     * Find a menu from a particular day
     * 
     * @param day
     * @return
     */
    private synchronized DayMenu getMenuByDay(Weekday day) {
        for (DayMenu menu: menus) {
            if (menu.getDay() == day) {
                return menu;
            }
        }
        
        return null;
    }

    @Override
    public synchronized void removeDish(Weekday day, String category, String dish) throws RemoteException {
        DayMenu menu = getMenuByDay(day);
        
        if (menu != null) {
            menu.removeDish(category, dish);
        }
        
    }

    @Override
    public synchronized void addCategory(String category) throws RemoteException {
        for (DayMenu menu : menus) {
            menu.addCategory(category);
        }  
    }

    @Override
    public synchronized void removeCategory(String category) throws RemoteException {
        for (DayMenu menu : menus) {
            menu.removeCategory(category);
        }  
    }

    @Override
    public List<Category> getDayMenu(Weekday day) throws RemoteException {
        DayMenu menu = getMenuByDay(day);
        
        if (menu != null) {
            return menu.getMenu();
        }
        
        return null;
    }
    
    @Override
    public List<DayMenu> getWeekMenu() throws RemoteException {
        return menus;
    }
}
