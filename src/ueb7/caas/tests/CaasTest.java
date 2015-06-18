package ueb7.caas.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//import ueb7.caas.control.UserControl;
import ueb7.caas.model.DayMenu;
import ueb7.caas.model.Order;
import ueb7.caas.model.User;
import ueb7.caas.model.Weekdays;
import ueb7.caas.server.Control;

public class CaasTest {

    @Test
    public void testAddUser() {
        Control userMng = null;

        try {
            userMng = new Control();

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
        Order order = new Order(user, Weekdays.MONDAY);

        assertEquals(0, order.size());
        order.add("Dish 1");
        order.add("Dish 2");
        order.add("Dish 3");
        assertEquals(3, order.size());
    }

    @Test
    public void testMenu() {
        DayMenu monday = new DayMenu(Weekdays.MONDAY);

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
            Control orderCtrl = new Control();
            User user1 = new User("user1", "user1");
            User user2 = new User("user2", "user2");

            orderCtrl.addDish(user1, Weekdays.MONDAY, "supa");
            orderCtrl.addDish(user1, Weekdays.MONDAY, "macaroni");
            orderCtrl.addDish(user1, Weekdays.MONDAY, "desert");

            assertEquals(1, orderCtrl.noOfOrders());
            assertEquals(3, orderCtrl.getOrder(user1).size());

            orderCtrl.addDish(user2, Weekdays.TUESDAY, "spaghetti");
            orderCtrl.addDish(user2, Weekdays.TUESDAY, "desert");

            assertEquals(2, orderCtrl.noOfOrders());
            assertEquals(2, orderCtrl.getOrder(user2).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserControl() {
        Control usrCtrl = null;

        try {
            usrCtrl = new Control();

            assertEquals(2, usrCtrl.noOfUsers());
            usrCtrl.addUser("test", "test");
            assertEquals(3, usrCtrl.noOfUsers());

            User user = usrCtrl.login("guest", "guest");
            assertNotNull(user);

            assertEquals(user, usrCtrl.getLoggedUser());
            assertTrue(usrCtrl.isLogged());

            usrCtrl.logout();
            assertTrue(!usrCtrl.isLogged());

            assertNotNull(usrCtrl.getUserByName("test"));
            assertNull(usrCtrl.getUserByName("TEST"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
