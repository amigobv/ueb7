package ueb7.caas.view;

import java.util.ArrayList;
import java.util.Iterator;

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
import ueb7.caas.control.UserControl;
import ueb7.caas.model.Group;
import ueb7.caas.model.Menu;
import ueb7.caas.model.User;
import ueb7.caas.model.UserType;
import ueb7.caas.model.Weekdays;

public class ApplicationView {
    private final int STD_PADDING = 10;
    private final int BTN_WIDTH = 120;

    private Stage mainStage;
    private TextField textFieldUsername;
    private PasswordField passwordFieldPwd;
    private UserControl db;
    private Button btnLogin;
    private Button btnLogout;
    private GridPane loginGrid;
    private Hyperlink reg;
    private Stage errorWindow;
    private Stage registration;
    private User activeUser;
    
    private TabPane menuPane;
    
    
    
    /**
     * Class used to display the content of the menu
     * 
     * @author s1310307036
     *
     */
    class MenuTab {
        Menu menu;
        Tab tab;
        private ScrollPane scrollContent;
        private Button btnCommand;
        private VBox tabContent;
        private Stage mainStage;
        private Stage orderWindow;
        private Stage addWindow;
        private Stage addCategorieWindow;
        private UserType user;
        private Group newGroup;
        private TextField newDish;
        private Button btnAddCategorie;
        private TextField newCategorie;
        
        
        public MenuTab(Menu menu) {
            this.menu = menu;
            tab = new Tab(menu.getDay().name());
            
            scrollContent = new ScrollPane();
            scrollContent.setMinHeight(520);
            scrollContent.setMinWidth(480);

            btnCommand = new Button("Bestellen");
            btnCommand.setPrefWidth(120);
            btnCommand.setId("btnOrder");
            btnCommand.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleCommand(e));

            btnAddCategorie = new Button("Neue Kategorie");
            btnAddCategorie.setPrefWidth(120);
            btnAddCategorie.setId("btnCategorie");
            btnAddCategorie.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleAddCategorie(e));
        }
        
        public Tab display() {
            tabContent = new VBox();
            tabContent.setId("tabContent");
            tabContent.setPadding(new Insets(10));
            tabContent.setAlignment(Pos.BOTTOM_LEFT);

            refresh();

            scrollContent.setContent(tabContent);
            tab.setContent(scrollContent);
            
            return tab;
        }
        
        public void refresh() {
            tabContent.getChildren().clear();
            
            tabContent.getChildren().add(getMenu());

            HBox btnBox = new HBox();

            if (user == UserType.GUEST) {
                btnCommand.setVisible(true);
                btnBox.getChildren().add(btnCommand);
            } else if (user == UserType.ADMIN) {
                btnAddCategorie.setVisible(true);
                btnBox.getChildren().add(btnAddCategorie);
            } else {
                btnCommand.setVisible(false);
                btnAddCategorie.setVisible(false);
            }

            btnBox.setPadding(new Insets(5));
            btnBox.setAlignment(Pos.TOP_RIGHT);

            tabContent.getChildren().add(btnBox);
        }


        public String getName() {
            return tab.getText();
        }

        public ArrayList<String> getSelected() {
            ArrayList<String> list = new ArrayList<String>();

            for (Node element : tabContent.getChildren()) {
                if (element instanceof RadioButton) {
                    RadioButton rItem = (RadioButton) element;
                    if (rItem.isSelected()) {
                        list.add(rItem.getText());
                    }
                }
            }

            return list;
        }

        private Pane getMenu() {
            int row = 0;
            int col = 0;
            
            GridPane menuGrid = new GridPane();
            Iterator<Group> it = menu.iterator();

            while (it.hasNext()) {
                ToggleGroup toggle = new ToggleGroup();
                Group group = it.next();
                Text name = new Text(group.getName());
                name.setUnderline(true);
                menuGrid.add(name, col, row++);
                
                Iterator<String> items = group.iterator();
                while (items.hasNext()) {
                    String item = items.next();
                    HBox itemBox = new HBox();
                    
                    Button btnRemove = new Button("-");
                    btnRemove.setId(group.getName() + "_" + item);
                    btnRemove.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleRemove(e));
                    if (user != UserType.ADMIN) {
                        btnRemove.setVisible(false);
                    }
                    
                    RadioButton radioItem = new RadioButton(item);
                    radioItem.setToggleGroup(toggle);

                    if (user != UserType.GUEST || group.getName() == "Desert") {
                        radioItem.setDisable(true);
                    }
                    
                    itemBox.getChildren().addAll(btnRemove, radioItem);
                    menuGrid.add(itemBox, col, row++);
                }
                
                if (user == UserType.ADMIN) {
                    Button btnAddItem = new Button("+");
                    btnAddItem.setId(group.getName());
                    btnAddItem.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> this.handleAdd(e));
                    menuGrid.add(btnAddItem, col, row++);
                }
                
                Separator separateLine = new Separator(Orientation.HORIZONTAL);
                separateLine.setId("separateLine");
                GridPane.setHgrow(separateLine, Priority.ALWAYS);
                menuGrid.add(separateLine, col, row++);
            }

            return menuGrid;
        }

        private void handleRemove(ActionEvent e) {
            if (e.getTarget() instanceof Button) {
                Button delete = (Button) e.getTarget();
                String[] id = delete.getId().split("_");
                Group group = null;
                
                Iterator<Group> it = menu.iterator();
                while (it.hasNext()) {
                    group = it.next();
                    if (id[0].equals(group.getName())) {
                        break;
                    } 
                }

                if (group != null) {
                    group.removeItem(id[1]);
                    refresh();
                }
            }
        }

        private void handleCommand(ActionEvent event) {
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
                    orderPane.add(new Label(item), 0, row);
                    row++;
                }

                HBox timeBox = new HBox();
                timeBox.getChildren().add(new Label("Uhrzeit eingeben"));
                timeBox.getChildren().add(new TextField());

                orderPane.add(timeBox, 0, row, row, 1);
                row++;
                orderPane.add(btnOk, 0, row, row, 1);

                orderWindow.setScene(new Scene(orderPane));
                orderWindow.show();
            }
        }

        private void handleAdd(ActionEvent event) {
            if (event.getTarget() instanceof Button) {
                Button add = (Button) event.getTarget();
                String id = add.getId();

                Iterator<Group> it = menu.iterator();
                while (it.hasNext()) {
                    Group group = it.next();
                    if (group.getName().equals(id)) {
                        newGroup = group;
                        break;
                    } 
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
                    newGroup.add(newDish.getText());
                    refresh();

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

        private void handleAddCategorie(ActionEvent event) {
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
                    menu.addGroup(newCategorie.getText());
                    refresh();

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
    
    private MenuTab monday;
    private MenuTab tuesday;
    private MenuTab wednesday;
    private MenuTab thursday;
    private MenuTab friday;
    
    public ApplicationView(Stage mainStage) {
        this.mainStage = mainStage;
        
        monday = new MenuTab(new Menu(Weekdays.MONDAY));
        tuesday = new MenuTab(new Menu(Weekdays.TUESDAY));
        wednesday = new MenuTab(new Menu(Weekdays.WEDNESDAY));
        thursday = new MenuTab(new Menu(Weekdays.THURSDAY));
        friday = new MenuTab(new Menu(Weekdays.FRIDAY));
    } 
}
