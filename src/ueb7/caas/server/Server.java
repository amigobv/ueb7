package ueb7.caas.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerInterface {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
            String hostPort = args[0];
            
            OrderInterface orderStub = (OrderInterface)UnicastRemoteObject.exportObject(new OrderControl(), 0);
            MenuInterface menuStub = (MenuInterface)UnicastRemoteObject.exportObject(new MenuControl(), 0);
            UserInterface userStub = (UserInterface)UnicastRemoteObject.exportObject(new UserControl(), 0);
            
            LocateRegistry.createRegistry(Util.getPort(hostPort));
            
            System.out.println("rmi://" + hostPort + "/Order");
            System.out.println("rmi://" + hostPort + "/Menu");
            System.out.println("rmi://" + hostPort + "/User");
            
            Naming.rebind("rmi://" + hostPort + "/Order", orderStub);
            Naming.rebind("rmi://" + hostPort + "/Menu", menuStub);
            Naming.rebind("rmi://" + hostPort + "/User", userStub);
            
            System.out.println("Campina service is running, waiting for connections ... ");
    }
   
}
