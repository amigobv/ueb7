package ueb7.caas;

import javafx.application.Application;
import javafx.stage.Stage;
import ueb7.caas.view.ApplicationView;

public class App extends Application {
    private static String hostPort;
    

    public static void main(String[] args) {
        hostPort = args[0];

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationView appView = new ApplicationView(stage, hostPort);
        appView.show();
    }

}
