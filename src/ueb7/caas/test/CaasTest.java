package ueb7.caas.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import ueb7.caas.model.DayMenu;
import ueb7.caas.model.Order;
import ueb7.caas.model.User;
import ueb7.caas.model.Weekday;
import ueb7.caas.server.MenuControl;
import ueb7.caas.server.MenuInterface;
import ueb7.caas.server.OrderControl;
import ueb7.caas.server.OrderInterface;
import ueb7.caas.server.UserControl;
import ueb7.caas.server.UserInterface;

public class CaasTest {

    @Test
    public void testAddUser() {
        UserInterface userMng = null;

        try {
            userMng = new UserControl();

            assertEquals(2, userMng.noOfUsers());
            userMng.addUser("Fritz", "1234");
            assertEquals(3, userMng.noOfUsers());
            userMng.addUser("Helmut", "1234");
            assertEquals(4, userMng.noOfUsers());

            userMng.removeUserByName("guest");
            assertEquals(3, userMng.noOfUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOrder() {
        User user = new User("Fritz", "1234");
        Order order = new Order(user, Weekday.MONDAY);

        assertEquals(0, order.size());
        order.add("Dish 1");
        order.add("Dish 2");
        order.add("Dish 3");
        assertEquals(3, order.size());
    }

    @Test
    public void testMenu() {
        DayMenu monday = new DayMenu(Weekday.MONDAY);

        assertEquals(0, monday.noOfDishes());
        monday.addDish("Suppen", "Suppe1");
        monday.addDish("Suppen", "Suppe1");
        assertEquals(2, monday.noOfDishes());
        monday.addDish("Vorspeise", "Vorspeise1");
        monday.addDish("Vorspeise", "Vorspeise2");
        assertEquals(4, monday.noOfDishes());
        monday.addDish("Hauptspeise", "Haupspeise1");
        monday.addDish("Hauprspeise", "Haupspeise2");
        assertEquals(6, monday.noOfDishes());
        monday.addDish("Desert", "Desert1");
        monday.addDish("Desert", "Desert2");
        assertEquals(8, monday.noOfDishes());

        monday.removeDish("Desert", "Desert1");
        assertEquals(7, monday.noOfDishes());
        monday.removeDish("Desert", "Unknown Dish");
        assertEquals(7, monday.noOfDishes());
        monday.removeDish("Unknown", "Haupspeise2");
        assertEquals(7, monday.noOfDishes());
    }

    @Test
    public void testOrderControl() {
        try {
            UserInterface ctrl = new UserControl();
            OrderInterface order = new OrderControl();
            
            User user1 = new User("user1", "user1");
            User user2 = new User("user2", "user2");

            ctrl.addUser(user1.getName(), user1.getPassword());
            ctrl.login(user1.getName(), user1.getPassword());
            order.addDish(user1, Weekday.MONDAY, "supa");
            order.addDish(user1, Weekday.MONDAY, "macaroni");
            order.addDish(user1, Weekday.MONDAY, "desert");

            assertEquals(1, order.noOfDishes());
            assertEquals(3, order.getOrder(user1).size());

            ctrl.addUser(user2.getName(), user2.getPassword());
            ctrl.login(user2.getName(), user2.getPassword());
            order.addDish(user2, Weekday.TUESDAY, "spaghetti");
            order.addDish(user2, Weekday.TUESDAY, "desert");

            assertEquals(2, order.noOfDishes());
            assertEquals(2, order.getOrder(user2).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserControl() {
        UserInterface service = null;

        try {
            service = new UserControl();

            assertEquals(2, service.noOfUsers());
            service.addUser("test", "test");
            assertEquals(3, service.noOfUsers());

            User user = service.login("guest", "guest");
            assertNotNull(user);

            assertNotNull(service.getUserByName("test"));
            assertNull(service.getUserByName("TEST"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testMenuControl() {
        MenuInterface menu = null;
        
        try {
            menu = new MenuControl();
            
            int size = menu.getDayMenu(Weekday.MONDAY).size();
            
            menu.addDish(Weekday.MONDAY, "Salat", "Salat");
            menu.addDish(Weekday.MONDAY, "Fisch", "Fisch");
            
            assertEquals(size + 2, menu.getDayMenu(Weekday.MONDAY).size());
            
            menu.addDish(Weekday.MONDAY, "Fisch", "Fisch2");
            assertEquals(size + 2, menu.getDayMenu(Weekday.MONDAY).size());
            
            menu.removeCategory("Fisch");
            assertEquals(size + 2, menu.getDayMenu(Weekday.MONDAY).size());
            
            menu.removeDish(Weekday.MONDAY, "Fisch", "Fisch");
            assertEquals(size + 2, menu.getDayMenu(Weekday.MONDAY).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
