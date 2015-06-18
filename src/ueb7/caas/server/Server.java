package ueb7.caas.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    
    private static int getPort(String hostPort) {
        int idx = hostPort.lastIndexOf(':');
        
        if (idx == -1) {
            return 1099;
        } else {
            return Integer.parseInt(hostPort.substring(idx+1));
        }
    }
    
            
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        String hostPort = args[0];
        
        LocateRegistry.createRegistry(getPort(hostPort));
        ControlInterface controlStub = (ControlInterface) UnicastRemoteObject.exportObject(new Control(), 0);
       
        System.out.println("rebinding rmi://" + hostPort + "/Control");
        
        Naming.rebind("rmi://" + hostPort + "/Control", controlStub);

        
        System.out.println("DateService running, waiting for connections...");
    }
}
