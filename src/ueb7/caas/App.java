package ueb7.caas;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.stage.Stage;
import ueb7.caas.view.ApplicationView;

public class App extends Application {
    private static String hostPort;
    ApplicationView appView;

    public static void main(String[] args) {
        hostPort = args[0];

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception, MalformedURLException, RemoteException, NotBoundException {
        appView = new ApplicationView(stage, hostPort);
        appView.show();
        appView.run();
    }
}
