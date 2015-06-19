package ueb7.caas.view;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ueb7.caas.control.UserManagement;
import ueb7.caas.model.Admin;
import ueb7.caas.model.Category;
import ueb7.caas.model.User;
import ueb7.caas.model.Weekday;
import ueb7.caas.server.MenuInterface;
import ueb7.caas.server.OrderInterface;
import ueb7.caas.server.UserInterface;
import ueb7.caas.time.DateTimePicker;

public class ApplicationView {
    private final int SCENE_WIDTH = 840;
    private final int SCENE_HEIGHT = 700;

    private final int MIN_HEIGHT = 240;
    private final int MIN_WIDTH = 240;

    private final int STD_PADDING = 10;
    private final int BTN_WIDTH = 120;

    private Stage mainStage;
    private Pane rootPane;
    private TabPane menuPane;
    private LoginPane login;
    private List<MenuTab> menus;
    private UserManagement usrCtrl;
    private OrderInterface order;
    private MenuInterface menu;
    private UserInterface userCtrl;

    /**
     * 
     * @author s1310307036
     *
     */
    class LoginPane {
        private TextField textFieldUsername;
        private PasswordField passwordFieldPwd;
        private Button btnLogin;
        private Button btnLogout;
        private GridPane loginGrid;
        private Hyperlink reg;
        private Stage errorWindow;
        private Stage registration;

        public LoginPane() {
            this.loginGrid = new GridPane();
            showLogin();
        }

        private void showLogin() {
            loginGrid.getChildren().clear();

            textFieldUsername = new TextField();
            textFieldUsername.setId("txtUsername");

            passwordFieldPwd = new PasswordField();
            passwordFieldPwd.setId("password");

            btnLogin = new Button("Login");
            btnLogin.setPrefWidth(BTN_WIDTH);
            btnLogin.setId("btnLogin");
            btnLogin.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleLogin(e));
            GridPane.setHalignment(btnLogin, HPos.CENTER);

            reg = new Hyperlink("registrate");
            reg.setId("regLink");
            reg.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.goToRegistration(e));

            loginGrid.setId("loginArea");
            loginGrid.setPadding(new Insets(5));
            loginGrid.setHgap(2);
            loginGrid.setVgap(2);

            loginGrid.add(new Label("Benutzername"), 0, 0);
            loginGrid.add(new Label("Kennwort"), 1, 0);
            loginGrid.add(reg, 2, 0);
            loginGrid.add(textFieldUsername, 0, 1);
            loginGrid.add(passwordFieldPwd, 1, 1);
            loginGrid.add(btnLogin, 2, 1);
            loginGrid.setAlignment(Pos.TOP_RIGHT);
        }

        public Pane show() {
            return loginGrid;
        }

        private void handleLogin(ActionEvent event) {
            if (event.getTarget() instanceof Button) {
                try {
                    usrCtrl.setLoggedUser(userCtrl.login(textFieldUsername.getText(), passwordFieldPwd.getText()));
                    if (usrCtrl.isLogged() && usrCtrl.getLoggedUser().isLocked()) {
                        displayMessage("Konto wurde gesperrt");
                    } else {
                        displayUser();
                        refreshMenu();
                    }
                } catch (Exception e) {
                    displayMessage(e.toString());
                }
            }
        }

        private void displayUser() {
            try {
                if (usrCtrl.isLogged()) {
                    loginGrid.getChildren().clear();
                    Label username = new Label(usrCtrl.getLoggedUser().getName());
                    textFieldUsername.setText("");
                    passwordFieldPwd.setText("");
                    loginGrid.getChildren().clear();

                    btnLogout = new Button("Logout");
                    btnLogout.setPrefWidth(BTN_WIDTH);
                    btnLogout.setId("btnLogin");
                    btnLogout.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleLogout(e));

                    loginGrid.add(new Label(username.getText()), 0, 0);
                    loginGrid.add(btnLogout, 1, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void displayMessage(String msg) {
            errorWindow = new Stage();
            errorWindow.initModality(Modality.APPLICATION_MODAL);
            errorWindow.initOwner(mainStage);

            GridPane errorPane = new GridPane();
            errorPane.setId("errorMsg");
            errorPane.setPadding(new Insets(STD_PADDING));
            errorPane.setHgap(STD_PADDING);
            errorPane.setVgap(STD_PADDING);

            Button btnOk = new Button("OK");
            btnOk.setPrefWidth(BTN_WIDTH);
            btnOk.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> {
                errorWindow.close();
            });
            GridPane.setHalignment(btnOk, HPos.CENTER);

            errorPane.add(new Label(msg), 0, 1, 2, 1);
            errorPane.add(btnOk, 0, 2, 2, 1);

            errorWindow.setScene(new Scene(errorPane));
            errorWindow.show();
        }

        private void handleLogout(ActionEvent event) {
            if (event.getTarget() instanceof Button) {
                try {
                    usrCtrl.logout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                showLogin();
                refreshMenu();
            }
        }

        private void goToRegistration(ActionEvent event) {
            if (event.getTarget() instanceof Hyperlink) {
                registration = new Stage();
                registration.initModality(Modality.APPLICATION_MODAL);
                registration.initOwner(mainStage);

                GridPane registrationPane = new GridPane();
                registrationPane.setId("registrationForm");
                registrationPane.setPadding(new Insets(STD_PADDING));
                registrationPane.setHgap(STD_PADDING);
                registrationPane.setVgap(STD_PADDING);

                GridPane.setHgrow(textFieldUsername, Priority.ALWAYS);
                GridPane.setHgrow(passwordFieldPwd, Priority.ALWAYS);

                Button btnRegistrate = new Button("registrate");
                btnRegistrate.setPrefWidth(BTN_WIDTH);
                btnRegistrate.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleRegistration(e));
                GridPane.setHalignment(btnRegistrate, HPos.CENTER);

                registrationPane.add(new Label("Benutzername"), 0, 0);
                registrationPane.add(textFieldUsername, 1, 0);
                registrationPane.add(new Label("Kennword"), 0, 1);
                registrationPane.add(passwordFieldPwd, 1, 1);
                registrationPane.add(btnRegistrate, 0, 2, 2, 1);

                registration.setScene(new Scene(registrationPane));
                registration.show();
            }
        }

        private void handleRegistration(ActionEvent event) {
            if (textFieldUsername.getText().length() == 0 || textFieldUsername.getText().length() == 0)
                return;

            try {
                userCtrl.addUser(textFieldUsername.getText(), passwordFieldPwd.getText());
                System.out.println("Num of users: " + userCtrl.noOfUsers());
            } catch (Exception e) {
                e.printStackTrace();
            }

            displayUser();
            registration.close();
        }

    }

    /**
     * Class used to display the content of the menu
     * 
     * @author s1310307036
     *
     */
    class MenuTab {
        Weekday day;
        Tab tab;
        private ScrollPane scrollContent;
        private Button btnCommand;
        private VBox tabContent;
        private Stage mainStage;
        private Stage orderWindow;
        private Stage addWindow;
        private Stage addCategorieWindow;

        private Category newGroup;
        private TextField newDish;
        private Button btnAddCategorie;
        private TextField newCategorie;

        public MenuTab(Weekday day) {
            this.day = day;
            tab = new Tab(day.toString());

            scrollContent = new ScrollPane();
            scrollContent.setMinHeight(520);
            scrollContent.setMinWidth(480);

            btnCommand = new Button("Bestellen");
            btnCommand.setPrefWidth(120);
            btnCommand.setId("btnOrder");
            btnCommand.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleOrder(e));

            btnAddCategorie = new Button("Neue Kategorie");
            btnAddCategorie.setPrefWidth(120);
            btnAddCategorie.setId("btnCategorie");
            btnAddCategorie.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleAddCategory(e));
        }

        public Tab show() {
            tabContent = new VBox();
            tabContent.setId("tabContent");
            tabContent.setPadding(new Insets(10));
            tabContent.setAlignment(Pos.BOTTOM_LEFT);

            refresh(day);

            scrollContent.setContent(tabContent);
            tab.setContent(scrollContent);

            return tab;
        }

        private void refresh(Weekday day) {
            tabContent.getChildren().clear();
            tabContent.getChildren().add(showDayMenu(day));

            HBox btnBox = new HBox();

            try {
                if (!usrCtrl.isLogged()) {
                    btnCommand.setVisible(false);
                    btnAddCategorie.setVisible(false);
                } else {
                    User user = usrCtrl.getLoggedUser();

                    if (user instanceof Admin) {
                        btnAddCategorie.setVisible(true);
                        btnBox.getChildren().add(btnAddCategorie);
                    } else {
                        btnCommand.setVisible(true);
                        btnBox.getChildren().add(btnCommand);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnBox.setPadding(new Insets(5));
            btnBox.setAlignment(Pos.TOP_RIGHT);

            tabContent.getChildren().add(btnBox);
        }

        /**
         * 
         * @return
         */
        public String getName() {
            return tab.getText();
        }
        
        /**
         * 
         */
        public Weekday getDay() {
            return day;
        }
        
        /**
         * 
         */
        public Tab getTab() {
            return tab;
        }

        /**
         * Returns a list with all selected dishes
         * 
         * @return
         */
        private ArrayList<String> getSelected() {
            ArrayList<String> list = new ArrayList<String>();

            for (Node element : tabContent.getChildren()) {
                if (element instanceof GridPane) {
                    for (Node container : ((GridPane) element).getChildren()) {
                        if (container instanceof HBox) {
                            for (Node item : ((HBox) container).getChildren()) {
                                if (item instanceof RadioButton) {
                                    RadioButton rItem = (RadioButton) item;
                                    if (rItem.isSelected()) {
                                        list.add(rItem.getText());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return list;
        }
        
        private Pane showDayMenu(Weekday day) {
            int row = 0;
            int col = 0;

            GridPane menuGrid = new GridPane();
            
            try {
                List<Category> categories = null;

                categories = menu.getDayMenu(day);

                for (Category category : categories) {
                    User user = null;
                    try {
                        user = usrCtrl.getLoggedUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ToggleGroup toggle = new ToggleGroup();
                    Text name = new Text(category.getName());
                    name.setUnderline(true);
                    menuGrid.add(name, col, row++);

                    Iterator<String> items = category.iterator();
                    while (items.hasNext()) {
                        String item = items.next();
                        HBox itemBox = new HBox();

                        Button btnRemove = new Button("-");
                        btnRemove.setId(category.getName() + "_" + item);
                        btnRemove.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleRemove(e));

                        if (user == null || !(user instanceof Admin)) {
                            btnRemove.setVisible(false);
                        }

                        RadioButton radioItem = new RadioButton(item);
                        radioItem.setToggleGroup(toggle);

                        if ((user == null || (user instanceof Admin)) || category.getName() == "Desert") {
                            radioItem.setDisable(true);
                        }

                        itemBox.getChildren().addAll(btnRemove, radioItem);
                        menuGrid.add(itemBox, col, row++);
                    }

                    if (user != null && (user instanceof Admin)) {
                        Button btnAddItem = new Button("+");
                        btnAddItem.setId(category.getName());
                        btnAddItem.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleAdd(e));
                        menuGrid.add(btnAddItem, col, row++);
                    }

                    Separator separateLine = new Separator(Orientation.HORIZONTAL);
                    separateLine.setId("separateLine");
                    GridPane.setHgrow(separateLine, Priority.ALWAYS);
                    menuGrid.add(separateLine, col, row++);
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }

            return menuGrid;
        }

        /**
         * Remove dish handler
         * 
         * @param e
         */
        private void handleRemove(ActionEvent e) {
            if (e.getTarget() instanceof Button) {
                Button delete = (Button) e.getTarget();
                String[] id = delete.getId().split("_");
                Category group = null;

                try {
                    List<Category> categories = menu.getDayMenu(day);
                    
                    for (Category category: categories) {
                        if (id[0].equals(category.getName())) {
                            group = category;
                            break;
                        }
                    }

                    if (group != null) {
                        group.removeDish(id[1]);
                        refresh(day);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        }

        /**
         * Order handler
         * 
         * @param event
         */
        private void handleOrder(ActionEvent event) {
            if (event.getTarget() instanceof Button) {
                orderWindow = new Stage();
                orderWindow.initModality(Modality.APPLICATION_MODAL);
                orderWindow.initOwner(mainStage);

                GridPane orderPane = new GridPane();
                orderPane.setId("OrderPane");
                orderPane.setPadding(new Insets(10));
                orderPane.setHgap(10);
                orderPane.setVgap(10);

                Button btnOk = new Button("Bestellen");
                btnOk.setPrefWidth(120);
                btnOk.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> {
                    orderWindow.close();
                });
                GridPane.setHalignment(btnOk, HPos.CENTER);

                ArrayList<String> orderList = getSelected();

                int row = 0;
                for (String item : orderList) {
                    try {
                        order.addDish(usrCtrl.getLoggedUser(), getMenuDay(), item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    orderPane.add(new Label(item), 0, row);
                    row++;
                }

                DateTimePicker d = new DateTimePicker();

                HBox timeBox = new HBox();
                timeBox.getChildren().add(new Label("Uhrzeit eingeben"));
                timeBox.getChildren().add(d);

                orderPane.add(timeBox, 0, row, row, 1);
                row++;
                orderPane.add(btnOk, 0, row, row, 1);

                orderWindow.setScene(new Scene(orderPane));
                orderWindow.show();
            }
        }
        
        private Weekday getMenuDay() {
            SingleSelectionModel<Tab> selection = menuPane.getSelectionModel();
            Weekday day = null;
            for (MenuTab menu : menus) {
                if (selection.getSelectedItem().equals(menu.getTab())) {
                    day = menu.getDay();
                    break;
                }
            }
            
            return day; 
        }

        /**
         * Add new dish handler
         * 
         * @param event
         */
        private void handleAdd(ActionEvent event) {
            if (event.getTarget() instanceof Button) {
                Button add = (Button) event.getTarget();
                String id = add.getId();
                List<Category> categories = null;
                
                try {
                    categories = menu.getDayMenu(day);
                    for(Category category: categories) {
                        if (category.getName().equals(id)) {
                            newGroup = category;
                            break;
                        }
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }

                newDish = new TextField();
                addWindow = new Stage();
                addWindow.initModality(Modality.APPLICATION_MODAL);
                addWindow.initOwner(mainStage);

                GridPane addPane = new GridPane();
                addPane.setId("addItemForm");
                addPane.setPadding(new Insets(10));
                addPane.setHgap(10);
                addPane.setVgap(10);

                Button btnAddDish = new Button("Hinzufügen");
                btnAddDish.setPrefWidth(120);
                btnAddDish.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> {
                    try {
                        menu.addDish(day, newGroup.getName(), newDish.getText());
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    refresh(day);

                    newDish.setText("");
                    addWindow.close();
                });
                GridPane.setHalignment(btnAddDish, HPos.CENTER);

                addPane.add(new Label("Speise"), 0, 0);
                addPane.add(newDish, 1, 0);
                addPane.add(btnAddDish, 0, 2, 2, 1);

                addWindow.setScene(new Scene(addPane));
                addWindow.show();
            }
        }

        /**
         * Add new category handler
         * 
         * @param event
         */
        private void handleAddCategory(ActionEvent event) {
            if (event.getTarget() instanceof Button) {
                newCategorie = new TextField();
                addCategorieWindow = new Stage();
                addCategorieWindow.initModality(Modality.APPLICATION_MODAL);
                addCategorieWindow.initOwner(mainStage);

                GridPane addPane = new GridPane();
                addPane.setId("addCategorieForm");
                addPane.setPadding(new Insets(10));
                addPane.setHgap(10);
                addPane.setVgap(10);

                Button btnAddDCategorie = new Button("Hinzufügen");
                btnAddDCategorie.setPrefWidth(120);
                btnAddDCategorie.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> {
                    try { 
                        menu.addCategory(newCategorie.getText());
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    
                    refreshMenu();

                    newCategorie.setText("");
                    addCategorieWindow.close();
                });

                GridPane.setHalignment(btnAddDCategorie, HPos.CENTER);

                addPane.add(new Label("Kategorie"), 0, 0);
                addPane.add(newCategorie, 1, 0);
                addPane.add(btnAddDCategorie, 0, 2, 2, 1);

                addCategorieWindow.setScene(new Scene(addPane));
                addCategorieWindow.show();
            }
        }
    }

    /**
     * ctor
     * 
     * @param mainStage
     */
    public ApplicationView(Stage mainStage, String port) throws MalformedURLException,
                                                                RemoteException, 
                                                                NotBoundException {  
        System.out.println("rmi://" + port + "/User");
        System.out.println("rmi://" + port + "/Order");
        System.out.println("rmi://" + port + "/Menu");
        
        //this.order = (OrderInterface) Naming.lookup("rmi://" + port + "/Order");
        this.menu = (MenuInterface) Naming.lookup("rmi://" + port + "/Menu");
        //this.userCtrl = (UserInterface) Naming.lookup("rmi://" + port + "/User");  
            
        this.usrCtrl = new UserManagement();
        this.mainStage = mainStage;
        this.menuPane = new TabPane();
        this.rootPane = new VBox();
        this.rootPane.setId("rootPane");
        this.login = new LoginPane();
        this.menus = new ArrayList<>();
        
        menus.add(new MenuTab(Weekday.MONDAY));
        menus.add(new MenuTab(Weekday.TUESDAY));
        menus.add(new MenuTab(Weekday.WEDNESDAY));
        menus.add(new MenuTab(Weekday.THURSDAY));
        menus.add(new MenuTab(Weekday.FRIDAY));

        for (MenuTab menu: menus) {
            menuPane.getTabs().add(menu.show());    
        }
        
        this.menuPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }
    
    public void run() throws RemoteException {
        Random randGen = new Random();
        try {
            refreshMenu();
            Thread.sleep(randGen.nextInt(100)+10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * method used to show the menu content
     */
    public void show() {
        rootPane.getChildren().add(login.show());
        rootPane.getChildren().add(menuPane);
        
        showMainWindow(new Scene(rootPane, SCENE_WIDTH, SCENE_HEIGHT));
    }

    /**
     * Displays the main window
     * 
     * @param scene
     */
    private void showMainWindow(Scene scene) {
        mainStage.setScene(scene);
        mainStage.setMinHeight(MIN_HEIGHT);
        mainStage.setMinWidth(MIN_WIDTH);
        mainStage.setTitle("Campina Service");
        mainStage.show();
    }

    /**
     * refresh the menu pane
     */
    private /*synchronized*/ void refreshMenu() {
        menuPane.getTabs().clear();

        for (MenuTab menu : menus) {
            menuPane.getTabs().add(menu.show());
        }
    }
}
