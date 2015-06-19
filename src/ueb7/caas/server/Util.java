package ueb7.caas.server;

public class Util {
    public static int getPort(String hostPort) {
        int idx = hostPort.lastIndexOf(':');
        
        if (idx == -1) {
            return 1099;
        } else {
            return Integer.parseInt(hostPort.substring(idx+1));
        }
    }
}
